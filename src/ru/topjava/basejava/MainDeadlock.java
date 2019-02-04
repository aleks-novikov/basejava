package ru.topjava.basejava;

public class MainDeadlock {
    private static int num = 0;
    private static final int LAPS_AMOUNT = 10;
    private static final Object O1 = new Object();
    private static final Object O2 = new Object();

    public static void main(String[] args) {
        //при отсутствии deadlock значение num = LAPS_AMOUNT*4
        for (int i = 0; i < LAPS_AMOUNT; i++) {
            new Thread(() -> addValue("Thread 2")).start();
            addValue("Thread 1");
        }
    }

    private static void addValue(String threadName) {
        synchronized (threadName.equals("Thread 1") ? O1: O2) {
            System.out.println(threadName + ", value: " + ++num);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (threadName.equals("Thread 1") ? O2: O1) {
                System.out.println(threadName + ", value: " + ++num);
            }
        }
    }
}