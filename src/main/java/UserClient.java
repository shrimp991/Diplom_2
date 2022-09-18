import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends RestClient {
    private static final String CREATE_USER_PATH = "/api/auth/register";
    private static final String LOGIN_USER_PATH = "/api/auth/login";
    private static final String DATA_USER_PATH = "/api/auth/user";

    public ValidatableResponse create (User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(CREATE_USER_PATH)
                .then();
    }

    public ValidatableResponse login (UserCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(LOGIN_USER_PATH)
                .then();
    }

    public ValidatableResponse modify (String token, User user) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", token)
                .body(user)
                .when()
                .patch(DATA_USER_PATH)
                .then();
    }

    public ValidatableResponse modify (User user) {
        return given()
            .spec(getBaseSpec())
            .body(user)
            .when()
            .patch(DATA_USER_PATH)
            .then();
    }

    public ValidatableResponse delete (String token) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", token)
                .when()
                .delete(DATA_USER_PATH)
                .then();
    }
}
