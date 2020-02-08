package swingy.model;

import swingy.model.characters.Hero;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataHandler {
    private static Map<String, String> menuMap = new HashMap<String, String>();

    public DataHandler() {
        menuMap.put("1", "Warrior");
        menuMap.put("2", "Mage");
        menuMap.put("3", "Paladin");
    }

    public String getClass(@NotNull String key) {
        return menuMap.get(key);
    }

    public File createSaveFile() {
        File saveFile = new File("./save.txt");

        try {
            saveFile.createNewFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveFile;
    }
    // Returns ArrayList of existing saves if they exist, else
    public ArrayList<String> buildSaveFile(File saveFile, String heroInfo) {
        BufferedReader reader;
        ArrayList<String> saveLines = new ArrayList<String>();
        String[] heroInfoPieces = heroInfo.split(",");
        String heroInfoName = heroInfoPieces[0];
        System.out.println("DataHandler: heroInfoName = "+heroInfoName);
        String line;
        boolean overwritten = false;
        try {
            reader = new BufferedReader(new FileReader(saveFile));
            while ((line = reader.readLine()) != null) {
                System.out.println("DataHandler: line = "+line);
                heroInfoPieces = line.split(",");
                if (heroInfoPieces[0].equals(heroInfoName)) {
                    saveLines.add(heroInfo);
                    overwritten = true;
                }
                else
                    saveLines.add(line);
            }
            if (saveLines.size() == 0 || !overwritten)
                saveLines.add(heroInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveLines;
    }

    public void saveGame(File saveFile, @NotNull Hero hero) {
        FileWriter fileWriter;
        String heroInfo = hero.getAllInfo();
        ArrayList<String> saveFileLines = buildSaveFile(saveFile, heroInfo);
        try {
            fileWriter = new FileWriter(saveFile);
            for (String save : saveFileLines) {
                fileWriter.write(save+"\n");
            }
            fileWriter.close();
            System.out.println("GUIController: hero allInfo = " +heroInfo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getPrintableSaves(File saveFile) {

        @NotNull
        ArrayList<String> saveGames = getSavedGames(saveFile);
        System.out.println("DataHandler: saveGames.size() == "+saveGames.size());
        if (saveGames.size() > 0) {
            ArrayList<String> guiSaveGames = new ArrayList<String>();
            String[] savePieces;
            String patchedPieces;

            for (String saveGame : saveGames) {
                if (saveGame.length() > 0)
                {
                    savePieces = saveGame.split(",");
                    patchedPieces = savePieces[0];
                    patchedPieces = patchedPieces.concat(" Class: "+savePieces[1]);
                    patchedPieces = patchedPieces.concat(" Level: "+savePieces[2]);
                    patchedPieces = patchedPieces.concat(" Exp: "+savePieces[3]);
                    patchedPieces = patchedPieces.concat(" Health: "+savePieces[4]);
                    patchedPieces = patchedPieces.concat(" Atk: "+savePieces[5]);
                    patchedPieces = patchedPieces.concat(" Def: "+savePieces[6]);

                    guiSaveGames.add(patchedPieces);
                }
            }
            return guiSaveGames;
        }
        return null;
    }

    public ArrayList<String> getSavedGames(File saveFile) {

        ArrayList<String> saveGames = new ArrayList<String>();
        String save;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(saveFile));
            while ((save = reader.readLine()) != null) {
                saveGames.add(save);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }

        return saveGames;
    }
}
