package ru.job4j.concurrent;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private final List<Thread> threads;
    private int size;
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool() {
        threads = new LinkedList<>();
        size = Runtime.getRuntime().availableProcessors();
        tasks = new SimpleBlockingQueue<>(3);
        for (int i = 0; i < size; i++) {
            threads.add(
                    new Thread(
                        () -> {
                            try {
                                while (!Thread.currentThread().isInterrupted()) {
                                    tasks.poll().run();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    )
            );
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
        notifyAll();
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}