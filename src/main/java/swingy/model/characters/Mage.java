package swingy.model.characters;

public class Mage extends Hero {
    public Mage(String name, String charClass) {
        super(name, charClass);
        this.xp = 0;
        this.hp = 40;
        this.lvl = 1;
        this.weapon = "Staff";
        this.armor = "Tunic";
        this.atk = 8;
        this.def = 3;
    }

}
