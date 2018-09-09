public class MainTestArrayStorage {
    private ArrayStorage arr;
    MainTestArrayStorage (int size){
        arr = new ArrayStorage(size);
    }

    //задание значений массива
    void dataFill(int val) {
        for (int i = 0; i < val; i++) {
            if (i < arr.getSize() && arr.getVal(i) == 0) {
                arr.valSave(i + 1);
            }
        }
    }

    void getVal(int val) {
        System.out.println(val + "-й элемент массива равен " + arr.getVal(val - 1) + "\n");
    }

    void delVal(int val) {
        arr.delVal(val);
    }

    void get_allInfo() {
        arr.get_AllInfo();
        System.out.println();
    }

    void getSize() {
        System.out.println("Размер массива равен " + arr.getSize() + "\n");
    }

    void arrClear() {
        arr.arrClear();
        System.out.println("Массив с данными был очищен!");
    }
}
