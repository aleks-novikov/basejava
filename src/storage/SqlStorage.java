package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;
import org.postgresql.util.PSQLException;
import sql.ConnectionFactory;
import sql.SqlHelper;
import sql.SqlSettings;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private static ConnectionFactory connectionFactory;

    SqlStorage(String url, String user, String password) {
        connectionFactory = () -> DriverManager.getConnection(url, user, password);
    }

    @Override
    public void clear() {
        SqlHelper<Void> helper = new SqlHelper<>();
        helper.executeStatement(connectionFactory, "DELETE FROM RESUME", PreparedStatement::execute);
    }

    @Override
    public Resume get(String uuid) {
        SqlSettings<Resume> settings = (ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
        SqlHelper<Resume> helper = new SqlHelper<>();
        return helper.executeStatement(connectionFactory, "SELECT * FROM resume r WHERE r.uuid = ?", settings);
    }

    @Override
    public void update(Resume resume) {
        SqlSettings<Void> settings = (ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.setString(3, resume.getUuid());
            int result = ps.executeUpdate();
            if (result == 0) {
                throw new NotExistStorageException("Резюме с uuid " + resume.getUuid() + " не существует");
            }
            return null;
        });
        SqlHelper<Void> helper = new SqlHelper<>();
        helper.executeStatement(connectionFactory, "UPDATE resume SET (uuid, full_name) = (?, ?) WHERE resume.uuid = ?", settings);
    }

    @Override
    public void save(Resume resume) {
        SqlSettings<Void> settings = (ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());

            try {
                ps.executeUpdate();
            } catch (PSQLException e) {
                throw new ExistStorageException("Резюме с uuid " + resume.getUuid() + " уже существует в базе!");
            }
            return null;
        });
        SqlHelper<Void> helper = new SqlHelper<>();
        helper.executeStatement(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?,?)", settings);
    }

    @Override
    public void delete(String uuid) {
        SqlSettings<Void> settings = (ps -> {
            ps.setString(1, uuid);
            int result = ps.executeUpdate();
            if (result == 0) {
                throw new NotExistStorageException("Резюме с uuid " + uuid + " не существует");
            }
            return null;
        });
        SqlHelper<Void> helper = new SqlHelper<>();
        helper.executeStatement(connectionFactory, "DELETE FROM resume WHERE resume.uuid = ?", settings);
    }

    @Override
    public List<Resume> getAllSorted() {
        SqlSettings<List> settings = (preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name").trim()));
            }
            return resumes;
        });
        SqlHelper<List<Resume>> helper = new SqlHelper<>();
        return helper.executeStatement(connectionFactory, "SELECT uuid, full_name FROM resume ORDER BY uuid", settings);
    }

    @Override
    public int size() {
        SqlSettings<Integer> settings = (ps -> {
            int rowsCount = 0;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rowsCount++;
            }
            return rowsCount;
        });
        SqlHelper<Integer> helper = new SqlHelper<>();
        return helper.executeStatement(connectionFactory, "SELECT FROM resume", settings);
    }
}