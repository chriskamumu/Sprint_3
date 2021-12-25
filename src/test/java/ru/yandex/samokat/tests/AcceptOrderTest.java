package ru.yandex.samokat.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.samokat.client.CourierClient;
import ru.yandex.samokat.client.OrderClient;
import ru.yandex.samokat.model.Courier;
import ru.yandex.samokat.model.Order;
import ru.yandex.samokat.utils.CourierUtils;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static ru.yandex.samokat.utils.CourierCredentialsUtils.buildCourierCredentialsByCourier;
import static ru.yandex.samokat.utils.OrderUtils.buildRandomOrder;

public class AcceptOrderTest {

    private OrderClient orderClient = new OrderClient();
    private CourierClient courierClient = new CourierClient();
    private Integer orderId;
    private Integer courierId;

    @BeforeEach
    private void setUp(){
        Order order = buildRandomOrder(null);
        Courier courier = CourierUtils.buildRandom();

        Integer orderTrack = orderClient.create(order).extract().path("track");
        orderId = orderClient.getOrderByTrack(orderTrack.toString()).extract().path("order.id");

        courierClient.create(courier);
        courierId = courierClient.login(buildCourierCredentialsByCourier(courier)).extract().path("id");
    }

    @AfterEach
    private void tearDown(){
        courierClient.delete(courierId.toString());
    }

    @Test
    public void testAcceptExistingOrderByExistingCourierReturnsOk() {

        orderClient.acceptOrder(orderId.toString(), courierId.toString())
                .statusCode(SC_OK).body("ok", equalTo(true));
    }

    @Test
    public void testAcceptOrderWithoutCourierIdReturnsBadRequest(){

        orderClient.acceptOrder(orderId.toString(), "")
                .statusCode(SC_BAD_REQUEST).body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    public void testAcceptOrderByNonExistingCourierIdReturnsNotFoundCode(){
        orderClient.acceptOrder(orderId.toString(), RandomStringUtils.randomNumeric(5))
                .statusCode(SC_NOT_FOUND).body("message", equalTo("Курьера с таким id не существует"));
    }

    @Test
    public void testAcceptWithoutOrderIdReturnsBadRequest(){
        orderClient.acceptOrder("", courierId.toString())
                .statusCode(SC_BAD_REQUEST).body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    public void testAcceptWithNonExistingOrderIdReturnsNotFoundCode(){
        orderClient.acceptOrder(RandomStringUtils.randomNumeric(8), courierId.toString())
                .statusCode(SC_NOT_FOUND).body("message", equalTo("Заказа с таким id не существует"));
    }
}
