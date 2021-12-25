package ru.yandex.samokat.utils;

import ru.yandex.samokat.model.Courier;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class CourierUtils {
    public static Courier buildRandom() {
        return new Courier(
                randomAlphabetic(10),
                randomAlphabetic(10),
                randomAlphabetic(10));
    }

    public static Courier buildRandomFirstNameAndPassword(String login) {
        final String password = randomAlphabetic(10);
        final String firstName = randomAlphabetic(10);

        return new Courier(login, password, firstName);
    }

    public static Courier buildRandomFirstNameAndLogin(String password) {
        final String login = randomAlphabetic(10);
        final String firstName = randomAlphabetic(10);

        return new Courier(login, password, firstName);
    }

    public static Courier buildRandomLoginAndPassword(String firstName) {
        final String login = randomAlphabetic(10);
        final String password = randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }

}
