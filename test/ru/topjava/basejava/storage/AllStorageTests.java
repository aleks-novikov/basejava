package ru.topjava.basejava.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.topjava.basejava.util.JsonParserTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SortedArrayStorageTest.class,
        ArrayStorageTest.class,
        MapUuidStorageTest.class,
        MapResumeStorageTest.class,
        ListStorageTest.class,
        ObjectFileStorageTest.class,
        ObjectPathStorageTest.class,
        XmlPathStorageTest.class,
        JsonPathStorageTest.class,
        DataPathStorageTest.class,
        SqlStorageTest.class,
        JsonParserTest.class
})
public class AllStorageTests {
}