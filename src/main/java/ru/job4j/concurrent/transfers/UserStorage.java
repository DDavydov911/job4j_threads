package ru.job4j.concurrent.transfers;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final List<User> list = new ArrayList<>();

    public synchronized boolean add (User user) {
        return list.add(user);
    }

    public synchronized boolean delete(User user) {
        return list.remove(user);
    }

    private synchronized User getUser(int id) {
        for (User user : list) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User from = getUser(fromId);
        User to = getUser(toId);
        if (from != null && to != null && from.getAmount() >= amount) {
            from.setAmount(from.getAmount() - amount);
            to.setAmount(to.getAmount() + amount);
            return true;
        }
        return false;
    }
}
