public class MainTestArrayStorage {
    private ArrayStorage arr;

    MainTestArrayStorage(int size) {
        arr = new ArrayStorage(size);
    }

    //задание значений массива
    void save(int val) {
        arr.save(val);
    }

    void get(int val) {
        arr.get(val);
    }

    void delete(int val) {
        arr.delete(val);
    }

    void getAll() {
        arr.getAll();
        System.out.println();
    }

    void size() {
        System.out.println("На данный момент хранилище содержит данные о " + arr.size() + " резюме\n");
    }

    void clear() {
        arr.clear();
        System.out.println("Массив с данными был очищен!");
    }
}
