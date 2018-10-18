package storage;

import model.Resume;
import java.util.List;

public interface Storage {

    void clear();

    void update(Resume resume);

    void save(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();

    void addResumeContacts(String uuid, String key, String value);

    void getResumeInfo(String uuid);
}
