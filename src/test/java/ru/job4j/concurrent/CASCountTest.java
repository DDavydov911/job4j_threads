package ru.job4j.concurrent;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void when3increment() {
        CASCount count = new CASCount();
        count.increment();
        assertThat(count.get(), is(1));
        count.increment();
        assertThat(count.get(), is(2));
        count.increment();
        assertThat(count.get(), is(3));
    }

    @Test
    public void when2ThreadIncrement() throws InterruptedException {
        CASCount count = new CASCount();
        List<Integer> list = new ArrayList<>();
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        count.increment();
                        list.add(count.get());
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        count.increment();
                        list.add(count.get());
                    }
                }
        );
        first.start();
        first.join();
        second.start();
        second.join();
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), list);
    }
}