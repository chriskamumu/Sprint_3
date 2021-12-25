package ru.yandex.samokat.utils;

import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.samokat.model.Courier;
import ru.yandex.samokat.model.CourierCredentials;

public class CourierCredentialsUtils {

    public static CourierCredentials buildCourierCredentialsByCourier(Courier courier) {
        return new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    public static CourierCredentials buildCourierCredentialsWithRandomPassword(String login) {
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new CourierCredentials(login, password);
    }

    public static CourierCredentials buildCourierCredentialsWithRandomLogin(String password) {
        final String login = RandomStringUtils.randomAlphabetic(10);
        return new CourierCredentials(login, password);
    }

}
