package util;

public class DeadlockRealization {
    private static int num = 0;
    private static final int LAPS_AMOUNT = 10;
    private static final Object O1 = new Object();
    private static final Object O2 = new Object();

    public static void main(String[] args) {
        //при отсутствии deadlock значение num = LAPS_AMOUNT*4
        for (int i = 0; i < LAPS_AMOUNT; i++) {
            new Thread(() -> {
                synchronized (O1) {
                    System.out.println("First thread " + ++num);
                    synchronized (O2) {
                        System.out.println("First Thread " + ++num);
                    }
                }
            }).start();

            new Thread(() -> {
                synchronized (O2) {
//                    synchronized (O1) {    //если раскоментировать и убрать пред. блок synchronized
                    System.out.println("Second Thread " + ++num);
                    synchronized (O1) {
//                        synchronized (O2) {  //deadlock не возникнет
                        System.out.println("Second Thread " + ++num);
                    }
                }
            }).start();
        }
    }
}