package ru.yandex.samokat.model;

import org.apache.commons.lang3.RandomUtils;

public enum MetroStation {
    BULVAR_ROCOSOVSCOGO ("Бульвар Рокоссовского"),
    CHERKIZOVSKAYA ("Черкизовская"),
    PREOBRAZHENSKAYA_PLOSHAD ("Преображенская площадь"),
    SOKOLNIKI ("Сокольники"),
    KRASNOSELSKAYA ("Красносельская"),
    KOMSOMOLSKAYA ("Комсомольская"),
    KRASNIE_VOROTA ("Красные Ворота");

    private final String title;

    MetroStation(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static MetroStation getRandomStation(){
        int length = MetroStation.values().length;
        int id = RandomUtils.nextInt(0, length);
        return MetroStation.values()[id];
    }

}
