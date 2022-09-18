import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

public class LoginUserTest {
  private User user;
  private UserClient userClient;
  private String accessToken;

  @Before
  public void initializationUserClient() {
    userClient = new UserClient();
  }

  @Test
  @DisplayName("Проверка, что пользователь может авторизоваться")
  @Description("Проверка POST-запроса для ручки /api/auth/login, должен вернуться код 200 и success: true")
  public void checkUserCanLogin() {
    user = UserGenerator.getDefault();
    userClient
        .create(user)
        .statusCode(SC_OK);
    ValidatableResponse response =
        userClient
        .login(UserCredentials.from(user))
        .statusCode(SC_OK)
        .and()
        .assertThat()
        .body("success", equalTo(TRUE));
    accessToken = response.extract().path("accessToken");
    userClient.delete(accessToken);
  }

  @Test
  @DisplayName("Проверка, что несуществующий пользователь не может авторизоваться")
  @Description("Проверка POST-запроса для ручки /api/auth/login, должен вернуться код 401 и success: false")
  public void checkNonExistentUserCanNotLogin() {
    user = UserGenerator.getNonExistent();
    userClient
        .login(UserCredentials.from(user))
        .statusCode(SC_UNAUTHORIZED)
        .and()
        .body("success", equalTo(FALSE));
  }
}
