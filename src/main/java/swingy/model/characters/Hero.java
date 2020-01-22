package swingy.model.characters;

// Superclass of all hero classes
public abstract class Hero {
    protected static String name;
    protected static String charClass;
    protected static int hp;
    protected static int xp;
    protected static int lvl;
    protected static int atk;
    protected static int def;

    public Hero(String name, String charClass) {
        this.name = name;
        this.charClass = charClass;
    }

    public String getName() { return this.name; }
    public String getHeroClass() { return this.charClass; }
    public abstract int getHp();
    public abstract int getLevel();
    public abstract int getXp();
    public abstract int getAtk();
    public abstract int getDef();

    public String getAllInfo() {
        String lvl = Integer.toString(this.getLevel());
        String xp = Integer.toString(this.getXp());
        String hp = Integer.toString(this.getHp());
        String atk = Integer.toString(this.getAtk());
        String def = Integer.toString(this.getDef());


        String allInfo = this.getName();
        allInfo = allInfo.concat(","+getHeroClass());
        allInfo = allInfo.concat(","+lvl);
        allInfo = allInfo.concat(","+xp);
        allInfo = allInfo.concat(","+hp);
        allInfo = allInfo.concat(","+atk);
        allInfo = allInfo.concat(","+def);

        return allInfo;
    }
}
//<name>,<class>,<xp>,<atk>,<def>,<weapon>,<armor>