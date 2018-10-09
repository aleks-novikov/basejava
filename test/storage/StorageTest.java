package storage;

import exception.StorageException;
import org.junit.Test;

public class StorageTest extends OverFlowTest {
    public StorageTest() {
        super(new ArrayStorage());
    }
}
