package ru.job4j.concurrent.pools;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParallelSearchTest {

    @Test
    public void whenSearch8ThenIndex7() {
        Integer[] numbers = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        assertEquals(9, ParallelSearch.search(numbers, 10));
    }

    @Test
    public void whenSearch1ThenIndex0() {
        Integer[] numbers = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        assertEquals(0, ParallelSearch.search(numbers, 1));
    }

    @Test
    public void whenSearch15ThenIndexMinus1() {
        Integer[] numbers = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        assertEquals(-1, ParallelSearch.search(numbers, 15));
    }
}