package ru.job4j.concurrent;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SingleLockListTest {

    @Test
    public void add() throws InterruptedException {
        SingleLockList<Integer> list = new SingleLockList<>();
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl, is(Set.of(1, 2)));
    }

    @Test
    public void get() throws InterruptedException {
        SingleLockList<Integer> list = new SingleLockList<>(List.of(1, 2));
        Set<Integer> rsl = new TreeSet<>();
        Thread first = new Thread(() -> rsl.add(list.get(0)));
        Thread second = new Thread(() -> rsl.add(list.get(1)));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(rsl, is(Set.of(1, 2)));
    }
}