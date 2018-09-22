package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void resumeSave(int index, Resume r) {
        index = -(index + 1);
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
    }

    @Override
    public void resumeDelete(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, numMoved);
        } else if (index == size - 1) {
            storage[index] = null;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
