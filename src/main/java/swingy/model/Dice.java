package swingy.model;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Dice {
    private static Random r = new Random();

    public int roll(@NotNull String die) {
        int roll = 1;
        // request random int from smaller range and add one, otherwise 1 is more likely to be generated than others
        if (die.equals("d100"))
            roll += r.nextInt(101 - 1);
        else if (die.equals("d20"))
            roll += r.nextInt(21 - 1);
        else if (die.equals("d12"))
            roll += r.nextInt(13 - 1);
        else if (die.equals("d10"))
            roll += r.nextInt(11 - 1);
        else if (die.equals("d6"))
            roll += r.nextInt(7 - 1);
        else if (die.equals("d4"))
            roll += r.nextInt(5 - 1);
        else if (die.equals("d2"))
            roll += r.nextInt(3 - 1);
        return roll;
    }

    public int getZeroBound(int upperBound) {
        return r.nextInt(upperBound);
    }

}
