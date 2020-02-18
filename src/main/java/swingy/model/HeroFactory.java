package swingy.model;

import org.jetbrains.annotations.NotNull;
//import swingy.model.artifacts.Armor;
//import swingy.model.artifacts.Artifact;
//import swingy.model.artifacts.Weapon;
import swingy.model.characters.Hero;
import swingy.model.characters.Mage;
import swingy.model.characters.Paladin;
import swingy.model.characters.Warrior;

import javax.validation.constraints.NotBlank;

public class HeroFactory {
    public Hero newHero(@NotBlank String heroName, @NotBlank String heroClass, String weapon, String armor) {
        if (heroClass.equalsIgnoreCase("warrior")) {
            return new Warrior(heroName, heroClass, weapon, armor);
        } else if (heroClass.equalsIgnoreCase("mage")) {
            return new Mage(heroName, heroClass, weapon, armor);
        } else if (heroClass.equalsIgnoreCase("paladin")) {
            return new Paladin(heroName, heroClass, weapon, armor);
        }
        return null;
    }

    // TODO: equip saved weapon/armor
    public Hero loadHero(@NotNull String heroInfo) {
        String[] stats = heroInfo.split(",");
        String name = stats[0],
                heroClass = stats[1];

        int lvl = Integer.parseInt(stats[2]),
            xp = Integer.parseInt(stats[3]),
            hp = Integer.parseInt(stats[4]);
//            atk = Integer.parseInt(stats[5]),
//            def = Integer.parseInt(stats[6]);

        String weapon = stats[7],
                armor = stats[8];

        System.out.println("HeroFactory: loadHero: weapon = "+weapon+" armor("+armor+") = "+armor);
        Hero hero = newHero(name, heroClass, weapon, armor);
        hero.setLvl(lvl);
        hero.setXp(xp);
        hero.setHp(hp);
        System.out.println("HeroFactory: hero atk = "+hero.getAtk()+" def = "+hero.getDef());
//        hero.setAtk(atk);
//        hero.setDef(def);
//        hero.equipWeapon(weapon, weapon.getAtk());
//        hero.donArmor(armor, armor.getDef());

        return hero;
    }
}
