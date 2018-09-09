class ArrayStorage {
    private int storage[];

    ArrayStorage(int size) {
        storage = new int[size];
    }

    //сохранение значения в первый пустой элемент массива
    public void save(int val) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == 0) {
                storage[i] = val;
                return;
            }
        }
    }

    public void get(int id) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == id) {
                System.out.println("ID резюме с номером " + id + " равно " + id);
                return;
            }
        }
        System.out.println("Резюме с номером " + id + " не найдено!");
    }

    public void delete(int del_ID) {
        int elemToCopy = storage.length - del_ID;

        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == del_ID) {
                System.arraycopy(storage, del_ID, storage, del_ID - 1, elemToCopy);
                storage[storage.length - 1] = 0;
                System.out.println("Резюме с номером " + del_ID + " успешно удалено!");
                return;
            }
        }
        System.out.println("Резюме с номером " + del_ID + " не найдено!");
    }

    public int size() {
        int sum = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != 0) sum++;
        }
        return sum;
    }

    public void getAll() {
        System.out.println("\nТекущие данные в массиве: ");
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != 0) {
                System.out.println(i + 1 + "-е резюме имеет ID=" + storage[i]);
            }
        }
        System.out.println();
    }

    public void clear() {
        for (int i = 0; i < storage.length; i++) storage[i] = 0;
    }
}
