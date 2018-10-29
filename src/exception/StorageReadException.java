package exception;

public class StorageReadException extends StorageException {
    public StorageReadException(String message, String uuid) {
        super(message, uuid);
    }
}
