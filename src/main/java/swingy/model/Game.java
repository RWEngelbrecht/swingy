package swingy.model;

import swingy.controller.GameController;
import swingy.model.characters.Hero;
import swingy.model.npcs.Mob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

// Keeps all information about current game i.e. the map, the hero
public class Game {
    private static GameController gameController;
    private static MobFactory mobFactory = new MobFactory();
    private static Hero hero;
    private static Map map;
    private static ArrayList<ArrayList<String>> artifacts;
    private static ArrayList<String> enemies;
    private static String currArtifact;
    private static Mob currMob;
    private static Dice dice = new Dice();
    private static boolean isActive;

    public Game(GameController gameController) {
        isActive = true;
        this.gameController = gameController;

        enemies = new ArrayList<String>(Arrays.asList(
                "Null Pointer Exception", "Duck With a Knife", "Government Drone",
                "Dalek", "Bag of Snapping Turtles"));

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
            currMob = generateEnemy();
        } else if (locationStatus == 3) {
            currArtifact = generateArtifact();
        }
        return locationStatus;
    }

    public String getCurrArtifact() { return currArtifact; }
    public String getCurrEnemy() { return currMob.getMobName(); }

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

    private Mob generateEnemy() {
        String mobString = enemies.get(dice.getZeroBound(enemies.size()));

        return mobFactory.newMob(mobString);
    }

    private String generateArtifact() {
        return artifacts.get(dice.getZeroBound(2)).get(dice.getZeroBound(5));
    }

    //TODO: make fight method
    public void fight() {
        // calculate result of battle
        int initiativeHero = dice.roll("d20");
        int initiativeMob = dice.roll("d20");
        while (initiativeHero == initiativeMob) {
            initiativeHero = dice.roll("d20");
            initiativeMob = dice.roll("d20");
        }

        if (currMob != null) {
            if (initiativeHero > initiativeMob)
                 hero.attack(currMob);
            else
                currMob.attack(hero);

        }
    }

    //TODO: make run method
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
