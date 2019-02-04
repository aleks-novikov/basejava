package ru.topjava.basejava.storage;

import ru.topjava.basejava.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void resumeSave(int index, Resume resume) {
        storage[size] = resume;
    }

    @Override
    public void resumeDelete(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
