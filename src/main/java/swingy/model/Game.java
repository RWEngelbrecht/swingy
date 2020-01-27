package swingy.model;

import swingy.controller.GameController;
import swingy.model.characters.Hero;

// Keeps all information about current game i.e. the map, the hero
public class Game {
    private static GameController gameController;
    private static Hero hero;
    private static Map map;
    private static boolean isActive;
    public Game(GameController gameController) {
        isActive = true;
        this.gameController = gameController;
    }

    public void addHero(Hero hero) {
        this.hero = hero;
    }

    public void makeMap() {
        map = new Map(hero.getLevel());
    }

    public void moveHero(String direction) {
        map.moveHero(direction);
    }

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
}
