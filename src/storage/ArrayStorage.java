package storage;

import model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public boolean resumeIsExist(int index) {
        return index != -1;
    }

    @Override
    public void resumeSave(Resume[] storage, int index, int size, Resume r) {
        storage[size] = r;
    }

    @Override
    public void resumeDelete(Resume[] storage, int index, int size) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
