package swingy.model.characters;

// Superclass of all hero classes
public abstract class Hero {
    protected static String name;
    protected static String charClass;
    protected static int hp;
    protected static int xp;
    protected static int lvl;

    public Hero(String name, String charClass) {
        this.name = name;
        this.charClass = charClass;
    }

    public abstract int getHp();
    public int getLevel() { return this.lvl; }
}
