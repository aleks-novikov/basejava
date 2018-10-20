package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.ContactType;
import model.Resume;
import model.SectionType;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractArrayStorage.class.getName());

    protected static final Comparator<Resume> STORAGE_SORT =
            (Comparator.comparing(Resume::getFullName).
                    thenComparing(Resume::getUuid));

    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK searchKey = getExistedSearchKey(resume.getUuid());
        doUpdate(resume, searchKey);
    }

    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = getNotExistedSearchKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        doDelete(searchKey);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        return doGet(searchKey);
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted ");
        List<Resume> list = getAll();
        list.sort(STORAGE_SORT);
        return list;
    }

    public void addResumeContacts(String uuid, ContactType key, String value) {
        LOG.info("addResumeContacts " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        addResumeInfo(searchKey, key, value);
    }

    public void getResumeContacts(String uuid) {
        LOG.info("getResumeContact " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        getInfo(searchKey);
    }

    public void addResumeSectionsData (String uuid, SectionType sectionType, String info) {
        LOG.info("addResumeSectionsData " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        addSectionData(searchKey, sectionType, info);
    }

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " is already exist!");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " is not exist!");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract void addSectionData(SK searchKey, SectionType sectionType, String info);

    protected abstract void addResumeInfo(SK searchKey, ContactType key, String value);

    protected abstract void getInfo(SK searchKey);

    protected abstract List<Resume> getAll();

    protected abstract SK getSearchKey(String uuid);

    protected abstract void doUpdate(Resume resume, SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract void doSave(Resume resume, SK searchKey);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doDelete(SK searchKey);
}
