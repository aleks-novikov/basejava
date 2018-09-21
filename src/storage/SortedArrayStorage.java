package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    public boolean resumeIsExist(String uuid) {
        if ((getIndex(uuid)) == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void resumeDelete(Resume[] storage, int index, int size) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
