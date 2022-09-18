import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static java.lang.Boolean.FALSE;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserWithoutRequiredFieldTest {
    UserClient userClient;
    User user;

    @Before
    @Step("Инициализация userClient")
    public void initializationUserClient() {
        userClient = new UserClient();
    }
    @After
    @Step("Попытка создать пользователя")
    public void tryToCreateUser(){
        ValidatableResponse response = userClient
            .create(user)
            .statusCode(SC_FORBIDDEN)
            .and()
            .assertThat()
            .body("success",
                equalTo(FALSE),
                "message",
                equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Проверка создания пользователя без email")
    @Description("Проверяется POST-запрос для ручки /api/auth/register, должен вернуться код 403 и success: false")
    @Step("Инициализация пользователя без email")
    public void checkUserCanNotBeCreatedWithoutEmail() {
        user = UserGenerator.getWithoutEmail();
    }

    @Test
    @DisplayName("Проверка создания пользователя без пароля")
    @Description("Проверяется POST-запрос для ручки /api/auth/register, должен вернуться код 403 и success: false")
    @Step("Инициализация пользователя без пароля")
    public void checkUserCanNotBeCreatedWithoutPassword() {
        user = UserGenerator.getWithoutPassword();
    }

    @Test
    @DisplayName("Проверка создания пользователя без имени")
    @Description("Проверяется POST-запрос для ручки /api/auth/register, должен вернуться код 403 и success: false")
    @Step("Инициализация пользователя без имени")
    public void checkUserCanNotBeCreatedWithoutName() {
        user = UserGenerator.getWithoutName();
    }
}
