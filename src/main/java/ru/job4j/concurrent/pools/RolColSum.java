package ru.job4j.concurrent.pools;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "rowSum: " + rowSum + "; colSum: " + colSum +";";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] arr = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            arr[i] = new Sums();
            arr[i].setColSum(getColSum(matrix, i));
            arr[i].setRowSum(getRowSum(matrix, i));
        }
        return arr;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] arr = new Sums[matrix.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = getTask(matrix, i).get();
        }
        return arr;
    }

    private static int getRowSum(int[][] arr, int i) {
        int sum = 0;
        for (int j = 0; j < arr.length; j++) {
            sum += arr[i][j];
        }
        return sum;
    }

    private static int getColSum(int[][] arr, int j) {
        int sum = 0;
        for (int[] ints : arr) {
            sum += ints[j];
        }
        return sum;
    }

    public static CompletableFuture<Sums> getTask(int[][] data, int i) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sums = new Sums();
            sums.setRowSum(getRowSum(data, i));
            sums.setColSum(getColSum(data, i));
            return sums;
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startSum = System.currentTimeMillis();
        Sums[] sums = RolColSum.sum(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        long finishSum = System.currentTimeMillis();
        System.out.println(finishSum - startSum);
        Arrays.stream(sums).forEach(System.out::println);

        long startAsyncSum = System.currentTimeMillis();
        Sums[] sumsAsync = RolColSum.asyncSum(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        long finishAsyncSum = System.currentTimeMillis();
        System.out.println(finishAsyncSum - startAsyncSum);
        Arrays.stream(sumsAsync).forEach(System.out::println);
    }
}

