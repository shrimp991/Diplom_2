import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.lang.Boolean.FALSE;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserWithoutRequiredFieldTest {

  private UserClient userClient;
  private String accessToken;
  private ValidatableResponse response;

  @Before
  @Step("Инициализация userClient")
  public void initializationUserClient() {
    userClient = new UserClient();
  }

  @After
  @Step("Удаление пользователя при наличии токена")
  public void deleteUserIfHasToken() {
    accessToken = response.extract().path("accessToken");
    if (accessToken != null) {
      userClient.delete(accessToken);
    }
  }

  @Test
  @DisplayName("Проверка создания пользователя без email")
  @Description("Проверяется POST-запрос для ручки /api/auth/register, должен вернуться код 403 и success: false")
  @Step("Инициализация пользователя без email")
  public void checkUserCanNotBeCreatedWithoutEmail() {
    response = userClient
        .create(User.getWithoutEmail())
        .statusCode(SC_FORBIDDEN)
        .and()
        .assertThat()
        .body("success",
            equalTo(FALSE),
            "message",
            equalTo("Email, password and name are required fields"));
  }

  @Test
  @DisplayName("Проверка создания пользователя без пароля")
  @Description("Проверяется POST-запрос для ручки /api/auth/register, должен вернуться код 403 и success: false")
  @Step("Инициализация пользователя без пароля")
  public void checkUserCanNotBeCreatedWithoutPassword() {
    response = userClient
        .create(User.getWithoutPassword())
        .statusCode(SC_FORBIDDEN)
        .and()
        .assertThat()
        .body("success",
            equalTo(FALSE),
            "message",
            equalTo("Email, password and name are required fields"));
  }

  @Test
  @DisplayName("Проверка создания пользователя без имени")
  @Description("Проверяется POST-запрос для ручки /api/auth/register, должен вернуться код 403 и success: false")
  @Step("Инициализация пользователя без имени")
  public void checkUserCanNotBeCreatedWithoutName() {
    response = userClient
        .create(User.getWithoutName())
        .statusCode(SC_FORBIDDEN)
        .and()
        .assertThat()
        .body("success",
            equalTo(FALSE),
            "message",
            equalTo("Email, password and name are required fields"));
  }
}
