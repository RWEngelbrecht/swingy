package swingy.model.characters;

import swingy.model.artifacts.Armor;
import swingy.model.artifacts.Weapon;

public class Paladin extends Hero {
    public Paladin(String name, String charClass) {
        super(name, charClass);
        xp = 0;
        hp = 40;
        lvl = 1;
        weapon = new Weapon("Greathammer");
        armor = new Armor("Metal Breastplate");
        atk = 2 + weapon.getAtk();
        def = 3 + armor.getDef();
    }

}
