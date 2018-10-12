package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> list = new ArrayList<>();

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        return null;
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        list.set((int) index, resume);
    }

    @Override
    protected boolean isExist(Object index) {
        return index != null;
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        list.add(resume);
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
        list.clear();
    }

    @Override
    public List<Resume> getListStorage() {
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }
}
