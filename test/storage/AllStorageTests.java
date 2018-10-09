package storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Suite.SuiteClasses(
        {SortedStorageTest.class, StorageTest.class, MapStorageTest.class, MapResumeStorageTest.class, ListStorageTest.class})
@RunWith(Suite.class)
public class AllStorageTests {
}
