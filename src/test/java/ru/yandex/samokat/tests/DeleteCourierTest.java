package ru.yandex.samokat.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import ru.yandex.samokat.client.CourierClient;
import ru.yandex.samokat.model.Courier;
import ru.yandex.samokat.utils.CourierUtils;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static ru.yandex.samokat.utils.CourierCredentialsUtils.buildCourierCredentialsByCourier;

public class DeleteCourierTest {

    CourierClient courierClient = new CourierClient();

    @Test
    public void testDeleteExistingCourierReturnsOk() {
        Courier courier = CourierUtils.buildRandom();
        courierClient.create(courier);

        Integer id = courierClient.login(buildCourierCredentialsByCourier(courier))
                .extract().path("id");

        courierClient.delete(id.toString())
                .assertThat()
                .statusCode(SC_OK)
                .body("ok", equalTo(true));
    }

    @Test
    public void testDeleteCourierWithoutIdReturnsBadRequest() {
        courierClient.delete("")
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для удаления курьера"));
    }

    @Test
    public void testDeleteCourierWithNonExistingIdReturnsNotFoundCode() {
        courierClient.delete(RandomStringUtils.randomNumeric(5))
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Курьера с таким id нет"));
    }
}
