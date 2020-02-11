package swingy.model.characters;

import swingy.model.artifacts.Armor;
import swingy.model.artifacts.Weapon;

public class Mage extends Hero {
    public Mage(String name, String charClass) {
        super(name, charClass);
        xp = 0;
        hp = 40;
        lvl = 1;
        weapon = new Weapon("Staff");
        armor = new Armor("Tunic");
        atk = 1 + weapon.getAtk();
        def = 1 + armor.getDef();
    }

}
