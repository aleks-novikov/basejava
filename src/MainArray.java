/*
public class MainArray {
    public static void main(String[] args) {
        MainTestArrayStorage test = new MainTestArrayStorage();
        Resume res1 = new Resume("1");
        Resume res2 = new Resume("2");
        Resume res3 = new Resume("3");

        test.save(res1);
        test.save(res2);
        test.save(res3);

        //повторное сохранение резюме
        test.save(res3);

        test.update(res3);
        test.delete("3");

        //попытка обновить удалённое резюме
        test.update(res3);

        //попытка получить удалённое резюме
        test.get("3");
    }
}
*/

public class MainArray {
    public static void main(String[] args) {
        MainTestArrayStorage test = new MainTestArrayStorage();
        Resume res1 = new Resume("1");
        Resume res2 = new Resume("2");
        Resume res3 = new Resume("3");
        Resume res4 = new Resume("4");
        Resume res5 = new Resume("5");

        test.save(res1);
        test.save(res2);
        test.save(res3);
        test.save(res4);
        test.save(res5);
        test.save(res5);

        test.getAll();
        test.size();

        test.get("1");
        test.get("4");

        test.update(res1);

        test.delete("3");
        test.delete("5");
        test.update(res5);

        test.getAll();
        test.size();
        test.clear();
        test.size();
        test.getAll();
    }
}
