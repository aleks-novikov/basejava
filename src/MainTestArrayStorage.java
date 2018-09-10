public class MainTestArrayStorage {
    private ArrayStorage arr = new ArrayStorage();

    void save(Resume resume) {
        arr.save(resume);
    }

    void get(String uuid) {
        System.out.println("Резюме с uuid номер " + uuid + " - " + arr.get(uuid));
    }

    void delete(String uuid) {
        arr.delete(uuid);
    }

    void getAll() {
        Resume resumes[] = arr.getAll();
        for (int i = 0; i < resumes.length; i++) {
            System.out.println(resumes[i]);
        }
    }

    void size() {
        System.out.println("На данный момент хранилище содержит данные о " + arr.size() + " резюме\n");
    }

    void clear() {
        arr.clear();
        System.out.println("Массив с данными был очищен!");
    }
}
