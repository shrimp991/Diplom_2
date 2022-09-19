import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.apache.http.HttpStatus.*;

public class CreateUserTest {

  private UserClient userClient;
  private String accessToken;

  @Before
  @Step("Инициализация userClient")
  public void initializationUserClient() {
    userClient = new UserClient();
  }

  @After
  @Step("Удаление тестового пользователя")
  public void deleteUser() {
    userClient.delete(accessToken);
  }

  @Test
  @DisplayName("Проверка создания пользователя")
  @Description("Проверяется POST-запрос для ручки /api/auth/register, должен вернуть код 200 и success: true")
  @Step("Создание дефолтного пользователя")
  public void checkUserCanBeCreatedTest() {
    ValidatableResponse response = userClient
        .create(User.getDefault())
        .statusCode(SC_OK)
        .and()
        .assertThat()
        .body("success", equalTo(TRUE));
    accessToken = response.extract().path("accessToken");
  }

  @Test
  @DisplayName("Проверка создания дубля пользователя")
  @Description("Проверяется POST-запрос для ручки /api/auth/register, должен вернуть код 403 и success: false")
  @Step("Создание двух одинаковых пользователей")
  public void checkDuplicateUserCanNotBeCreated() {
    ValidatableResponse response = userClient.create(User.getDefault());
    accessToken = response.extract().path("accessToken");
    userClient.create(User.getDefault())
        .statusCode(SC_FORBIDDEN)
        .and()
        .assertThat()
        .body("success",
            equalTo(FALSE),
            "message",
            equalTo("User already exists"));
  }
}
