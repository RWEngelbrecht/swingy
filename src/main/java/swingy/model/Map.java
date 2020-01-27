package swingy.model;

import java.util.Random;

public class Map {
    private int map[][];
    public Map(int heroLevel) {
        int size = (heroLevel-1)*5+10-(heroLevel%2);
        map = new int[size][size];
        this.populateMap();
    }

    public void populateMap() {
        // environment: 0; hero: 1; enemy: 2; artifact: 3;
        Random r = new Random();
        int size = getMapSize();

        map[size/2][size/2] = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] != 1) {
                    int nextRand = r.nextInt(3) + 1;
                    map[i][j] = (nextRand == 1) ? 0 : nextRand;
                }
            }
        }
    }

    public int getMapSize() {
        int count = 0;
        while (count < map.length) {
            count++;
        }
        return count;
    }

    public int[][] getMap() {
        return map;
    }

    public void moveHero(String direction) {
        int spaces = 0;
        if (direction.equalsIgnoreCase("north") || direction.equalsIgnoreCase("east"))
            spaces = 1;
        else if (direction.equalsIgnoreCase("south") || direction.equalsIgnoreCase("west"))
            spaces = -1;
        System.out.println("Map: spaces = "+spaces);
    }
}
