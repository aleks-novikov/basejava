package storage;
import model.Resume;
import storageWorking.AbstractArrayStorage;
import storageWorking.Storage;

public abstract class AbstractTest {
    int n = AbstractArrayStorage
    protected Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";
    protected static final Resume RESUME_1 = new Resume(UUID_1);
    protected static final Resume RESUME_2 = new Resume(UUID_2);
    protected static final Resume RESUME_3 = new Resume(UUID_3);
    protected static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractTest(Storage storage){
        this.storage = storage;
    }

    public abstract void save();

    public abstract void saveExistStorageException();

    public abstract void delete();

    public abstract void deleteNotExistStorageException();

    public abstract void get();

    public abstract void getNotExistStorageException();

    public abstract void clear();

    public abstract void update();

    public abstract void updateNotExistStorageException();

    public abstract void getAll();
}
