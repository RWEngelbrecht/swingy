package swingy.model.characters;

public class Paladin extends Hero {
    public Paladin(String name, String charClass) {
        super(name, charClass);
        this.xp = 0;
        this.hp = 40;
        this.lvl = 1;
        this.atk = 6;
        this.def = 6;
    }

}
