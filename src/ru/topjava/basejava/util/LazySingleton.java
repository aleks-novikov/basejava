package ru.topjava.basejava.util;

public class LazySingleton {
    volatile private static LazySingleton instance;

    //паттерн Double checked locking
    public static LazySingleton getInstance() {
        if (instance == null) {
            //потоки встают в очередь для создания instance
            synchronized (LazySingleton.class) {
                //в данной точке экземпляр instance может быть уже создан
                //одним из потоков, поэтому требуется проверка
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }

    //лучшая замена Double checked locking. "Ленивый" синглетон - загрузка класса по требованию
    private static class LazySingletonHolder {
        //пока поле final не проинициализировано, класс не будет виден!
        private static final LazySingleton instance = new LazySingleton();
    }

    public static LazySingleton lazySingletonGetter() {
        return LazySingletonHolder.instance;
    }
}