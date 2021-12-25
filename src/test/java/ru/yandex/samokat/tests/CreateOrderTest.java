package ru.yandex.samokat.tests;

import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.yandex.samokat.client.OrderClient;
import ru.yandex.samokat.model.ColorType;
import ru.yandex.samokat.model.Order;
import ru.yandex.samokat.utils.OrderUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.yandex.samokat.model.ColorType.BLACK;
import static ru.yandex.samokat.model.ColorType.GREY;

public class CreateOrderTest {

    @ParameterizedTest
    @MethodSource("generateTestData")
    public void testCreateOrder(List<ColorType> colors, int expectedCode, Matcher expectedTrack) {
        OrderClient orderClient = new OrderClient();
        Order order = OrderUtils.buildRandomOrder(colors);

        ValidatableResponse responseOfCreating = orderClient.create(order);

        responseOfCreating.assertThat()
                .statusCode(expectedCode)
                .body("track", expectedTrack);
    }

    static Collection<Object[]> generateTestData() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList(BLACK), SC_CREATED, notNullValue()},
                {Arrays.asList(GREY), SC_CREATED, notNullValue()},
                {Arrays.asList(GREY, BLACK), SC_CREATED, notNullValue()},
                {null, SC_CREATED, notNullValue()}
        });
    }
}
