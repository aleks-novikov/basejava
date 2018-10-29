import java.io.File;

public class AllDirectoryFilesShow {
    public static void main(String[] args) {
        File dir = new File(System.getProperty("user.dir"));
        if (dir.isDirectory()) {
            System.out.println("Имена файлов в каталогах и подкаталогах basejava:");
            filesShow(dir);
        }
    }

    private static void filesShow(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File fileName : files) {
                if (fileName.isDirectory() && fileName.getParent().equals(System.getProperty("user.dir"))) {
                    filesShow(fileName);
                } else if (fileName.isFile()) {
                    System.out.println(fileName);
                }
            }
        }
    }
}
