package swingy.model;

import java.util.Random;

public class Dice {
    private static Random r = new Random();

    public int roll(String dice) {
        String[] amountAndDie = dice.split("d");
        String die = amountAndDie[1];
        int amount = Integer.parseInt(amountAndDie[0]);

        int roll = 1;
        // request random int from smaller range and add one, otherwise 1 is more likely to be generated than others
        switch (die) {
            case "100":
                for (int i = 0; i < amount; i++)
                    roll += r.nextInt(101 - 1);
                break;
            case "20":
                for (int i = 0; i < amount; i++)
                    roll += r.nextInt(21 - 1);
                break;
            case "12":
                for (int i = 0; i < amount; i++)
                    roll += r.nextInt(13 - 1);
                break;
            case "10":
                for (int i = 0; i < amount; i++)
                    roll += r.nextInt(11 - 1);
                break;
            case "6":
                for (int i = 0; i < amount; i++)
                    roll += r.nextInt(7 - 1);
                break;
            case "4":
                for (int i = 0; i < amount; i++)
                    roll += r.nextInt(5 - 1);
                break;
            case "2":
                for (int i = 0; i < amount; i++)
                    roll += r.nextInt(3 - 1);
                break;
        }
        return roll;
    }

    public int getZeroBound(int upperBound) {
        return r.nextInt(upperBound);
    }

}
