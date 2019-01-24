package storage;

import util.Config;

import java.util.Properties;

public class SqlStorageTest extends AbstractStorageTest {
    private static final Properties props = Config.get().getProps();

    public SqlStorageTest() {
        super(new SqlStorage(
                props.getProperty("url"),
                props.getProperty("user"),
                props.getProperty("password")));
    }
}
