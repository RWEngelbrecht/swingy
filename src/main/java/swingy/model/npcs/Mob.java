package swingy.model.npcs;

import swingy.model.characters.Hero;

public class Mob {
    protected String name;
    protected int hp;
    protected int atk;
    protected int def;

    public void attack(Hero hero) {

    }

    public String getMobName() { return this.name; }

    public int getHp() { return this.hp; }

    public int getDef() { return this.def; }

    public int getAtk() { return this.atk; }

    public void takeDamage(int dmg) { this.hp -= dmg; }
}
