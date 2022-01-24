package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000);
        progress.interrupt();
    }

    @Override
    public void run() {
        String[] arr = {"\\", "|", "/", "-"};
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (String s : arr) {
                    System.out.print("\r load: " + s);
                    Thread.sleep(250);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

