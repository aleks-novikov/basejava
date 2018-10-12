package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> map = new TreeMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void doUpdate(Resume resume, Object objResume) {
        map.replace(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(Object objResume) {
        return objResume != null;
    }

    @Override
    protected void doSave(Resume resume, Object objResume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object objResume) {
        return (Resume) objResume;
    }

    @Override
    protected void doDelete(Object objResume) {
        map.remove(((Resume)objResume).getUuid());
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> getListStorage() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }
}
