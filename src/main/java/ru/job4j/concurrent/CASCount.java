package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int number;
        do {
            number = this.get();
        } while (!count.compareAndSet(number, number + 1));
    }

    public int get() {
        return count.get();
    }

    public static void main(String[] args) {
        CASCount count = new CASCount();
        count.increment();
        System.out.println(count.get());
    }
}