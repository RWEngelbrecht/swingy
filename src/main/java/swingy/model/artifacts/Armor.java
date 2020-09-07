package swingy.model.artifacts;

public class Armor extends Artifact {

    public Armor(String name) {
        super(name);
        if (name.equals("Iron Kettle Helm"))
            this.def = 3;
        else if (name.equals("Self Respect"))
            this.def = 5;
        else if (name.equals("Chainmail Slipper"))
            this.def = 2;
        else if (name.equals("Steel Speedo"))
            this.def = 6;
        else if (name.equals("Legendary Stone Mittens"))
            this.def = 7;
        else if (name.equals("Tunic"))
            this.def = 2;
        else if (name.equals("Metal Breastplate"))
            this.def = 5;
        else if (name.equals("Leather Loincloth"))
            this.def = 2;
    }
}
