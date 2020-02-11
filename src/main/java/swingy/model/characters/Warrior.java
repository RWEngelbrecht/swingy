package swingy.model.characters;

import swingy.model.artifacts.Armor;
import swingy.model.artifacts.Weapon;

public class Warrior extends Hero {
    public Warrior(String name, String charClass) {
        super(name, charClass);
        xp = 0;
        hp = 40;
        lvl = 1;
        weapon = new Weapon("Dual axes");
        armor = new Armor("Leather Loincloth");
        atk = 3 + weapon.getAtk();
        def = 2 + weapon.getDef();
    }
}
