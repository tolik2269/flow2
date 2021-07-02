package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {


    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        File myFile = new File("C://Users//Anato//Рабочий стол//задчи по джаве//Games//savegames//save3.dat");
        try {
            if (myFile.createNewFile()) ;
            sb.append("Файл был создан \n");
        } catch (IOException ex) {
            sb.append(ex.getMessage());
        }
        System.out.println(sb.toString());
        String absPath = ("C://Users//Anato//Рабочий стол//задчи по джаве//Games//savegames//zip.zip");
        List<String> list = new ArrayList<>();
        list.add(absPath);
    }

    public static void saveGame(String path, GameProgress game) {
        GameProgress gameProgress = new GameProgress(52, 65, 36, 25.6);
        try (FileOutputStream fos = new FileOutputStream("save3.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
    }

    public static void zipFiles(String pathZip, List<String> list) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream("zip_output.zip"));
             FileInputStream fis = new FileInputStream("notes.txt")) {
            ZipEntry entry = new ZipEntry("packed_notes.txt");
            zout.putNextEntry(entry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        File del = new File("C://Users//Anato//Рабочий стол//задчи по джаве//Games//temp//temp.txt");
        del.delete();

    }
}



