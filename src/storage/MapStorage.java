package storage;

import model.Resume;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> map = new TreeMap<>();

    @Override
    protected Object getIndex(String uuid) {
        for (Map.Entry<String, Resume> resume : map.entrySet()) {
            if (resume.getKey().equals(uuid)) {
                return resume.getKey();
            }
        }
        return null;
    }

    @Override
    protected void doUpdate(Resume r, Object index) {
        map.put((String) index, r);
    }

    @Override
    protected boolean isExist(Object index) {
        return index != null;
    }

    @Override
    protected void doSave(Resume r, Object index) {
        map.put(r.getUuid(), r);
        System.out.println();
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
        Resume[] res =  new Resume[map.size()];
        map.values().toArray(res);
        return res;
     }

    @Override
    public int size() {
        return map.size();
    }
}
