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
/*
Михаил, добрый день! Здорово, что Вы пишете такие развернутые объяснения. Спасибо!
И у меня, конечно же, появились вопросы.
Со временем начала отсчета времени понятно - данные файла загружается
в момент инициализации объекта BufferedInputStream. И здесь вижу противоречие с
заданием - мы не можем ограничить эту скорость. Мы можем лишь ограничить скорость
полного чтения данных через буфер и время записи полного файла. Тем самым имитировать
ограничение скорости загрузки. Для определения скорости, конечно же нужно знать
помимо времени еще и объем переданных данных. И как Вы правильно заметили, скорость можно
регулировать изменением объема данных в 1 секунду. Это отличный способ.
Но есть и другой...
У нас есть буфер в 1024 байта, поэтому за один цикл больше не
запишется и не прочитается. Это объем данных. И есть время обработки этих 1024
байтов - время чтения в буфер и время записи в файл. Поэтому регулируя время обработки
этого буфера, мы тоже имитируем скорость загрузки. Поэтому можно ограничить скорость
ограничивая объем данных за 1 секунду, а можно ограничивать время пропуска 1024 байт. Что я и делал.
Таким образом, если нам нужна скорость не 1 КБ в секунду, как у меня было, а 1 МБ в секунду,
- то достаточно уменьшить время пропуска, в данном случае установить не 1000 мс,
а ~ 1 мс (1000/1024). Хотя это будет менеее точно. Или потребуется использовать наносекунды.
Пожалуйста, поправьте меня, если я не прав.
Далее вижу у себя такую ошибку:
сначала данные читаются в буфер, потом отмечается начало временного отрезка, потом
данные записываются в файл и отмечается время окончания временного отрезка. Фактически,
я измеряю только время записи данных, но не учитываю время чтения в буфер и время загрузки.
Ещё непонятна формулировка о том, что я "сравниваю и вычитаю разные величины".
Могли бы Вы объяснить, что Вы имели ввиду?
 */