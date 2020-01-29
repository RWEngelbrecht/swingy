package swingy.model;

import swingy.controller.GameController;
import swingy.model.characters.Hero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

// Keeps all information about current game i.e. the map, the hero
public class Game {
    private static GameController gameController;
    private static Hero hero;
    private static Map map;
    private static ArrayList<ArrayList<String>> artifacts;
    private static ArrayList<String> enemies;
    private static String currArtifact;
    private static String currEnemy;
    private static boolean isActive;
    public Game(GameController gameController) {
        isActive = true;
        this.gameController = gameController;

        enemies = new ArrayList<String>(Arrays.asList(
                "Two-Toed Sloth", "Duck With a Knife", "Government Drone",
                "Your Demons", "Bag of Snapping Turtles"));

        artifacts = new ArrayList<ArrayList<String>>();

        ArrayList<String> weapons = new ArrayList<String>(Arrays.asList(
                "Spoon", "Brick", "Sharp Wit", "Lightsaber", "Pocket Knife"
        ));
        ArrayList<String> armors = new ArrayList<String>(Arrays.asList(
                "Iron Kettle Helm", "Self Respect", "Chainmail Slipper",
                "Steel Speedo", "Legendary Stone Mittens"
        ));

        artifacts.add(weapons);
        artifacts.add(armors);
    }

    public void addHero(Hero hero) {
        this.hero = hero;
    }

    public void makeMap() {
        map = new Map(1/*hero.getLevel()*/);
    }

    public int moveHero(String direction) {
        int locationStatus = map.moveHero(direction);
        if (locationStatus == 2) {
            currEnemy = generateEnemy();
        } else if (locationStatus == 3) {
            currArtifact = generateArtifact();
        }
        return locationStatus;
    }

    public String getCurrArtifact() { return currArtifact; }
    public String getCurrEnemy() { return currEnemy; }

    public void printMap() {
//        System.out.println("Game: mapsize = "+map.getMapSize());
        int mapRaw[][] = map.getMap();
        for (int i = 0; i < map.getMapSize(); i++) {
            for (int j = 0; j < map.getMapSize(); j++) {
                System.out.print(mapRaw[i][j]+" ");
            }
            System.out.print("\n");
        }
    }

    public Hero getHero() { return this.hero; }

    public static boolean gameIsActive() { return isActive; }

    public String generateEnemy() {
        Random r = new Random();

        return enemies.get(r.nextInt(enemies.size()));
    }

    private String generateArtifact() {
        Random r = new Random();

        return artifacts.get(r.nextInt(2)).get(r.nextInt(5));
    }

    public void fight() {
        // calculate result of battle
    }

    public void run() {
        // do not update map position
    }

    public void equipArtifact() {
        //TODO: Specific atk/def buffs/de-buffs depending on artifact;
        if (artifacts.get(0).contains(currArtifact)) {
            hero.equipWeapon(currArtifact, 1);
        } else {
            hero.donArmor(currArtifact, 1);
        }
    }
}
