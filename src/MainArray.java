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
