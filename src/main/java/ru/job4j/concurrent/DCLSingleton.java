package ru.job4j.concurrent;

public final class DCLSingleton {

    private static DCLSingleton inst;

    public static DCLSingleton instOf() {
        /*if (inst == null) {   Это условие может проверяться
        сразу несколькими потоками, этот код не синхронизирован.
        Нужен только код следующий ниже.*/
            synchronized (DCLSingleton.class) { /* А этот код синхронизирует единственный объект класса*/
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            /*}*/
        }
        return inst;
    }

    private DCLSingleton() {
    }

}