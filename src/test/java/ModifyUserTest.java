import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ModifyUserTest {

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
  @DisplayName("Проверка, что авторизованный пользователь может изменить сведения о себе")
  @Description("Проверяется PATCH-запрос для ручки /api/auth/user, должен вернуться код 200 и success: true")
  @Step("Попытка изменить сведения авторизованным пользователем")
  public void checkUserCanBeModifiedWithAuth() {
    userClient
        .create(User.getDefault())
        .statusCode(SC_OK);
    ValidatableResponse response =
        userClient
            .login(UserCredentials.from(User.getDefault()))
            .statusCode(SC_OK);
    accessToken = response.extract().path("accessToken");
    userClient
        .modify(accessToken, User.getNew())
        .statusCode(SC_OK)
        .and()
        .assertThat()
        .body("success", equalTo(TRUE),
            "user.email", equalTo(User.getNew().getEmail()),
            "user.name", equalTo(User.getNew().getName()));
  }

  @Test
  @DisplayName("Проверка, что неавторизованный пользователь не может изменить сведения о пользователе")
  @Description("Проверяется PATCH-запрос для ручки /api/auth/user, должен вернуться код 401 и success: false")
  @Step("Попытка изменить сведения неавторизованным пользователем")
  public void checkUserCanNotBeModifiedWithoutAuth() {
    ValidatableResponse response =
        userClient
            .create(User.getDefault())
            .statusCode(SC_OK);
    accessToken = response.extract().path("accessToken");
    userClient
        .modify(User.getNew())
        .statusCode(SC_UNAUTHORIZED)
        .and()
        .body("success", equalTo(FALSE),
            "message", equalTo("You should be authorised"));
  }
}
