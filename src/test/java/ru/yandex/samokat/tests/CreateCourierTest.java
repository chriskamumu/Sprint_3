package ru.yandex.samokat.tests;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.samokat.client.CourierClient;
import ru.yandex.samokat.model.Courier;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.yandex.samokat.utils.CourierCredentialsUtils.buildCourierCredentialsByCourier;
import static ru.yandex.samokat.utils.CourierUtils.*;

public class CreateCourierTest {
    public CourierClient courierClient;
    List<Integer> courierIds;

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();
        courierIds = new ArrayList<>();
    }

    @AfterEach
    public void tearDown() {
        if (!courierIds.isEmpty()) {
            for (Integer courierId :
                    courierIds) {
                courierClient.delete(courierId.toString()).assertThat().statusCode(SC_OK);
            }
        }
    }

    @Test
    public void testCreateCourierWithAllFieldsReturnsOk() {

        Courier courier = buildRandom();
        ValidatableResponse responseOfCreating = courierClient.create(courier);

        responseOfCreating
                .assertThat()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));

        loginCourierAndAddCourierIdForDeletion(courier);

    }

    @Test
    public void testCreateExistingCourierReturnsCodeConflict() {
        Courier courier1 = buildRandom();
        courierClient.create(courier1).statusCode(SC_CREATED);
        loginCourierAndAddCourierIdForDeletion(courier1);

        Courier courier2 = buildRandomFirstNameAndPassword(courier1.getLogin());
        ValidatableResponse responseOfCourier2Creating =
                courierClient.create(courier2);

        responseOfCourier2Creating.assertThat()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));

    }

    @Test
    public void testCreateCourierWithOnlyMandatoryFieldsReturnsOk() {
        Courier courier = buildRandomLoginAndPassword(null);
        ValidatableResponse responseOfCreating = courierClient.create(courier);
        responseOfCreating.assertThat()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));

        loginCourierAndAddCourierIdForDeletion(courier);

    }


    @Test
    public void testCreateCourierWithoutLoginReturnsCodeBadRequest() {
        Courier courier = buildRandomFirstNameAndPassword(null);
        ValidatableResponse responseOfCreating = courierClient.create(courier);
        responseOfCreating.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void testCreateCourierWithoutPasswordReturnsCodeBadRequest() {
        Courier courier = buildRandomFirstNameAndLogin(null);
        ValidatableResponse responseOfCreating = courierClient.create(courier);
        responseOfCreating.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


    private void loginCourierAndAddCourierIdForDeletion(Courier courier) {
        Integer id = courierClient
                .login(buildCourierCredentialsByCourier(courier))
                .extract().path("id");
        courierIds.add(id);
    }

}
