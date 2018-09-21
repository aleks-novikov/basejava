package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        if ((getIndex(uuid)) == 0) {
            System.out.println("Resume c uuid номер " + uuid + " уже существует!");
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище заполнено!");
        } else {
            storage[size] = r;
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume c uuid номер " + uuid + " не найдено!");
        } else {
        }
        System.arraycopy(storage, index + 1, storage, index, STORAGE_LIMIT - index - 1);
        size--;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
