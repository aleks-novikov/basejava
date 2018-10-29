package storage;

import model.AbstractSection;
import model.ContactType;
import model.Resume;
import model.SectionType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AbstractFileStorage extends AbstractStorage<File> {
    protected final File dir = new File(System.getProperty("System.get") + "/ResumeStorage/");
    protected File file;
    protected FileWriter writer;
    protected String fullFilePath;
    protected final String separator = System.getProperty("line.separator");

    public int size() {
        String[] list = dir.list();
        if (list == null) {
            throw
        }
        return Objects.requireNonNull(dir.list()).length;
    }

    public void clear() {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume resume, File fileName) {
        if (!fileName.exists()) {
            try {
                fileName.createNewFile();
                writer = new FileWriter(fileName, false);
                for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                    writer.write(entry.getKey() + ": " + entry.getValue() + separator);
                }

                for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                    writer.write(entry.getKey() + ": " + entry.getValue() + separator);
                }
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doDelete(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        }
        if (isExist(file)) {

            return
        }

//        for (File file : Objects.requireNonNull(dir.listFiles())) {
//            if (file.getName().equals(uuid)) {
//                return file;
//            }
//        }
        return null;

    }

    @Override
    protected void doUpdate(String newInfo, String uuid) {
        File curFile = doGet(uuid);
        if (curFile != null) {
            //доработать
        }
    }

    public List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void resumeSave(int index, Resume resume);

    protected abstract void resumeDelete(int index);
}

