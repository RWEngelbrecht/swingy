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

    public String getClassFromGui(@NotNull String key) {
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

    public void saveGame(File saveFile, @NotNull Hero hero) {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(saveFile, true);
            fileWriter.write(hero.getAllInfo()+"\n");
            fileWriter.close();
            System.out.println("GUIController: hero allInfo = " +hero.getAllInfo());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getSavesForGui(File saveFile) {

        ArrayList<String> saveGames = getSavedGames(saveFile);
        ArrayList<String> guiSaveGames = new ArrayList<String>();
        String[] savePieces;
        String patchedPieces;

        for (String saveGame : saveGames) {
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

        return guiSaveGames;
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
