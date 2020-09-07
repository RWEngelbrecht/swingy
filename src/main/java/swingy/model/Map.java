package swingy.model;

import java.util.ArrayList;
import java.util.Random;

public class Map {

    private int[][] map;
    private String lastMove = "None";

    public Map(int heroLevel, ArrayList<String> loadMap) {
		int size;
		if (loadMap == null) {
			size = (heroLevel-1)*5+10-(heroLevel%2);
			map = new int[size][size];
			this.populateMap();
		} else {
			size = loadMap.size();
			map = new int[size][size];
			this.rebuildMap(loadMap);
		}
    }

	private void rebuildMap(ArrayList<String> loadMap) {
		int size = getMapSize();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				map[i][j] = Integer.parseInt(loadMap.get(i).split(", ")[j]);
			}
		}
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
        int y = pos[0];
        int x = pos[1];
        lastMove = direction;

        if (direction.equalsIgnoreCase("north")) {
            if (y - 1 >= 0) {
                locationStatus = map[y - 1][x];
            }
        } else if (direction.equalsIgnoreCase("east")) {
            if (x + 1 <= size -1) {
                locationStatus = map[y][x + 1];
            }
        } else if (direction.equalsIgnoreCase("south")) {
            if (y + 1 <= size -1) {
                locationStatus = map[y + 1][x];
            }
        } else if (direction.equalsIgnoreCase("west")) {
            if (x - 1 >= 0) {
                locationStatus = map[y][x - 1];
            }
        }
        if (locationStatus == 0 || locationStatus == 3) {
            updateMap();
        }
        return locationStatus;
    }

    public void updateMap() {
        int size = getMapSize();
        int[] lastPos = getHeroPosition(size);
        int y = lastPos[0];
        int x = lastPos[1];

        map[y][x] = 0;
        if (lastMove.equalsIgnoreCase("north")) {
            map[y - 1][x] = 1;
        } else if (lastMove.equalsIgnoreCase("east")) {
            map[y][x + 1] = 1;
        } else if (lastMove.equalsIgnoreCase("south")) {
            map[y + 1][x] = 1;
        } else if (lastMove.equalsIgnoreCase("west")) {
            map[y][x - 1] = 1;
        }
    }
}
