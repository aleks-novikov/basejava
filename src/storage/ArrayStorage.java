package storage;

import model.Resume;

import java.util.Arrays;

public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        String uuid = r.getUuid();
        if ((getIndex(uuid)) != -1) {
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
        if (index == -1) {
            System.out.println("Resume c uuid номер " + uuid + " не найдено!");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
