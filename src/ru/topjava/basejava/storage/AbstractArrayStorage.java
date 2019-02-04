package ru.topjava.basejava.storage;

import ru.topjava.basejava.exception.StorageException;
import ru.topjava.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected void doSave(Resume resume, Integer index) {
        String uuid = resume.getUuid();
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", uuid);
        } else {
            resumeSave(index, resume);
            size++;
        }
    }

    @Override
    protected void doDelete(Integer index) {
        resumeDelete(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    protected void doUpdate(Resume resume, Integer index) {
        storage[index] = resume;
    }

    public List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void resumeSave(int index, Resume resume);

    protected abstract void resumeDelete(int index);
}
