package storage;

import exception.StorageException;
import model.Resume;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    protected BufferedWriter writer;
    protected final File dir = new File(System.getProperty("System.get") + "/ResumeStorage/");

    public int size() {
        return Objects.requireNonNull(dir.list()).length;
    }

    public void clear() {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new StorageException("File storage is null!", dir.getName());
        }
        for (File file : files) {
            file.delete();
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doDelete(File file) {
        if (isExist(file)) {
            file.delete();
        } else {
            throw new StorageException("File doesn't exist!", file.getName());
        }
    }

    @Override
    protected void doUpdate(Resume resumeInfo, File file) {
        if (isExist(file)) {
            try {
                writer = new BufferedWriter(new FileWriter(file, true));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new StorageException("File doesn't exist!", file.getName());
        }
    }

    public List<Resume> getAll() {
        File files[] = dir.listFiles();
        if (files == null) {
            throw new StorageException("File storage is null!", dir.getName());
        }
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw  new StorageException("File reading error!", file.getName());
        }
    }

    protected abstract File getSearchKey(String uuid);

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;
}