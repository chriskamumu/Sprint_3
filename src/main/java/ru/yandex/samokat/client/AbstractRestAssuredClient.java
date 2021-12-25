package ru.yandex.samokat.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

abstract class AbstractRestAssuredClient {
    protected RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://qa-scooter.praktikum-services.ru/")
                .build();
    }
}
