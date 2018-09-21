package storage;

import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void save(Resume r) {
        String uuid = r.getUuid();
        if (resumeIsExist(uuid) == true) {
            System.out.println("Resume c uuid номер " + uuid + " уже существует!");
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище заполнено!");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume c uuid номер " + uuid + " не найдено!");
        } else {
            resumeDelete(storage, index, size);
            size--;
        }
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else if (index < 0) {
            System.out.println("Resume c uuid номер " + index + " не найдено!");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract boolean resumeIsExist(String uuid);

    protected abstract void resumeDelete(Resume[] storage, int index, int size);
}
