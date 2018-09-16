class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void save(Resume resume) {
        String uuid = resume.getUuid();
        if ((returnResumeInd(uuid)) == -1) {
            if (size < storage.length) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("Resume " + resume + " не может быть сохранено. Хранилище заполнено!");
            }
        } else {
            System.out.println("Resume c uuid номер " + uuid + " уже существует!");
        }
    }

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int i = returnResumeInd(uuid);
        if (i != -1) {
            storage[i] = resume;
        } else {
            System.out.println("Resume c uuid номер " + uuid + " не найдено!");
        }
    }

    public Resume get(String uuid) {
        int i = returnResumeInd(uuid);
        if (i != -1) {
            return storage[i];
        }
        System.out.println("Resume c uuid номер " + uuid + " не найдено!");
        return null;
    }

    public void delete(String uuid) {
        int i = returnResumeInd(uuid);
        if (i != -1) {
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
        System.out.println("Резюме c uuid номер " + uuid + " не найдено!");
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

    private int returnResumeInd(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
