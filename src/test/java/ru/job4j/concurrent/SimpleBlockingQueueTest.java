package ru.job4j.concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class SimpleBlockingQueueTest {

    @Test
    public void offer() throws InterruptedException {
        var queue = new SimpleBlockingQueue<Integer>(3);

        Thread first = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Producer");

        Thread second = new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                try {
                    queue.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Consumer");

        first.start();
        second.start();
        first.join();
        second.join();
        List list = new ArrayList();
        for (int i = 0; i < 3; i++) {
             list.add(queue.poll());
        }
        assertEquals(List.of(3, 4, 5), list);
    }
}