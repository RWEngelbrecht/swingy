package swingy.model.characters;

public class Warrior extends Hero {
    public Warrior(String name, String charClass) {
        super(name, charClass);
        this.xp = 0;
        this.hp = 40;
        this.lvl = 1;
        this.weapon = "Dual axes";
        this.armor = "Leather Loincloth";
        this.atk = 7;
        this.def = 4;
    }
}
