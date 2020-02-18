package swingy.model;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Dice {
    private static Random r = new Random();

    public int roll(@NotNull String dice) {
        String amountAndDie[] = dice.split("d");
        String die = amountAndDie[1];
        int amount = Integer.parseInt(amountAndDie[0]);

        int roll = 1;
        // request random int from smaller range and add one, otherwise 1 is more likely to be generated than others
        if (die.equals("100"))
            for (int i = 0; i < amount; i++)
                roll += r.nextInt(101 - 1);
        else if (die.equals("20"))
            for (int i = 0; i < amount; i++)
                roll += r.nextInt(21 - 1);
        else if (die.equals("12"))
            for (int i = 0; i < amount; i++)
                roll += r.nextInt(13 - 1);
        else if (die.equals("10"))
            for (int i = 0; i < amount; i++)
                roll += r.nextInt(11 - 1);
        else if (die.equals("6"))
            for (int i = 0; i < amount; i++)
                roll += r.nextInt(7 - 1);
        else if (die.equals("4"))
            for (int i = 0; i < amount; i++)
                roll += r.nextInt(5 - 1);
        else if (die.equals("2"))
            for (int i = 0; i < amount; i++)
                roll += r.nextInt(3 - 1);
        return roll;
    }

    public int getZeroBound(int upperBound) {
        return r.nextInt(upperBound);
    }

}
