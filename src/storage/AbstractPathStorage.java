/*
package storage;

import exception.StorageException;
import model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    public AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "isn't directory or  isn't writable");
        }
        this.directory = directory;
    }

    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Directory read error", null);
        }
        return list.length;
    }


    @Override
    protected boolean isExist(Path file) {
        return file.exists();
    }

    @Override
    protected void doDelete(Path file) {
        if (!file.delete()) {
            throw new StorageException("Path deleting exception", file.getName());
        }
    }

    @Override
    protected void doSave(Resume r, Path file) {
        try {
            file.createNewPath();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName());
        }
        doUpdate(r, file);
    }

    @Override
    protected void doUpdate(Resume resume, Path file) {
        try {
            doWrite(resume, new BufferedOutputStream(new PathOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Path writing error!", resume.getUuid());
        }
    }

    public List<Resume> getAll() {
        Path files[] = directory.listPaths();
        if (files == null) {
            throw new StorageException("Path storage is null!", directory.getName());
        }
        List<Resume> list = new ArrayList<>(files.length);
        for (Path file : files) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return doRead(new BufferedInputStream(new PathInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Path reading error!", file.getName());
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return new Path(directory, uuid);
    }

    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;
}*/
