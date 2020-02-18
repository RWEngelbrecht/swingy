package swingy.model.characters;

import swingy.model.artifacts.Armor;
import swingy.model.artifacts.Weapon;

public class Mage extends Hero {
    public Mage(String name, String charClass, String weapon, String armor) {
        super(name, charClass);
        xp = 0;
        hp = 40;
        lvl = 1;
        if (weapon.equals("default") || armor.equals("default")) {
            Hero.weapon = new Weapon("Staff");
            Hero.armor = new Armor("Tunic");
        } else {
            Hero.weapon = new Weapon(weapon);
            Hero.armor = new Armor(armor);
        }
        atk = 1 + Hero.weapon.getAtk();
        def = 1 + Hero.armor.getDef();
    }

}
