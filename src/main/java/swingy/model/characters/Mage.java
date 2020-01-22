package swingy.model.characters;

public class Mage extends Hero {
    public Mage(String name, String charClass) {
        super(name, charClass);
        this.xp = 0;
        this.hp = 40;
        this.lvl = 1;
        this.atk = 8;
        this.def = 3;
    }

    public int getHp() { return this.hp; }
    public int getLevel() { return this.lvl; }
    public int getXp() { return this.xp; }
    public int getAtk() { return this.atk; }
    public int getDef() { return this.def; }

}
