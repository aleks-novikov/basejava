package storage;

import exception.StorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Test;

public class StorageTestMain extends MainStorageTestClass {
    public StorageTestMain() {
        super(new ArrayStorage());
    }

    @Test(expected = StorageException.class)
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
