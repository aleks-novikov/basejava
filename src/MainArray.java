public class MainArray {
    public static void main(String[] args) {
        MainTestArrayStorage test = new MainTestArrayStorage(10000);
        Resume res1 = new Resume(1);
        Resume res2 = new Resume(2);
        Resume res3 = new Resume(3);

        test.save(res1.get_ID());
        test.save(res2.get_ID());
        test.save(res3.get_ID());
        test.getAll();
        test.size();

        test.get(1);
        test.get(5);

        test.delete(2);
        test.delete(5);

        test.getAll();
        test.clear();
        test.size();
    }
}
