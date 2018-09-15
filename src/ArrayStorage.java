class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void save(Resume resume) {
        String uuid = resume.getUuid();
        if (resumeIsExist(uuid) == false) {
            if (size < storage.length) {
                storage[size] = resume;
                size++;
                showMessage(4, uuid);
            } else {
                showMessage(5, "");
            }
        } else {
            showMessage(1, uuid);
        }
    }

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        if (resumeIsExist(uuid) == true) {

            //код для обновления данных резюме

            showMessage(2, uuid);
        } else {
            showMessage(0, uuid);
        }
    }

    public Resume get(String uuid) {
        if (resumeIsExist(uuid) == true) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    return storage[i];
                }
            }
        } else {
            showMessage(0, uuid);
        }
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                showMessage(3, uuid);
                size--;
                return;
            }
        }
        showMessage(0, uuid);
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

    public boolean resumeIsExist(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public void showMessage(int type, String uuid) {
        if (type < 5) {
            System.out.print("\nРезюме c uuid номер " + uuid);
            switch (type) {
                case 0:
                    System.out.print(" не найдено в хранилище.");
                    break;
                case 1:
                    System.out.print(" уже имеется в хранилище.");
                    break;
                case 2:
                    System.out.print(" успешно обновлено!");
                    break;
                case 3:
                    System.out.print(" успешно удалено!");
                    break;
                case 4:
                    System.out.print(" успешно сохранено!");

            }
        } else {
            System.out.println("Хранилище заполнено. Сохранение нового резюме невозможно!");
        }
    }
}
