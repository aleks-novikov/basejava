package storage;

import exception.NotExistStorageException;
import model.ContactType;
import model.Resume;
import sql.SqlHelper;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(String url, String user, String password) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(url, user, password));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r " +
                "LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                "WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }

            Resume resume = null;
            while (rs.next()) {
                resume = new Resume(uuid, rs.getString("full_name"));
                addContacts(rs, resume);
            }
            return resume;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE resume.uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                int result = ps.executeUpdate();
                if (result == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            insertContacts(conn, resume, "UPDATE contact SET type = ?, value = ? WHERE resume_uuid = ?");
            return null;
        });
    }

    private void insertContacts(Connection connection, Resume resume, String statement) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(statement)) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ps.setString(1, entry.getKey().getTitle());
                ps.setString(2, entry.getValue());
                ps.setString(3, resume.getUuid());
                ps.addBatch();  //операция добавляется к исполнению в connection, но не исполняется!
            }
            ps.executeBatch();  //выполнение всех команд за одно обращение к БД
        }
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        if (ps.executeUpdate() != 1) {
                            throw new NotExistStorageException(resume.getUuid());
                        }
                    }
                    insertContacts(conn, resume, "INSERT INTO contact (type, value, resume_uuid) VALUES (?,?,?)");
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE resume.uuid = ?", ps -> {
            ps.setString(1, uuid);
            int result = ps.executeUpdate();
            if (result == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM resume " +
                            "LEFT JOIN contact " +
                            "ON resume.uuid = contact.resume_uuid " +
                            "ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                List<Resume> resumes = new LinkedList<>();

                Resume resume;
                while (rs.next()) {
                    resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                    addContacts(rs, resume);
                    resumes.add(resume);
                }
                return resumes;
            }
        });
        return null;
    }

    private void addContacts(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        ContactType contactType = ContactType.valueOf(rs.getString("type"));
        resume.addContact(contactType, value);
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}