package ru.job4j.concurrent.transfers;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class User {
    private final int id;

    @GuardedBy("this")
    private int amount;

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public synchronized int getAmount() {
        return amount;
    }

    public synchronized void setAmount(int amount) {
        this.amount = amount;
    }
}
