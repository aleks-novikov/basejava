package ru.topjava.basejava;

import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10_000;
    private static int counter;
    private static final Object LOCK = new Object();
    private static final Lock REENTRANT_LOCK = new ReentrantLock();
    private final AtomicInteger atomicInteger = new AtomicInteger();
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"));

    public static void main(String[] args) throws IllegalStateException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                throw new IllegalStateException(); //при выбрасывании исключения в параллельном потоке работа основного потока не прерывается!
            }
        };
        thread0.start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())).start();
        System.out.println(thread0.getState());  //получение текущего состояния потока

        //первый способ запуска n потоков - последовательный запуск потоков, занесение каждого в лист
        // и ожидание завершения каждого из потоков с помощью join()

        /*final ru.topjava.basejava.MainConcurrency mainConcurrency = new ru.topjava.basejava.MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(counter);*/

        //второй способ: CountDownLatch и ExecutorService
        /*
        final ru.topjava.basejava.MainConcurrency mainConcurrency = new ru.topjava.basejava.MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);   //синхронизатор
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
                latch.countDown();   //уменьшение числа latch после выполнения действий в потоке
            });
            thread.start();
        }

        latch.await(1, TimeUnit.SECONDS);
        executorService.shutdown();    //завершение запуска и выполнения потоков
        System.out.println(counter);*/

        //третий способ - AtomicInteger
        final MainConcurrency mainConcurrency = new MainConcurrency();
        for (int i = 0; i < THREADS_NUMBER; i++) {
            for (int j = 0; j < 100; j++) {
                mainConcurrency.inc();
            }
        }
        System.out.println(mainConcurrency.atomicInteger.get());
    }

    //использование AtomicInteger (синхронизация в данном случае не требуется!):
    private void inc() {
        atomicInteger.incrementAndGet();
    }

    //способы синхронизации блока кода:
  /*  private synchronized void inc() {
//        synchronized (this) {                 //по экземпляру объекта класса, у которого вызван метод, если метод нестатический
//        synchronized (ru.topjava.basejava.MainConcurrency.class) {  //по классу, если метод статический
//            synchronized (LOCK) {               //по статич. объекту класса
        counter++;
//        }
    }*/

    //сихронизация с помощью Lock:
    /*private void inc() {
        REENTRANT_LOCK.lock();
        try {
            counter++;
        } finally {
            REENTRANT_LOCK.unlock();
        }
    }*/
}