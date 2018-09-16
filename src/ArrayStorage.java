class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void save(Resume resume) {
        String uuid = resume.getUuid();
        if ((resumeIsExist(uuid)) == -1) {
            if (size < storage.length) {
                storage[size] = resume;
                size++;
                System.out.println("Резюме c uuid номер " + uuid + " успешно сохранено!");
            } else {
                System.out.println("Хранилище заполнено. Сохранение нового резюме невозможно!");
            }
        } else {
            System.out.println("Резюме c uuid номер " + uuid + " уже имеется в хранилище.");
        }
    }

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        if ((resumeIsExist(uuid)) != -1) {

            resume.setUuid(uuid);
            System.out.println("Резюме c uuid номер " + uuid + " успешно обновлено!");
        } else {
            System.out.println("Резюме c uuid номер " + uuid + " не найдено в хранилище.");
        }
    }

    public Resume get(String uuid) {
        if ((resumeIsExist(uuid)) != -1) {
            return storage[(resumeIsExist(uuid))];
        }
        System.out.println("Резюме c uuid номер " + uuid + " не найдено в хранилище");
        return null;
    }

    public void delete(String uuid) {
        if ((resumeIsExist(uuid) != -1)) {
            storage[(resumeIsExist(uuid))] = storage[size - 1];
            storage[size - 1] = null;
            System.out.println("Резюме c uuid номер " + uuid + " успешно удалено!");
            size--;
            return;
        }
        System.out.println("Резюме c uuid номер " + uuid + " не найдено в хранилище");
    }

    public int size() {
        return size;
    }

    public Resume[] getAll() {
        Resume allResumes[] = new Resume[size];
        for (int i = 0; i < size; i++) {
            allResumes[i] = storage[i];
        }
        return allResumes;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    private int resumeIsExist(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return  i;
            }
        }
        return -1;
    }
}
