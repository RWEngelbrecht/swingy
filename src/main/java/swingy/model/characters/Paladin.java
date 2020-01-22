package swingy.model.characters;

public class Paladin extends Hero {
    public Paladin(String name, String charClass) {
        super(name, charClass);
        this.xp = 0;
        this.hp = 40;
        this.lvl = 1;
    }

    public int getHp() { return this.hp; }
    public int getLevel() { return this.lvl; }
}
