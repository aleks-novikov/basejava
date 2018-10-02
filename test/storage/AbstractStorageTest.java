package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import storageWorking.Storage;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest extends AbstractTest {

    protected AbstractStorageTest(Storage storage) {
        super(storage);
    }

    @Before
    protected void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Override
    @Test
    public void save() {
        int oldStorageSize = storage.size();
        storage.save(RESUME_4);
        assertEquals(RESUME_4, storage.get(UUID_4));
        assertEquals(oldStorageSize + 1, storage.size());
    }

    @Override
    @Test(expected = ExistStorageException.class)
    public void saveExistStorageException() {
        storage.save(RESUME_3);
    }

    @Override
    @Test(expected = NotExistStorageException.class)
    public void delete() {
        int oldStorageSize = storage.size();
        storage.delete(UUID_1);
        assertEquals(oldStorageSize - 1, storage.size());
        storage.get(UUID_1);
    }

    @Override
    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistStorageException() {
        storage.delete(UUID_4);
    }

    @Override
    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Override
    @Test(expected = NotExistStorageException.class)
    public void getNotExistStorageException() {
        storage.get(UUID_4);
    }

    @Override
    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Override
    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }

    @Override
    @Test(expected = NotExistStorageException.class)
    public void updateNotExistStorageException() {
        storage.update(RESUME_4);
    }

    @Ignore
    @Override
    @Test
    public void getAll() {
        assertEquals(3, storage.getAll().length);
        Resume[] curStorage = {RESUME_1, RESUME_2, RESUME_3};
        assertArrayEquals(curStorage, storage.getAll());
    }

    public abstract void saveOverflow();
}