package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private Storage storage = new ArrayStorage();
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setup() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test (expected = ExistStorageException.class)
    public void save() throws Exception {
        storage.save(new Resume(UUID_3));
    }

    @Test (expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete("uuid4");
    }

    @Test
    public void size()throws Exception {
    }

    @Test
    public void get() throws Exception {
    }

    @Test
    public void clear() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void getAll() throws Exception {
    }

    @Test (expected = NotExistStorageException.class)
    public  void getNotExist()throws Exception {
        storage.get("dummy");
    }


}