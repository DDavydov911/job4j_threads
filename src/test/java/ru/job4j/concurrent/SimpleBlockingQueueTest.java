package ru.job4j.concurrent;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class SimpleBlockingQueueTest {

    @Test
    public void offer() throws InterruptedException {
        var queue = new SimpleBlockingQueue<Integer>(3);

        Thread first = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                queue.offer(i);
            }
        }, "Producer");

        Thread second = new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                queue.poll();
            }
        }, "Consumer");

        first.start();
        second.start();
        first.join();
        second.join();
        assertEquals(List.of(3, 4, 5), queue.getList());
    }
}