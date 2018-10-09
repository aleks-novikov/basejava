package storage;

import exception.StorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class OverFlowTest extends MainTest {
    public OverFlowTest(Storage storage){
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("Name" + i));
            }
        } catch (StorageException e) {
            Assert.fail("Storage was overflowed");
        }
        storage.save(new Resume("OverflowName"));
    }
}
