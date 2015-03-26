package ru.yaal.gazinformservice;

public class Account {
    private String name;
    private String family;

    public Account(String name, String family) {
        this.name = name;
        this.family = family;
    }

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }
}