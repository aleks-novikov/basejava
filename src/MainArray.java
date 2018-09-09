public class MainArray {
    public static void main(String[] args) {
        MainTestArrayStorage test = new MainTestArrayStorage(10000);
        test.dataFill(25);
        test.getSize();
        test.get_allInfo();

        test.getVal(5);
        test.delVal(3);
        test.delVal(6);
        test.delVal(8);

        test.get_allInfo();
        test.arrClear();
    }
}
