package storage;

import model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void resumeSave(int index, Resume r) {
        storage[size] = r;
    }

    @Override
    public void resumeDelete(int index) {
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
