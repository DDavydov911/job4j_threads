package ru.job4j.concurrent;

public class ParallelSearch {
    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(3);
        final Thread producer = new Thread(
                () -> {
                    for (int index = 0; index < 3; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    Thread.currentThread().interrupt();
                }
        );
        final Thread consumer = new Thread(
                () -> {
                    while (!producer.isInterrupted()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    Thread.currentThread().interrupt();
                }
        );

        producer.start();
        consumer.start();
    }
}
