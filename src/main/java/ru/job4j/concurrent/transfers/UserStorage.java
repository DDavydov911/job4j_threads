package ru.job4j.concurrent.transfers;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized boolean add(User user) {
        User u = users.putIfAbsent(user.getId(), user);
        return (u == null);
    }

    public synchronized boolean update(User user) {
        return users.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    private synchronized User getUser(int id) {
        return users.get(id);
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
