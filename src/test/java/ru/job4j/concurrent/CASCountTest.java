package ru.job4j.concurrent;

import org.junit.Test;

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
}