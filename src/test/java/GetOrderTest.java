import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class GetOrderTest {

  private UserClient userClient;
  private String accessToken;
  private OrderClient orderClient;

  @Test
  @DisplayName("Проверка, что для авторизованного пользователя вернется список заказов")
  @Description("Проверяется GET-запрос для ручки /api/orders, должен вернуться код 200 и success: true")
  public void GetOrderWithAuthTest() {
    userClient = new UserClient();
    orderClient = new OrderClient();
    ValidatableResponse response =
        userClient.create(User.getDefault())
            .statusCode(SC_OK);
    accessToken = response.extract().path("accessToken");
    orderClient.create(Order.getDefault(), accessToken)
        .statusCode(SC_OK);
    orderClient.get(accessToken)
        .statusCode(SC_OK)
        .and()
        .body("success", equalTo(TRUE));
    userClient.delete(accessToken);
  }

  @Test
  @DisplayName("Проверка, что для авторизованного пользователя вернется список заказов")
  @Description("Проверяется GET-запрос для ручки /api/orders, должен вернуться код 401 и success: false")
  public void GetOrderWithoutAuthTest() {
    orderClient = new OrderClient();
    orderClient.get()
        .statusCode(SC_UNAUTHORIZED)
        .and()
        .body("success", equalTo(FALSE));
  }
}
