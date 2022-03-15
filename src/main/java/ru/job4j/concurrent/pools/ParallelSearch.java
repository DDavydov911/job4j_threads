package ru.job4j.concurrent.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<T> {
    private final T[] array;
    private T target;
    private final int from;
    private final int to;
    public int index = -1;

    public ParallelSearch(T[] array, T target, int from, int to) {
        this.array = array;
        this.target = target;
        this.from = from;
        this.to = to;
    }

    @Override
    protected T compute() {
        if (to + 1 - from <= 10) {
            for (int i = from; i <= to; i++) {
                if (array[i] == target) {
                    index = i;
                    return target;
                }
            }
            return null;
        }
        int mid = (from + to) / 2;
        ParallelSearch<T> leftParallelSearch = new ParallelSearch<>(array, target, from, mid);
        ParallelSearch<T> rightParallelSearch = new ParallelSearch<>(array, target, mid + 1, to);
        leftParallelSearch.fork();
        rightParallelSearch.fork();
        T leftResult = leftParallelSearch.join();
        T rightResult = rightParallelSearch.join();
        if (leftResult != null) {
            this.index = leftParallelSearch.index;
            return leftResult;
        }
        if (rightResult != null) {
            this.index = rightParallelSearch.index;
            return rightResult;
        }
        return null;
    }

    public static <T> int search(T[] array, T target) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ParallelSearch<T> ps = new ParallelSearch<>(array, target, 0, array.length - 1);
        forkJoinPool.invoke(ps);
        return ps.index;
    }

    public static void main(String[] args) {
        Integer[] numbers = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        System.out.println(ParallelSearch.search(numbers, 15));
    }
}
