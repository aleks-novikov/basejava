package ru.topjava.basejava.storage;

import ru.topjava.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void doUpdate(Resume resume, Resume objResume) {
        map.replace(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(Resume objResume) {
        return objResume != null;
    }

    @Override
    protected void doSave(Resume resume, Resume objResume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Resume objResume) {
        return objResume;
    }

    @Override
    protected void doDelete(Resume objResume) {
        map.remove(objResume.getUuid());
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }
}
