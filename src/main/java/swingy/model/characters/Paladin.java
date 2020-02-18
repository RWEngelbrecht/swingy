package swingy.model.characters;

import swingy.model.artifacts.Armor;
import swingy.model.artifacts.Weapon;

public class Paladin extends Hero {
    public Paladin(String name, String charClass, String weapon, String armor) {
        super(name, charClass);
        xp = 0;
        hp = 40;
        lvl = 1;
        if (weapon.equals("default") || armor.equals("default")) {
            Hero.weapon = new Weapon("Greathammer");
            Hero.armor = new Armor("Metal Breastplate");
        } else {
            Hero.weapon = new Weapon(weapon);
            Hero.armor = new Armor(armor);
        }
        atk = 2 + Hero.weapon.getAtk();
        def = 3 + Hero.armor.getDef();
    }

}
