package swingy.model;

import swingy.model.characters.Hero;

import javax.validation.constraints.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DataHandler {
    private static Map<String, String> menuMap = new HashMap<String, String>();

    public DataHandler() {
        menuMap.put("1", "Warrior");
        menuMap.put("2", "Mage");
        menuMap.put("3", "Paladin");
    }

    public String getClass(String key) {
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
    public ArrayList<String> buildSaveFile(File saveFile, String heroInfo, String map) {
        @NotNull
        BufferedReader reader;
        String line;
        ArrayList<String> saveLines = new ArrayList<String>();
		String[] heroInfoPieces = heroInfo.split(",");
        String heroInfoName = heroInfoPieces[0];
		boolean overwritten = false;

        try {
			reader = new BufferedReader(new FileReader(saveFile));
            while ((line = reader.readLine()) != null) {
                if (heroInfoName.equals(line.split(",")[0])) {
                    saveLines.add(heroInfo+"\t"+map);
                    overwritten = true;
                }
                else
                    saveLines.add(line);
            }
            if (saveLines.size() == 0 || !overwritten)
                saveLines.add(heroInfo+"\t"+map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveLines;
    }

    public void saveGame(File saveFile, Hero hero) {
        FileWriter fileWriter;
		String heroInfo = hero.getAllInfo();
		int[][] map = Game.getMap();
		ArrayList<String> mapStrings = new ArrayList<String>();
		String mapMotherString = "";
		for (int[] mapRow : map) {
			mapStrings.add(Arrays.toString(mapRow).replaceAll("\\[|\\]", ""));
		}
		for (String mapRow : mapStrings) {
			mapMotherString += mapRow + "\t";
		}
        ArrayList<String> saveFileLines = buildSaveFile(saveFile, heroInfo, mapMotherString);
        try {
            fileWriter = new FileWriter(saveFile);
            for (String save : saveFileLines) {
                fileWriter.write(save+"\n");
            }
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getPrintableSaves(File saveFile) {

        @NotNull
        ArrayList<String> saveGames = getSavedGames(saveFile);
        if (saveGames.size() > 0) {
			ArrayList<String> guiSaveGames = new ArrayList<String>();
			String saveSansMap;
            String[] savePieces;
            String patchedPieces;

            for (String saveGame : saveGames) {
                if (saveGame.length() > 0)
                {
					saveSansMap = saveGame.split("\t")[0];
                    savePieces = saveSansMap.split(",");
                    patchedPieces = savePieces[0];
                    patchedPieces = patchedPieces.concat(" Class: "+savePieces[1]);
                    patchedPieces = patchedPieces.concat(" Level: "+savePieces[2]);
                    patchedPieces = patchedPieces.concat(" Exp: "+savePieces[3]);
                    patchedPieces = patchedPieces.concat(" Health: "+savePieces[4]);
                    patchedPieces = patchedPieces.concat(" Weapon: "+savePieces[7]);
                    patchedPieces = patchedPieces.concat(" Atk: "+savePieces[5]);
                    patchedPieces = patchedPieces.concat(" Armor: "+savePieces[8]);
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
			reader.close();
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }

        return saveGames;
    }
}
