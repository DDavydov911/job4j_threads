package ru.job4j.concurrent.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch2<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T target;
    private final int from;
    private final int to;

    public ParallelSearch2(T[] array, T target, int from, int to) {
        this.array = array;
        this.target = target;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to + 1 - from <= 10) {
            for (int i = from; i <= to; i++) {
                if (array[i] == target) {
                    return i;
                }
            }
            return -1;
        }
        int mid = (from + to) / 2;
        ParallelSearch2<T> leftParallelSearch = new ParallelSearch2<>(array, target, from, mid);
        ParallelSearch2<T> rightParallelSearch = new ParallelSearch2<>(array, target, mid + 1, to);
        leftParallelSearch.fork();
        rightParallelSearch.fork();
        int leftResult = leftParallelSearch.join();
        int rightResult = rightParallelSearch.join();
        return (leftResult != -1) ? leftResult : rightResult;
    }

    public static <T> int search(T[] array, T target) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ParallelSearch2<T> ps = new ParallelSearch2<>(array, target, 0, array.length - 1);
        return forkJoinPool.invoke(ps);
    }

    public static void main(String[] args) {
        Integer[] numbers = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        System.out.println(ParallelSearch2.search(numbers, 9));
    }
}
