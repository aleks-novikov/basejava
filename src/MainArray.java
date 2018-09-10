public class MainArray {
    public static void main(String[] args) {
        MainTestArrayStorage test = new MainTestArrayStorage();
        Resume res1 = new Resume("1");
        Resume res2 = new Resume("2");
        Resume res3 = new Resume("3");
        Resume res4 = new Resume("4");
        Resume res5 = new Resume("5");

        test.save(res1.getUuid());
        test.save(res2.getUuid());
        test.save(res3.getUuid());
        test.save(res4.getUuid());
        test.save(res5.getUuid());
        test.getAll();
        test.size();

        test.get("1");
        test.get("4");

        test.delete("3");
        test.delete("5");

        test.getAll();
        test.size();
        test.clear();
        test.size();
        test.getAll();
    }
}
