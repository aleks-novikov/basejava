package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> map = new TreeMap<>();

    @Override
    protected Resume getIndex(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void doUpdate(Resume r, Object resume) {
        map.replace(r.getUuid(), r);
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected void doSave(Resume r, Object resume) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void doDelete(Object resume) {
        map.remove(((Resume) resume).getUuid());
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        ArrayList list = new ArrayList(map.values());
        storageSort(list);
        return list;
    }

    @Override
    public int size() {
        return map.size();
    }
}
