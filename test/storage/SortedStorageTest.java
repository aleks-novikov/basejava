package storage;

import exception.StorageException;
import org.junit.Test;

public class SortedStorageTest extends OverFlowTest {
    public SortedStorageTest() {
        super(new SortedArrayStorage());
    }
}
