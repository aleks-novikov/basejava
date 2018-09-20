import java.util.Arrays;

public interface Storage {
    void save(Resume resume);

    void update(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    int size();

    Resume[] getAll();

    void clear();
}
