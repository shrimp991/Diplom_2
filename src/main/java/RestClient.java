import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestClient {

  private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";

  public RequestSpecification getBaseSpec() {
    return given()
        .baseUri(BASE_URL)
        .header("Content-type", "application/json");
  }
}
