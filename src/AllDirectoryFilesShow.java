import java.io.File;
import java.util.*;

public class AllDirectoryFilesShow {
    private static File file;
    private static int nestingLevel = 0;

    public static void main(String[] args) {
        File dir = new File(System.getProperty("user.dir"));
        System.out.println("Имена файлов в каталогах и подкаталогах basejava:");
        filesShow(dir);
    }

    private static void filesShow(File dir) {
        int filesCounter = 0;
        Set<File> files = new HashSet<>(Arrays.asList(Objects.requireNonNull(dir.listFiles())));
        Set<File> folders = new HashSet<>();

        //вывод списка файлов данного каталога
        for (File file: files) {
            if (file.isFile()) {
                filesCounter++;
                if (filesCounter == 1) {  //если в нём есть хотя бы 1 файл, выводим имя каталога
                    System.out.println(dir);
                }
                System.out.println(getIndent(file.getParentFile()) + file.getName());  //вывод имени файла
            } else {
                folders.add(file);  //иначе это каталог, сохраняем её в коллекцию для последующего вывода её файлов
            }
        }

        //вывод списка файлов в каждом отд. каталоге
        for (File folder: folders) {
            if (nestingLevel < 2) {
                nestingLevel++;
                filesShow(folder);  //переход в каталог на уровень ниже
                nestingLevel--;
            }
        }
    }

    private static String getIndent(File folderPath) {
        int folderPathLength = folderPath.getAbsolutePath().length();
        StringBuilder sb = new StringBuilder(folderPathLength);
        for (int i = 0; i < folderPathLength; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}