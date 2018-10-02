package storage;

import storageWorking.ArrayStorage;

//public class StorageTest extends AbstractStorageTest {
public class StorageTest extends StoragesCommonTest {
    public StorageTest() {
        super(new ArrayStorage());
    }
}