package ru.job4j.concurrent.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch extends RecursiveTask<Integer> {
    private final int[] array;
    private final int num;
    private final int from;
    private final int to;

    public ParallelSearch(int[] array, int num, int from, int to) {
        this.array = array;
        this.num = num;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to + 1 - from <= 10) {
            for (int i = from; i <= to; i++) {
                if (array[i] == num) {
                    return i;
                }
            }
            return -1;
        }
        int mid = (from + to) / 2;
        ParallelSearch leftSearch = new ParallelSearch(array, num, from, mid);
        ParallelSearch rightSearch = new ParallelSearch(array, num, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        int leftResult = leftSearch.join();
        int rightResult = rightSearch.join();
        return (leftResult != -1) ? leftResult : rightResult;
    }

    public static int search(int[] array, int number) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch(array, number, 0, array.length - 1));
    }

    public static void main(String[] args) {
        int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        System.out.println(ParallelSearch.search(numbers, 1));
    }
}
