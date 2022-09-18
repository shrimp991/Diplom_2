import static io.restassured.RestAssured.given;

import io.restassured.response.ValidatableResponse;

public class OrderClient extends RestClient {
  private static final String ORDER_PATH = "/api/orders";

  public ValidatableResponse create (Order order) {
    return given()
        .spec(getBaseSpec())
        .body(order)
        .when()
        .post(ORDER_PATH)
        .then();
  }
  public ValidatableResponse create (Order order, String token) {
    return given()
        .spec(getBaseSpec())
        .header("Authorization", token)
        .body(order)
        .when()
        .post(ORDER_PATH)
        .then();
  }
  public ValidatableResponse get(String token) {
    return given()
        .spec(getBaseSpec())
        .header("Authorization", token)
        .when()
        .get(ORDER_PATH)
        .then();
  }
  public ValidatableResponse get() {
    return given()
        .spec(getBaseSpec())
        .when()
        .get(ORDER_PATH)
        .then();
  }
}
