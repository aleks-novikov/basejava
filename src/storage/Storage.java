package storage;

import model.ContactType;
import model.Resume;
import model.SectionType;

import java.util.List;

public interface Storage {

    void clear();

    void update(Resume resume);

    void save(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();

    void addResumeContacts(String uuid, ContactType key, String info);

    void getResumeContacts(String uuid);

    void addResumeSectionsData(String uuid, SectionType sectionType, String info);
}
