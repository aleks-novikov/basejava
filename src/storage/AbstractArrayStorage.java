package storage;

import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void save(Resume r) {
        String uuid = r.getUuid();
        int index = getIndex(uuid);
        if (index >= 0) {
            System.out.println("Resume c uuid номер " + uuid + " уже существует!");
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище заполнено!");
        } else {
            resumeSave(index, r);
            size++;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume c uuid номер " + uuid + " не найдено!");
        } else {
            resumeDelete(index);
            storage[size - 1] = null;
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
        } else {
            System.out.println("Resume c uuid номер " + index + " не найдено!");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void resumeSave(int index, Resume r);

    protected abstract void resumeDelete(int index);
}
