package ru.yandex.samokat.utils;

import org.apache.commons.lang3.RandomUtils;
import ru.yandex.samokat.model.ColorType;
import ru.yandex.samokat.model.MetroStation;
import ru.yandex.samokat.model.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.*;

public class OrderUtils {

    private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public static Order buildRandomOrder(List<ColorType> colors){
        final String firstName = randomAlphabetic(10);
        final String lastName = randomAlphabetic(10);
        final String address = randomAlphanumeric(10);
        final MetroStation metroStation = MetroStation.getRandomStation();
        final String phone = "+7" + randomNumeric(10);
        final int rentTime = RandomUtils.nextInt(0,11);
        final String deliveryDate = formatter.format(new Date());
        final String comment = randomAlphabetic(10);

        return new Order(
                firstName,
                lastName,
                address,
                metroStation,
                phone,
                rentTime,
                deliveryDate,
                comment,
                colors);
    }
}
