package ru.topjava.basejava.exception;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(Exception e) {
        this(e.getMessage(), e);
    }

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }

    public StorageException(String message, Exception e) {
        super(message, e);
        this.uuid = null;
    }

    public String getUuid() {
        return uuid;
    }
}
