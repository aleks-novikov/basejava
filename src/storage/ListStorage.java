package storage;

import model.ContactType;
import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    protected List<Resume> list = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        return null;
    }

    @Override
    protected void doUpdate(Resume resume, Integer index) {
        list.set(index.intValue(), resume);
    }

    @Override
    protected boolean isExist(Integer index) {
        return index != null;
    }

    @Override
    protected void doSave(Resume resume, Integer index) {
        list.add(resume);
    }

    @Override
    protected Resume doGet(Integer index) {
        return list.get(index.intValue());
    }

    @Override
    protected void doDelete(Integer index) {
        list.remove(index.intValue());
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(list);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void addResumeInfo(Integer objResume, ContactType key, String value) {
        list.get(objResume).addResumeContacts(key, value);
    }

    @Override
    public void getInfo(Integer objResume) {
        list.get(objResume).getResumeContact();
    }

    @Override
    public void addSectionData(Integer objResume) {
        list.get(objResume).setSectionData();
    }

}