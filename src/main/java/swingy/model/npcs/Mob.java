package swingy.model.npcs;

import org.hibernate.validator.constraints.Length;

public class Mob {
    @Length(min=1)
    protected String name;
    protected int hp;
    protected int atk;
    protected int def;
    protected int xp;

    public int attack(int damage) {
        return atk + damage;
    }

    public String getMobName() { return this.name; }

    public int getHp() { return this.hp; }

    public int getDef() { return this.def; }

    public int getAtk() { return this.atk; }

    public int getXp() { return this.xp; }

    public void takeDamage(int dmg) { this.hp -= dmg; }
}
