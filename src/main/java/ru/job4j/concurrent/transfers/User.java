package ru.job4j.concurrent.transfers;

public class User {
    private final int id;

    private int amount;

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
