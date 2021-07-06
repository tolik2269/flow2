package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    private static final StringBuilder LOG = new StringBuilder("");

    public static void main(String[] args) {

        String userGameSavesDir = System.getProperty("user.home") + File.separator + "Games" + File.separator + "savegames";

        String game1SaveFile = userGameSavesDir + File.separator + "save1.dat";
        GameProgress gameFirst = new GameProgress(52, 65, 36, 25.6);
        saveGame(game1SaveFile, gameFirst);

        String game2SaveFile = userGameSavesDir + File.separator + "save2.dat";
        GameProgress gameSecond = new GameProgress(30, 100, 80, 13);
        saveGame(game2SaveFile, gameSecond);

        String game3SaveFile = userGameSavesDir + File.separator + "save3.dat";
        GameProgress gameThird = new GameProgress(80, 56, 45, 30);
        saveGame(game3SaveFile, gameThird);

        String gamesArchiveFile = userGameSavesDir + File.separator + "games.zip";
        zipFiles(gamesArchiveFile, game1SaveFile, game2SaveFile, game3SaveFile);
        System.out.println(LOG.toString());
    }

    public static void saveGame(String path, GameProgress game) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
            LOG.append("Game has been saved to file '").append(path).append("'\n");
        } catch (Exception ex) {
            LOG.append("Something went wrong on saving game. Exception: ").append(ex.getMessage()).append("\n");
        }
    }

    public static void zipFiles(String pathZip, String... filesToZip) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathZip))) {
            for (String saveGameFile: filesToZip) {
                try (FileInputStream fis = new FileInputStream(saveGameFile)) {
                    ZipEntry entry = new ZipEntry(new File(saveGameFile).getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                    LOG.append(saveGameFile).append(" has been written to zip file \n");
                } catch (Exception ex) {
                    LOG.append("Something went wraong on writing file ").append(saveGameFile).append(" tp zip archive ")
                            .append(pathZip).append(", exception: ").append(ex.getMessage()).append("\n");
                }
                new File(saveGameFile).delete();
                LOG.append("File ").append(saveGameFile).append(" has been deleted\n");
            }
        } catch (Exception ex) {
            LOG.append("Something went wrong on wroking with zip archive ").append(pathZip).append(", exception:")
                    .append(ex.getMessage()).append("\n");
        }

    }
}
