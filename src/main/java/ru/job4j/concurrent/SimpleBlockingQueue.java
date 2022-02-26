package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final int limit;

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == limit) {
            this.wait();
        }
        queue.add(value);
        this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            this.wait();
        }
        T item = queue.poll();
        this.notify();
        return item;
    }

    public static void main(String[] args) throws InterruptedException {
        var sbq = new SimpleBlockingQueue(2);
        Thread first = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Offering " + i);
                try {
                    sbq.offer(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("added " + i);
            }
        });

        Thread second = new Thread(() -> {
            for (int i = 1; i <= 2; i++) {
                try {
                    System.out.println(sbq.poll());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        first.start();
        second.start();
        first.join();
        second.join();
    }
}
