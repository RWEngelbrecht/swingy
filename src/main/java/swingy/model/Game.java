package swingy.model;

import swingy.controller.GameController;
import swingy.model.artifacts.Armor;
import swingy.model.artifacts.Artifact;
import swingy.model.artifacts.Weapon;
import swingy.model.characters.Hero;
import swingy.model.npcs.Mob;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;

// Keeps all information about current game i.e. the map, the hero
public class Game {
    @NotNull
    private static GameController gameController;
    private static MobFactory mobFactory = new MobFactory();
    @NotNull
    private static Hero hero;
    private static Map map = null;
    @NotNull
    private static ArrayList<ArrayList<String>> artifacts;
    @NotNull
    private static ArrayList<String> enemies;
    private static Artifact currArtifact;
    private static Mob currMob;
    private static Dice dice = new Dice();
    @NotNull
    private static boolean isActive;
    private boolean onEnemy = false;

    public Game(GameController gameController) {
        isActive = true;
        Game.gameController = gameController;

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
        Game.hero = hero;
    }

    public void makeMap() {
		if (Game.map == null) {
			Game.map = new Map(hero.getLevel(), null);
		}
	}

	public void loadMap(ArrayList<String> map) {
		System.out.println("Game: calling loadMap()");
		Game.map = new Map(hero.getLevel(), map);
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

    public Artifact getCurrArtifact() { return currArtifact; }
    public String getCurrEnemy() { return currMob.getMobName(); }

    public void printMap() {
        int[][] mapRaw = map.getMap();
        for (int i = 0; i < map.getMapSize(); i++) {
            for (int j = 0; j < map.getMapSize(); j++) {
                System.out.print(mapRaw[i][j]+" ");
            }
            System.out.print("\n");
        }
	}

	public static int[][] getMap() {
		return map.getMap();
	}

    public Hero getHero() { return hero; }

    public int getHeroHp() { return hero.getHp(); }
    public int getMobHp() {
        if (currMob != null)
            return currMob.getHp();
        else
            return 0;
    }

    public static boolean gameIsActive() { return isActive; }

    private Mob generateEnemy() {
        String mobString = enemies.get(dice.getZeroBound(enemies.size()));
        onEnemy = true;

        return mobFactory.newMob(mobString);
    }

    private Artifact generateArtifact() {
        String artifactName = artifacts.get(dice.getZeroBound(2)).get(dice.getZeroBound(5));
        Artifact artifact;
        if (artifacts.get(0).contains(artifactName))
            artifact = new Weapon(artifactName);
        else
            artifact = new Armor(artifactName);
        return artifact;
    }

    public boolean isOnEnemy() { return onEnemy; }

    public String fight() {

        String outcome = "Absolutely nothing happened.";

        int initiativeHero = dice.roll("1d20");
        int initiativeMob = dice.roll("1d20");

        while (initiativeHero == initiativeMob) {
            initiativeHero = dice.roll("1d20");
            initiativeMob = dice.roll("1d20");
        }

        if (currMob != null) {

            int attackRoll = dice.roll("1d6");
            if (initiativeHero > initiativeMob)
                outcome = heroFightMob(attackRoll);
            else
                outcome = mobFightHero(attackRoll);
        }
        return outcome;
    }

    private String mobFightHero(int attackRoll) {
        int attackPower = currMob.attack(attackRoll);
        int heroDef = hero.getDef();

        int damage = attackPower - heroDef;
        if (damage > 0) {
            hero.hpDown(damage);
            if (hero.getHp() > 0) {
				System.out.println("You take "+damage+" damage. Maybe dodge next time?");
                return "You take "+damage+" damage. Maybe dodge next time?";
			}
            else {
                return "YOU DIED.";
            }
        } else {
            return "The "+currMob.getMobName()+" attacked and missed.";
        }
    }

    private String heroFightMob(int attackRoll) {
        int attackPower = hero.attack(attackRoll);
        int mobDef = currMob.getDef();

        int damage = attackPower - mobDef;
        if (damage > 0) {
            currMob.takeDamage(damage);
            if (getMobHp() > 0)
                return "You do " + Integer.toString(damage) + " damage to "+currMob.getMobName();
            else {
                onEnemy = false;
                map.updateMap();
                if (hero.xpUp(currMob.getXp())) {
                    gameController.levelUp();
                }
                return "You kill the " + currMob.getMobName() + ". It will be missed by its friends and family.\nYou also gain "+currMob.getXp()+" XP.";
            }
        } else {
            return "You attacked and missed...";
        }
    }

    public boolean run() {
		// do not update map position
		if (dice.roll("1d2") == 1)
			return true;
		this.mobFightHero(dice.roll("1d6"));
        return false;
    }

    public void equipArtifact() {
        if (artifacts.get(0).contains(currArtifact.getArtifactName())) {
            hero.equipWeapon(currArtifact, currArtifact.getAtk());
        } else {
            hero.donArmor(currArtifact, currArtifact.getDef());
        }
    }
}
