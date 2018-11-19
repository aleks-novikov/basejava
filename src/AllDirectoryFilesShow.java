import java.io.File;
import java.util.Objects;

public class AllDirectoryFilesShow {
    public static void main(String[] args) {
        File dir = new File(System.getProperty("user.dir"));
        System.out.println("Имена файлов в каталогах и подкаталогах basejava:");
        filesShow(dir, "", 0);
    }

    private static void filesShow(File folder, String indent, int nestingLevel) {
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if ((file.isDirectory()) && nestingLevel < 2) {
                System.out.println(indent + "D: " + file.getName());
                filesShow(file, indent + "  ", ++nestingLevel);
                nestingLevel--;
            } else if (file.isFile()) {
                System.out.println(indent + "F: " + file.getName());
            }
        }
    }
}