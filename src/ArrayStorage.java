class ArrayStorage {
    private String[] storage = new String[10_000];
    private int resAmount = 0;

    public void save(String str) {
        storage[resAmount] = str;
        resAmount++;  //является одновременно и счётчиком кол-ва резюме, и адресом первого пустого эл-та массива
        return;
    }

    public String get(String uuid) {
        for (int i = 0; i < resAmount; i++) {
            if (storage[i] == uuid) {
                return storage[i];
            }
        }
        return "null";
    }

    public void delete(String delId) {
        int elemToCopy;
        for (int i = 0; i < resAmount; i++) {
            if (storage[i] == delId) {
                elemToCopy = storage.length - i - 1;
                System.arraycopy(storage, i + 1, storage, i, elemToCopy);
                storage[storage.length - 1] = null;
                System.out.println("Резюме с номером " + delId + " успешно удалено!");
                resAmount--;
                return;
            }
        }
        System.out.println("Резюме с номером " + delId + " не найдено!");
    }

    public int size() {
        return resAmount;
    }

    public void getAll() {
        System.out.println("\nТекущие данные в массиве: ");
        for (int i = 0; i < resAmount; i++) {
            System.out.print(storage[i] + " ");
        }
        System.out.println();
    }

    public void clear() {
        resAmount = 0;
        for (int i = 0; i < resAmount; i++) {
            storage[i] = null;
        }
    }
}
