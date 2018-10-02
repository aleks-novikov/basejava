package storage;

import model.Resume;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> list = new LinkedList<>();

    protected Object getIndex(String uuid) {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        return null;
    }

    @Override
    protected void doUpdate(Resume r, Object index) {
        list.set((int) index, r);
    }

    @Override
    protected boolean isExist(Object index) {
        return index != null;
    }

    protected void doSave(Resume r, Object index) {
        list.add(r);
    }

    @Override
    protected Resume doGet(Object index) {
        return list.get((int) index);
    }

    @Override
    protected void doDelete(Object index) {
        list.remove((int) index);
    }

    @Override
    public void clear() {
        list.removeAll(list);
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return list.size();
    }
}
