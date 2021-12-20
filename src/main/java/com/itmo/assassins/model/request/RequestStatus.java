package com.itmo.assassins.model.request;

public enum RequestStatus {

    MODERATING("На согласовании"),
    PACKING_1("Подбор оружия"),
    PACKING_2("Снаряжение поездки"),
    EXECUTING("Исполнение"),
    CONFIRMING("Ожидает подтверждения"),
    EVALUATING("Выставление оценки"),
    PAYMENT_CONFIRMING("Подтверждение оплаты"),
    DONE("Выполнен");

    private final String label;

    RequestStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
