package swingy.model.artifacts;

public class Weapon extends Artifact {
//    private int atk;

    public Weapon(String name) {
        super(name);
        if (name.equals("Spoon"))
            this.atk = 2;
        else if (name.equals("Brick"))
            this.atk = 3;
        else if (name.equals("Sharp Wit"))
            this.atk = 5;
        else if (name.equals("Lightsaber"))
            this.atk = 6;
        else if (name.equals("Pocket Knife"))
            atk = 4;
        else if (name.equals("Staff"))
            atk = 6;
        else if (name.equals("Greathammer"))
            atk = 5;
        else if (name.equals("Dual axes"))
            atk = 5;
    }

}
