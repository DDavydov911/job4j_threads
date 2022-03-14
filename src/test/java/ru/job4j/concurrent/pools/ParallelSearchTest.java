package ru.job4j.concurrent.pools;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParallelSearchTest {

    @Test
    public void whenSearch8ThenIndex7() {
        Integer[] numbers = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        ParallelSearch<Integer> ps = new ParallelSearch<>(numbers, 8, 0, numbers.length - 1);
        assertEquals(9, ps.search(10));
    }

    @Test
    public void whenSearch1ThenIndex0() {
        Integer[] numbers = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        ParallelSearch<Integer> ps = new ParallelSearch<>(numbers, 1, 0, numbers.length - 1);
        assertEquals(0, ps.search());
    }

    @Test
    public void whenSearch15ThenIndexMinus1() {
        Integer[] numbers = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        ParallelSearch<Integer> ps = new ParallelSearch<>(numbers, 15, 0, numbers.length - 1);
        assertEquals(-1, ps.search());
    }
}