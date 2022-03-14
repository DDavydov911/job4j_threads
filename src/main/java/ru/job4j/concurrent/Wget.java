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
            long bytesWritten = 0L;
            int bytesRead;
            long diff;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                bytesWritten += bytesRead;
                System.out.println(bytesWritten);
                if (bytesWritten >= 1048576) {
                    diff = System.currentTimeMillis() - start;
                    System.out.println(diff);
                    if (diff < 1000) {
                        try {
                            Thread.sleep(1000 - diff);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                    start = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void validate(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new InterruptedException();
        }
    }

    /* arguments: https://proof.ovh.net/files 1048576 10Mb.dat */
    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        long speed = Long.parseLong(args[1]);
        String name = args[2];
        Thread wget = new Thread(new Wget(url, speed, name));
        wget.start();
        wget.join();
    }
}
