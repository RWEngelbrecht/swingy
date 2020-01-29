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

    private int[] getHeroPosition(int size) {

        int[] pos = new int[2];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == 1) {
                    pos[0] = i;
                    pos[1] = j;
                }
            }
        }
        return pos;
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

    public int moveHero(String direction) {
        int size = getMapSize();
        int locationStatus = -1;
        int[] pos = getHeroPosition(size);
        System.out.println("Map: pos of hero: y: "+pos[0]+" x: "+pos[1]);
        if (direction.equalsIgnoreCase("north")) {
            if (pos[0] + 1 < size)
                locationStatus = map[pos[0] - 1][pos[1]];
        }
        else if (direction.equalsIgnoreCase("east")) {
            if (pos[1] + 1 < size)
                locationStatus = map[pos[0]][pos[1] + 1];
        }
        else if (direction.equalsIgnoreCase("south")) {
            if (pos[0] - 1 > 0)
                locationStatus = map[pos[0] + 1][pos[1]];
        }
        else if (direction.equalsIgnoreCase("west")) {
            if (pos[1] - 1 > 0)
                locationStatus = map[pos[0]][pos[1] - 1];
        }
        return locationStatus;
    }
}
