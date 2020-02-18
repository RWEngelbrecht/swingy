package swingy.model.characters;

import swingy.model.artifacts.Armor;
import swingy.model.artifacts.Weapon;

public class Warrior extends Hero {
    public Warrior(String name, String charClass, String weapon, String armor) {
        super(name, charClass);
        xp = 0;
        hp = 40;
        lvl = 1;
        if (weapon.equals("default") || armor.equals("default")) {
            Hero.weapon = new Weapon("Dual axes");
            Hero.armor = new Armor("Leather Loincloth");
        } else {
            Hero.weapon = new Weapon(weapon);
            Hero.armor = new Armor(armor);
        }
        atk = 3 + Hero.weapon.getAtk();
        def = 2 + Hero.armor.getDef();
    }
}
