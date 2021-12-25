package ru.yandex.samokat.tests;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.yandex.samokat.client.CourierClient;
import ru.yandex.samokat.model.Courier;
import ru.yandex.samokat.model.CourierCredentials;
import ru.yandex.samokat.utils.CourierUtils;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.yandex.samokat.utils.CourierCredentialsUtils.*;

public class LoginCourierTest {
    private final CourierClient courierClient = new CourierClient();
    private Courier courier;
    private Integer courierId;

    @BeforeEach
    public void setUp() {
        courier = CourierUtils.buildRandom();
        courierClient.create(courier);
    }

    @AfterEach
    public void tearDown() {
        loginAndSetCourierIdForDeletion(courier);
        courierClient.delete(courierId.toString()).assertThat().statusCode(SC_OK);
    }

    private void loginAndSetCourierIdForDeletion(Courier courier) {
        courierId = courierClient.login(buildCourierCredentialsByCourier(courier))
                .extract()
                .path("id");
    }

    @Test
    public void testLoginWithAllFieldsReturnsId() {

        ValidatableResponse responseOfLogin = courierClient.login(
                new CourierCredentials(
                        courier.getLogin(),
                        courier.getPassword()
                ));

        responseOfLogin.assertThat()
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    public void testLoginWithoutLoginReturnsBadRequestCode() {

        ValidatableResponse responseOfLogin = courierClient.login(
                new CourierCredentials(null, courier.getPassword()));
        responseOfLogin.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));

    }

    @Disabled("TC is ignored because of a bug: service returns 504 error code when password equals to null")
    @Test
    public void testLoginWithoutPasswordReturnsBadRequestCode() {
        // FIXME: 22.12.2021 в этих всех методах тоже надо поаккуратнее порефакторить

        ValidatableResponse responseOfLogin = courierClient.login(new CourierCredentials(courier.getLogin(), null));
        responseOfLogin.assertThat().statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void testLoginWithIncorrectLoginReturnsNotFoundCode() {
        ValidatableResponse responseOfLogin = courierClient
                .login(buildCourierCredentialsWithRandomLogin(courier.getPassword()));

        responseOfLogin.assertThat().statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void testLoginWithIncorrectPasswordReturnsNotFoundCode() {
        ValidatableResponse responseOfLogin = courierClient
                .login(buildCourierCredentialsWithRandomPassword(courier.getLogin()));

        responseOfLogin.assertThat().statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
