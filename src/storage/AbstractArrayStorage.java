package storage;
import exception.StorageException;
import model.Resume;

import java.util.Arrays;

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
    protected boolean isExist (Object index){
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
//    public void delete(String uuid) {
    protected void doDelete(Object index) {
//        int index = (Integer) getIndex(uuid);
//        if (index < 0) {
//            throw new NotExistStorageException(uuid);
//        } else {
        resumeDelete((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
//    public Resume doGet(String uuid) {
    protected Resume doGet(Object index) {
//        int index = (Integer) getIndex(uuid);
//        if (index < 0) {
//            throw new NotExistStorageException(uuid);
//        }
//        return storage[index];
        return storage[(Integer)index];
    }

    @Override
//    public void update(Resume resume) {
    protected void doUpdate(Resume resume, Object index) {
//        int index = (Integer) getIndex(resume.getUuid());
//        if (index < 0) {
//            throw new NotExistStorageException(resume.getUuid());
//        } else {
//            storage[index] = resume;
            storage[(Integer) index] = resume;
//        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract Object getIndex(String uuid);

    protected abstract void resumeSave(int index, Resume resume);

    protected abstract void resumeDelete(int index);
}
