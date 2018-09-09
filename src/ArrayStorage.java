class ArrayStorage {
    private String[] storage = new String[10_000];
    private int resAmount = 0;

    //сохранение значения в первый пустой элемент массива
    public void save(String str) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = str;
                resAmount++;
                return;
            }
        }
    }

    public void get(String id) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == id) {
                System.out.println("ID резюме с номером " + id + " равно " + id);
                return;
            }
        }
        System.out.println("null");
    }

    public void delete(String del_ID) {
        int elemToCopy;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == del_ID) {
                elemToCopy = storage.length - i-1;
                System.arraycopy(storage, i+1, storage, i, elemToCopy);
                storage[storage.length - 1] = null;
                System.out.println("Резюме с номером " + del_ID + " успешно удалено!");
                resAmount--;
                return;
            }
        }
        System.out.println("Резюме с номером " + del_ID + " не найдено!");
    }

    public int size() {
        if (resAmount == 0) {
            for (int i = 0; i < storage.length; i++) {
                if (storage[i] != null) {
                    resAmount++;
                }
            }
        }
        return resAmount;
    }

        public void getAll () {
            System.out.println("\nТекущие данные в массиве: ");
            for (int i = 0; i < storage.length; i++) {
                if (storage[i] != null) {
                    System.out.print(storage[i] + " ");
                }
            }
            System.out.println();
        }

        public void clear () {
            resAmount = 0;
            for (int i = 0; i < storage.length; i++) {
                storage[i] = null;
            }
        }
    }
