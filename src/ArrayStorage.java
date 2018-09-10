class ArrayStorage {
    private String[] storage = new String[10_000];
    private int resumeAmount = 0;

    public void save(String str) {
        storage[resumeAmount] = str;
        resumeAmount++;  //является одновременно и счётчиком кол-ва резюме, и адресом первого пустого эл-та массива
    }

    public String get(String uuid) {
        for (int i = 0; i < resumeAmount; i++) {
            if (storage[i] == uuid) {
                return storage[i];
            }
        }
        return "null";
    }

    public void delete(String delId) {
        int elemToCopy;
        for (int i = 0; i < resumeAmount; i++) {
            if (storage[i] == delId) {
                elemToCopy = storage.length - i - 1;
                System.arraycopy(storage, i + 1, storage, i, elemToCopy);
                storage[storage.length - 1] = null;
                System.out.println("Резюме с номером " + delId + " успешно удалено!");
                resumeAmount--;
                return;
            }
        }
        System.out.println("Резюме с номером " + delId + " не найдено!");
    }

    public int size() {
        return resumeAmount;
    }

    public String[] getAll() {
        return storage;
    }

    public void clear() {
        for (int i = 0; i < resumeAmount; i++) {
            storage[i] = null;
        }
        resumeAmount = 0;
    }
}
