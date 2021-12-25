package ru.yandex.samokat.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.samokat.model.Courier;
import ru.yandex.samokat.model.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient extends AbstractRestAssuredClient {

    private final static String COURIER_PATH = "/api/v1/courier";

    @Step("Send POST request to /api/v1/courier to create a new courier")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Send POST request to /api/v1/courier/login to login")
    public ValidatableResponse login(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "/login")
                .then();
    }

    @Step("Send DELETE request to /api/v1/courier/:id to remove the courier with ID = {courierId}")
    public ValidatableResponse delete(String courierId) {

        return given()
                .spec(getBaseSpec())
                .log().all()
                .when()
                .delete(COURIER_PATH + "/" + courierId)
                .then();

    }
}
