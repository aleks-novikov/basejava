package ru.topjava.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        String filePath = "G:\\Java\\Projects\\basejava\\basejava.iml";
        File file = new File(filePath);

        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("G:\\Java\\Projects\\basejava\\");
        System.out.println(dir.isDirectory());

        String[] list = dir.list();
        if (list != null) {
            System.out.println("Список файлов:");
            for (String name : list) {
                System.out.println(name);
            }
        }

        //все ресурсы, которые помещаются в блок try, должны наследовать интерфейс AutoClosable
        try (FileInputStream fis = new FileInputStream(filePath)) {

            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
