package com.itmo.assassins.model.user;

public enum UserRole {
    MASTER_ASSASSIN("Мастер"),
    GUNSMITH("Оружейник"),
    CABMAN("Извозчик"),
    CUSTOMER("Заказчик"),
    EXECUTOR("Исполнитель");

    private final String label;

    UserRole(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
