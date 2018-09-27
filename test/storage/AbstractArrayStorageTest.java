package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.*;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    protected Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(boolean arrayIsSorted) {
        storage = (arrayIsSorted ? new SortedArrayStorage() : new ArrayStorage());
    }

    @Before
    public void setup() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @After
    public void dataClear() {
        storage.clear();
    }

    @Test(expected = ExistStorageException.class)
    public void save() {
        storage.save(new Resume(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid1");
        storage.delete("uuid3");
        storage.delete("uuid4");
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void get() {
        storage.delete("uuid4");
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void update() {
        storage.delete("uuid1");
        storage.update(new Resume("uuid1"));
    }

    @Test
    public void getAll() {
        assertEquals(3, storage.size());
        Resume[] curStorage = {new Resume(UUID_1),
                new Resume(UUID_2),
                new Resume(UUID_3)};
        assertArrayEquals(curStorage, storage.getAll());
    }

    @Test(expected = AssertionError.class)
    public void storageOverflowChecking() {
        storage.clear();
        for (int i = 0; i < storage.size(); i++) {
            storage.save(new Resume());
        }
        Assert.fail("Storage wasn't overflowed");

        try {
            storage.save(new Resume());
        } catch (StorageException e) {
            Assert.fail("Storage was overflowed");
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }
}