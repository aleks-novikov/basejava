package storage;

import model.Resume;
import java.util.Map;
import java.util.TreeMap;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> map = new TreeMap<>();

    @Override
    protected Object getIndex(String uuid) {
        return uuid;
    }

    @Override
    protected void doUpdate(Resume r, Object index) {
        map.replace((String) index, r);
    }

    @Override
    protected boolean isExist(Object index) {
        return map.containsKey(index);
    }

    @Override
    protected void doSave(Resume r, Object index) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object index) {
        return map.get(index);
    }

    @Override
    protected void doDelete(Object index) {
        map.remove(index);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return map.size();
    }
}
