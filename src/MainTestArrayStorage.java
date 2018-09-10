public class MainTestArrayStorage {
    private ArrayStorage arr = new ArrayStorage();

    void save(String val) {
        arr.save(val);
    }

    void get(String val) {
        System.out.println("ID резюме с номером " + val + " равно " + arr.get(val));
    }

    void delete(String val) {
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
