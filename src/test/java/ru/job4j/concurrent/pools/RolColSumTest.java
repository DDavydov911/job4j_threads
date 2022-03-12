package ru.job4j.concurrent.pools;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void sum() {
        int[][] arr = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] sums = RolColSum.sum(arr);
        RolColSum.Sums sums1 = new RolColSum.Sums();
        sums1.setColSum(15);
        sums1.setRowSum(15);
        assertEquals(sums1, sums[1]);
    }

    @Test
    public void asyncSum() throws ExecutionException, InterruptedException {
        int[][] arr = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] sums = RolColSum.asyncSum(arr);
        RolColSum.Sums sums1 = new RolColSum.Sums();
        sums1.setColSum(15);
        sums1.setRowSum(15);
        assertEquals(sums1, sums[1]);
    }
}