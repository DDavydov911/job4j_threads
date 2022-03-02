package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int number;
        do {
            number = this.get();
        } while (!count.compareAndSet(number, number + 1));
    }

    public int get() {
        Integer number = count.get();
        if (number == null) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        return number;
    }
}