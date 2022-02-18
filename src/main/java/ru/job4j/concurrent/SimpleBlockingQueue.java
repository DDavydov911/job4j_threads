package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final int limit;

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) {
        while (queue.size() == limit + 1) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.add(value);
        this.notifyAll();
    }

    public synchronized T poll() {
        while (queue.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        T item = queue.poll();
        this.notify();
        return item;
    }

    public synchronized List<T> getList() {
        return new LinkedList<>(queue);
    }

    public static void main(String[] args) throws InterruptedException {
        var sbq = new SimpleBlockingQueue(2);
        Thread first = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Offering " + i);
                sbq.offer(i);
                System.out.println("added " + i);
            }
        });

        Thread second = new Thread(() -> {
            for (int i = 1; i <= 2; i++) {
                System.out.println(sbq.poll());
            }
        });
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(sbq.getList());
    }
}
