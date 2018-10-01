package storage;

import model.Resume;
import exception.NotExistStorageException;
import exception.ExistStorageException;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        Object index = getExistedIndex(r.getUuid());
        doUpdate(r, index);
    }

    public void save(Resume r) {
        Object index = getNotExistedIndex(r.getUuid());
        doSave(r, index);
    }

    public void delete(String uuid) {
        Object index = getExistedIndex(uuid);
        doDelete(index);
    }

    public Resume get(String uuid) {
        Object index = getExistedIndex(uuid);
        return doGet(index);
    }

    private Object getExistedIndex(String uuid) {
        Object index = getIndex(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    private Object getNotExistedIndex(String uuid) {
        Object index = getIndex(uuid);
        if (isExist(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    protected abstract Object getIndex(String uuid);

    protected abstract void doUpdate(Resume r, Object index);

    protected abstract boolean isExist(Object index);

    protected abstract void doSave(Resume r, Object index);

    protected abstract Resume doGet(Object index);

    protected abstract void doDelete(Object index);
}
