package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public boolean resumeIsExist(int index) {
        return index == 0;
    }

    @Override
    public void resumeSave(Resume[] storage, int index, int size, Resume r) {
        if (index < 0) {
            index = -(index + 1);
        }
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
    }

    @Override
    public void resumeDelete(Resume[] storage, int index, int size) {
        if (index == size - 1) {
            storage[index] = null;
        } else {
            System.arraycopy(storage, index + 1, storage, index, size - index);
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
