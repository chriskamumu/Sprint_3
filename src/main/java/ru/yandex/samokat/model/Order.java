package ru.yandex.samokat.model;

import java.util.List;

public class Order {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final MetroStation metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<ColorType> colors;

    public Order(String firstName,
                 String lastName,
                 String address,
                 MetroStation metroStation,
                 String phone,
                 int rentTime,
                 String deliveryDate,
                 String comment,
                 List<ColorType> colors) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.colors = colors;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetroStation() {
        return metroStation.getTitle();
    }

    public String getPhone() {
        return phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public List<ColorType> getColors() {
        return colors;
    }

    @Override
    public String toString() {
        return "Order{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", metroStation='" + metroStation.getTitle() + '\'' +
                ", phone='" + phone + '\'' +
                ", rentTime=" + rentTime +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", comment='" + comment + '\'' +
                ", colors=" + colors +
                '}';
    }
}
