package ru.yandex.samokat.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.samokat.client.OrderClient;
import ru.yandex.samokat.model.Order;
import ru.yandex.samokat.utils.OrderUtils;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderByTrackTest {

    OrderClient orderClient = new OrderClient();
    Integer orderTrack;

    @BeforeEach
    public void setUp() {
        Order order = OrderUtils.buildRandomOrder(null);
        orderTrack = orderClient.create(order).extract().path("track");
    }


    @Test
    public void testGetOrderByExistingTrackReturnsOkAndNotNullOrder(){
        orderClient.getOrderByTrack(orderTrack.toString()).statusCode(SC_OK).body("order", notNullValue());
    }

    @Test
    public void testGetOrderByEmptyTrackReturnsBadRequestCode(){
        orderClient.getOrderByTrack("").statusCode(SC_BAD_REQUEST).body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    public void testGetOrderByNonExistingTrackReturnsNotFoundCode(){
        orderClient.getOrderByTrack(RandomStringUtils.randomNumeric(8)).statusCode(SC_NOT_FOUND).body("message", equalTo("Заказ не найден"));
    }
}
