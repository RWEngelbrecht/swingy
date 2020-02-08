package swingy.model;

import swingy.model.npcs.*;
import swingy.model.npcs.NullPointerException;

public class MobFactory {
    public Mob newMob(String type) {
        if (type.equals("Bag Of Snapping Turtles"))
            return new BagOfSnappingTurtles();
        else if (type.equals("Dalek"))
            return new Dalek();
        else if (type.equals("Duck With a Knife"))
            return new DuckWithKnife();
        else if (type.equals("Government Drone"))
            return new GovernmentDrone();
        else
            return new NullPointerException();
    }
}
