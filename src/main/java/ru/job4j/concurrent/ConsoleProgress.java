package ru.job4j.concurrent;

import org.junit.Rule;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        progress.interrupt();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\r load: " + "\\");
                Thread.sleep(250);
                System.out.print("\r load: " + "|");
                Thread.sleep(250);
                System.out.print("\r load: " + "/");
                Thread.sleep(250);
                System.out.print("\r load: " + "-");
                Thread.sleep(250);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

