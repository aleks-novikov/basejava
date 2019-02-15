package ru.topjava.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.topjava.basejava.exception.ExistStorageException;
import ru.topjava.basejava.exception.NotExistStorageException;
import ru.topjava.basejava.model.Resume;
import ru.topjava.basejava.util.Config;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static ru.topjava.basejava.util.ResumeTestData.*;

public abstract class AbstractStorageTest {
    static final File STORAGE_DIR = Config.get().getStorageDir();
    protected Storage storage;
    private int oldStorageSize;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_3);
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        oldStorageSize = storage.size();
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertEquals(RESUME_4, storage.get(UUID_4));
        assertEquals(oldStorageSize + 1, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorageException() {
        storage.save(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(oldStorageSize - 1, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistStorageException() {
        storage.delete(UUID_4);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistStorageException() {
        storage.get(UUID_4);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1, "NewName");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistStorageException() {
        storage.update(RESUME_4);
    }

    @Test
    public void getAll() {
        assertEquals(3, storage.getAllSorted().size());
        assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), storage.getAllSorted());
    }

    @Test(expected = NullPointerException.class)
    public void setFullNameIncorrectNameException() {
        storage.save(new Resume("uuid5", null));
    }
}