import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class CreateOrderTest {
  private User user;
  private UserClient userClient;
  private String accessToken;
  private Order order;
  private OrderClient orderClient;

  @Test
  @DisplayName("Проверка создания заказа авторизованным пользователем с ингредиентами")
  @Description("Проверяется POST-запрос для ручки /api/orders. \n" +
  "Должен вернуться код 200, в теле ответа должен быть ключ \"success\" : true, в order должен содержаться ключ status. \n" +
  "Одновременно проверяется 2 условия \"с авторизацией\" и \"с ингредиентами\", т.к. одно без другого не даст положительный результат.")
  public void checkOrderCanBeCreatedWithAuthAndIngredients() {
    user = UserGenerator.getDefault();
    userClient = new UserClient();
    order = OrderGenerator.getDefault();
    orderClient = new OrderClient();
    ValidatableResponse response = userClient.create(user)
        .statusCode(SC_OK);
    accessToken = response.extract().path("accessToken");
    orderClient.create(order, accessToken)
        .statusCode(SC_OK)
        .and()
        .assertThat()
        .body("success", equalTo(TRUE))
        .body("order", hasKey("status"));
    userClient.delete(accessToken);
  }

  @Test
  @DisplayName("Проверка создания заказа неавторизованным пользователем с ингредиентами")
  @Description("Проверяется POST-запрос для ручки /api/orders. \n" +
      "Должен вернуться код 200, в теле ответа должен быть ключ \"success\" : true, в order не должно быть ключа status.")
  public void checkOrderCanNotBeCreatedWithoutAuthAndWithIngredients() {
    order = OrderGenerator.getDefault();
    orderClient = new OrderClient();
    orderClient.create(order)
        .statusCode(SC_OK)
        .and()
        .assertThat()
        .body("success", equalTo(TRUE))
        .body("order", not(hasKey("status")));
  }

  @Test
  @DisplayName("Проверка создания заказа авторизованным пользователем без ингредиентов")
  @Description("Проверяется POST-запрос для ручки /api/orders. \n" +
      "Должен вернуться код 400, в теле ответа должен быть ключ \"success\" : false.")
  public void checkOrderCanNotBeCreatedWithAuthAndWithoutIngredients() {
    user = UserGenerator.getDefault();
    userClient = new UserClient();
    order = OrderGenerator.getWithoutIngredients();
    orderClient = new OrderClient();
    ValidatableResponse response = userClient.create(user)
        .statusCode(SC_OK);
    accessToken = response.extract().path("accessToken");
    orderClient.create(order, accessToken)
        .statusCode(SC_BAD_REQUEST)
        .and()
        .assertThat()
        .body("success", equalTo(FALSE));
    userClient.delete(accessToken);
  }

  @Test
  @DisplayName("Проверка создания заказа авторизованным пользователем c неправильным хэшем ингредиентов")
  @Description("Проверяется POST-запрос для ручки /api/orders. \n" +
      "Должен вернуться код 500.")
  public void checkOrderCanNotBeCreatedWithAuthAndWithoutIncorrectHash() {
    user = UserGenerator.getDefault();
    userClient = new UserClient();
    order = OrderGenerator.getWithIncorrectHash();
    orderClient = new OrderClient();
    ValidatableResponse response = userClient.create(user)
        .statusCode(SC_OK);
    accessToken = response.extract().path("accessToken");
    orderClient.create(order, accessToken)
        .statusCode(SC_INTERNAL_SERVER_ERROR);
    userClient.delete(accessToken);
  }
}
