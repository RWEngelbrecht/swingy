package swingy.model.characters;

import swingy.model.artifacts.Artifact;
import swingy.model.npcs.Mob;

import java.util.Random;

// Superclass of all hero classes
public abstract class Hero {
    protected static String name;
    protected static String charClass;
    protected static Artifact weapon;
    protected static Artifact armor;
    protected static int hp;
    protected static int xp;
    protected static int lvl;
    protected static int atk;
    protected static int def;

    public Hero(String name, String charClass) {
        Hero.name = name;
        Hero.charClass = charClass;
    }

    public String getName() { return this.name; }
    public String getHeroClass() { return this.charClass; }
    public int getHp() { return this.hp; }
    public int getLevel() { return this.lvl; }
    public int getXp() { return this.xp; }
    public int getAtk() { return this.atk; }
    public int getDef() { return this.def; }

    public void setHp(int newHp) { hp = newHp; }
    public void setXp(int newXp) { xp = newXp; }
    public void setLvl(int newLvl) { lvl = newLvl; }
    public void setAtk(int newAtk) { atk = newAtk; }
    public void setDef(int newDef) { def = newDef; }

    public void hpUp(int increase) { hp += increase; }
    public void hpDown(int decrease) { hp -= decrease; }
    public void xpUp(int increase) { xp += increase; }
    public void lvlUp() { lvl++; }
    public void atkUp(int increase) { atk += increase; }
    public void atkDown(int decrease) { atk -= decrease; }
    public void defUp(int increase) { def += increase; }
    public void defDown(int decrease) { def -= decrease; }

    public void equipWeapon(Artifact weapon, int atkBoost) {
        atk -= Hero.weapon.getAtk();
        Hero.weapon = weapon;
        atk += atkBoost;
    }
    public void donArmor(Artifact armor, int defBoost) {
        def -= Hero.armor.getDef();
        Hero.armor = armor;
        def += defBoost;
    }

    public String getAllInfo() {
        String heroClass = getHeroClass();
        String lvl = Integer.toString(this.getLevel());
        String xp = Integer.toString(this.getXp());
        String hp = Integer.toString(this.getHp());
        String atk = Integer.toString(this.getAtk());
        String def = Integer.toString(this.getDef());

        String allInfo = this.getName();
        allInfo = allInfo.concat(","+heroClass);
        allInfo = allInfo.concat(","+lvl);
        allInfo = allInfo.concat(","+xp);
        allInfo = allInfo.concat(","+hp);
        allInfo = allInfo.concat(","+atk);
        allInfo = allInfo.concat(","+def);

        return allInfo;
    }

    public void attack(Mob currMob) {

    }
}
//<name>,<class>,<xp>,<atk>,<def>,<weapon>,<armor>