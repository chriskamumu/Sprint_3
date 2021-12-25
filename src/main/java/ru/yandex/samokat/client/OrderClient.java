package ru.yandex.samokat.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.samokat.model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends AbstractRestAssuredClient {
    private final static String ORDER_PATH = "/api/v1/orders";

    @Step("Send POST request to /api/v1/orders to create a new order")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .log().all()
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }

    @Step("Send GET request to /api/v1/orders to get order list")
    public ValidatableResponse getOrderList() {
        return given()
                .spec(getBaseSpec())
                .log().all()
                .when()
                .get(ORDER_PATH)
                .then().log().all();
    }

    @Step("Send PUT request to /api/v1/orders/accept/:{orderId} to accept the order " +
            "by a courier with ID = {courierId}")
    public ValidatableResponse acceptOrder(String orderId, String courierId) {
        return given()
                .spec(getBaseSpec())
                .queryParam("courierId", courierId)
                .log().all()
                .when()
                .put(ORDER_PATH + "/accept/" + orderId)
                .then().log().all();
    }

    @Step("Send GET request to /api/v1/orders to get order id by track = {track}")
    public ValidatableResponse getOrderByTrack(String track) {
        return given()
                .spec(getBaseSpec())
                .queryParam("t", track)
                .log().all()
                .when()
                .get(ORDER_PATH + "/track")
                .then().log().all();
    }
}
