package serialization;

import model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {
    public void doWrite(Resume resume, OutputStream os) throws IOException;
    public Resume doRead(InputStream is) throws IOException;
}
