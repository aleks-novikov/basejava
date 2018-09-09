class ArrayStorage {
    private int storage[];
    ArrayStorage(int size) {
        storage = new int[size];
    }

    //сохранение значения в первый пустой элемент массива
    public void valSave(int val) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == 0) {
                storage[i] = val;
                return;
            }
        }
    }

    public int getVal(int i) {
        return storage[i];
    }

    public void delVal(int delInd) {
        int elemToCopy = storage.length - delInd;
        System.arraycopy(storage, delInd, storage, delInd-1, elemToCopy);
        storage[storage.length - 1] = 0;
    }

    public int getSize() {
        return storage.length;
    }

    public void get_AllInfo() {
        System.out.println("Текущие данные в массиве: ");
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] !=0) {
                System.out.println(i + 1 + "-й элемент массива равен " + storage[i]);
            }
        }
        System.out.println();
    }

    public void arrClear() {
        storage = null;
    }
}
