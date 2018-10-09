package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        String uuid = resume.getUuid();
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", uuid);
        } else {
            resumeSave((Integer) index, resume);
            size++;
        }
    }

    @Override
    protected void doDelete(Object index) {
        resumeDelete((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume doGet(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        storage[(Integer) index] = resume;
    }

    public List<Resume> getAllSorted() {
        Resume[] arr = new Resume[size];
        System.arraycopy(storage, 0, arr, 0, size);
        List <Resume> list = Arrays.asList(arr);
        return storageSort(list);
    }

    protected abstract Object getIndex(String uuid);

    protected abstract void resumeSave(int index, Resume resume);

    protected abstract void resumeDelete(int index);
}
