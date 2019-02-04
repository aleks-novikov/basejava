package ru.topjava.basejava.storage;

import ru.topjava.basejava.exception.NotExistStorageException;
import ru.topjava.basejava.model.*;
import ru.topjava.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

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
        Resume resume = getResumeInfo("contact c ON r.uuid = c.resume_uuid", uuid, null);
        return getResumeInfo("section s ON r.uuid = s.resume_uuid", uuid, resume);
    }

    private Resume getResumeInfo(String statement, String uuid, Resume existingResume) {
        return sqlHelper.execute("SELECT * FROM resume r LEFT JOIN " + statement + " WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();

            if (statement.contains("contact") && !rs.next()) {
                throw new NotExistStorageException(uuid);
            }

            Resume resume = statement.contains("contact")
                    ? new Resume(uuid, rs.getString("full_name"))
                    : existingResume;

            if (statement.contains("contact")) {
                do {
                    addContacts(rs, resume);
                } while (rs.next());
            } else {
                while (rs.next()) {
                    addSections(rs, resume);
                }
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
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteData(conn, resume, "DELETE FROM contact WHERE contact.resume_uuid = ?");
            insertContacts(conn, resume);
            deleteData(conn, resume, "DELETE FROM section WHERE section.resume_uuid = ?");
            insertSections(conn, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    insertContacts(conn, resume);
                    insertSections(conn, resume);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE resume.uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> allResumes = sqlHelper.execute("SELECT uuid, full_name FROM resume ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return resumes;
        });

        Map<String, List> contacts = getData("contact");
        Map<String, List> sections = getData("section");

        List<Resume> sortedResume = new ArrayList<>();
        for (Resume resume : allResumes) {
            List<String> resumeContacts = contacts.get(resume.getUuid());
            for (String contact : resumeContacts) {
                String[] data = contact.split("\t");
                resume.addContact(ContactType.valueOf(data[0]), data[1]);
            }

            List<String> resumeSections = sections.get(resume.getUuid());
            for (String section : resumeSections) {
                String[] data = section.split("\t");
                SectionType type = SectionType.valueOf(data[0]);
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(type, new TextSection(data[1]));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        data = section.substring(section.indexOf("\t") + 1).split("\n");
                        resume.addSection(type, new ListSection(Arrays.asList(data)));
                }
            }
            sortedResume.add(resume);
        }
        return sortedResume;
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void addContacts(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSections(ResultSet rs, Resume resume) throws SQLException {
        if (rs.getString("type") != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.addSection(type, new TextSection(rs.getString("value")));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String[] values = rs.getString("value").split("\n");
                    resume.addSection(type, new ListSection(Arrays.asList(values)));
            }
        }
    }

    private void insertContacts(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();  //операция добавляется к исполнению в connection, но не исполняется!
            }
            ps.executeBatch();  //выполнение всех команд за одно обращение к БД
        }
    }

    private void insertSections(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                SectionType type = entry.getKey();
                ps.setString(2, type.name());

                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        ps.setString(3, entry.getValue().toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection values = (ListSection) entry.getValue();
                        StringBuilder sb = new StringBuilder();
                        for (String item : values.getItems()) {
                            sb.append(item + "\n");
                        }
                        ps.setString(3, sb.toString());
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteData(Connection connection, Resume resume, String statement) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(statement)) {
            ps.setString(1, resume.getUuid());
            ps.executeUpdate();
        }
    }

    private Map<String, List> getData(String tableName) {
        return sqlHelper.execute("SELECT resume_uuid, type, value FROM " + tableName, ps -> {
            ResultSet rs = ps.executeQuery();
            Map<String, List> map = new HashMap<>();

            while (rs.next()) {
                String uuid = rs.getString("resume_uuid");
                String data = rs.getString("type") + "\t" + rs.getString("value");
                List list = map.computeIfAbsent(uuid, c -> new ArrayList() {{
                    add(data);
                }});
                if (!list.contains(data)) {
                    list.add(data);
                }
            }
            return map;
        });
    }
}