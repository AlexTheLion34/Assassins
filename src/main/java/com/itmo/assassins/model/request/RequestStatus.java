package com.itmo.assassins.model.request;

public enum RequestStatus {

    MODERATING("На согласовании"),
    PACKING_1("Подбор оружия"),
    PACKING_2("Снаряжение поездки"),
    EXECUTING("Исполнение"),
    CONFIRMING("Ожидает подтверждения"),
    PAYMENT_CONFIRMING("Подтверждение оплаты"),
    DONE("Выполнен");

    public final String label;

    RequestStatus(String label) {
        this.label = label;
    }
}
