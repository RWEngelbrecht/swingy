package swingy.model.characters;

public class Warrior extends Hero {
    public Warrior(String name, String charClass) {
        super(name, charClass);
        this.xp = 0;
        this.hp = 40;
        this.lvl = 1;
    }

    public int getHp() { return this.hp; }
}
