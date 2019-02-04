package ru.topjava.basejava;

import java.io.File;
import java.util.Objects;

public class AllDirectoryFilesShow {
    public static void main(String[] args) {
        File dir = new File(System.getProperty("user.dir"));
        System.out.println("Имена файлов в каталогах и подкаталогах basejava:");
        filesShow(dir, "");
    }

    private static void filesShow(File folder, String indent) {
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if ((file.isDirectory())) {
                System.out.println(indent + "D: " + file.getName());
                filesShow(file, indent + "  ");
            } else if (file.isFile()) {
                System.out.println(indent + "F: " + file.getName());
            }
        }
    }
}