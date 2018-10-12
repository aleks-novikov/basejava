package storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SortedArrayStorageTest.class,
        ArrayStorageTest.class,
        MapStorageTest.class,
        MapResumeStorageTest.class,
        ListStorageTest.class
})
public class AllStorageTests {
}
