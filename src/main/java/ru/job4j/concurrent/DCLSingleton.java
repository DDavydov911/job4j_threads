package ru.job4j.concurrent;

public final class DCLSingleton {

    private static volatile DCLSingleton inst; /* Переменная была не синхронизирована. Из-за этого
     возможна ситуация, при которой в момент создания объекта, другой поток может получить
     и начать использовать не полностью сконструированный объект.*/

    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }

    private DCLSingleton() {
    }

}