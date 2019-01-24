package sql;

import exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper<T> {
    public T executeStatement(ConnectionFactory connectionFactory, String statement, SqlSettings settings) {
        T result;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(statement)) {
            result = (T) settings.setSettings(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return result;
    }
}