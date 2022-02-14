package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final long speed;
    private final String name;

    public Wget(String url, long speed, String name) {
        this.url = url;
        this.speed = speed;
        this.name = name;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(name)) {
            byte[] dataBuffer = new byte[1024];
            long bytesWrited = 0L;
            long deltaTime = 0L;
            int bytesRead;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long finish = System.currentTimeMillis();
                long diff = finish - start;
                deltaTime += diff;
                bytesWrited += bytesRead;
                if (bytesWrited >= speed) {
                    bytesWrited = 0L;
                    if (deltaTime < 1000) {
                        Thread.sleep(1000 - deltaTime);
                        deltaTime = 0L;
                    }
                }
                start = System.currentTimeMillis();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new InterruptedException();
        }
        String url = args[0];
        long speed = Long.parseLong(args[1]);
        String name = args[2];
        Thread wget = new Thread(new Wget(url, speed, name));
        wget.start();
        wget.join();
    }
}
