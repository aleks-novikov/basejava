package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.*;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private Resume resume1 = new Resume(UUID_1);
    private Resume resume2 = new Resume(UUID_2);
    private Resume resume3 = new Resume(UUID_3);
    private Resume resume4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void save() {
        storage.save(resume4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveException() {
        storage.save(resume3);
    }

    @Test
    public void delete() {
        storage.delete("uuid1");
        storage.delete("uuid3");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteException() {
        storage.delete("uuid4");
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        storage.get("uuid1");
    }

    @Test(expected = NotExistStorageException.class)
    public void getException() {
        storage.get("uuid4");
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        storage.update(resume1);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateException() {
        storage.delete("uuid1");
        storage.update(resume1);
    }

    @Test
    public void getAll() {
        assertEquals(3, storage.getAll().length);
        Resume[] curStorage = {resume1, resume2, resume3};
        assertArrayEquals(curStorage, storage.getAll());
    }

    @Test(expected = AssertionError.class)
    public void saveOverflow() {
        storage.clear();
        for (int i = 0; i < 10_000; i++) {
            storage.save(new Resume());
        }
        try {
            storage.save(new Resume());
        } catch (StorageException e) {
            Assert.fail("Storage was overflowed");
        }
    }
}