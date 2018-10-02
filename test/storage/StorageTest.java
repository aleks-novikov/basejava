package storage;

import exception.StorageException;
import model.Resume;
import org.junit.Assert;

public class StorageTest extends MainTest {
    public StorageTest() {
        super(new ArrayStorage());
    }

    @org.junit.Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Storage was overflowed");
        }
        storage.save(new Resume());
    }

}
