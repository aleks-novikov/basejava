package sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlSettings<T> {
    T setSettings(PreparedStatement ps) throws SQLException;
}