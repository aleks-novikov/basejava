package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> map = new HashMap<>();

    protected Object getIndex(String uuid) {
        for(Map.Entry<String, Resume> r : map.entrySet()) {
            if (r.getKey().equals(uuid)) {
                return r.getValue();
            }
        }
        return null;
    }

    @Override
    protected void doUpdate(Resume r, Object index) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected boolean isExist(Object index) {
        return index != null;
    }

    @Override
    protected void doSave(Resume r, Object index) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object index) {
        return map.get()
    }

    @Override
    protected void doDelete(Object index) {
        map.remove((int) index);
    }

    @Override
    public void clear() {
//        map.removeAll(map);
    }

    @Override
    public Resume[] getAll() {
//        return map.toArray(new Resume[map.size()]);
    }

    @Override
    public int size() {
        return map.size();
    }
}
