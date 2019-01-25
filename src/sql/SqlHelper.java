package sql;

import exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private static ConnectionFactory connectionFactory;

    public SqlHelper(String url, String user, String password) {
        connectionFactory = () -> DriverManager.getConnection(url, user, password);
    }

    public <T> T execute(String statement, SqlExecutor settings) {
        T result;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(statement)) {
            result = (T) settings.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return result;
    }
}