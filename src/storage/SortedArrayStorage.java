package storage;

import model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    public static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override
    public void resumeSave(int index, Resume resume) {
        index = -(index + 1);
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
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
    protected Integer getIndex(String uuid) {
        Resume searchKey = new Resume(uuid, "No name");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }
}
