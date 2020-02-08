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

}
