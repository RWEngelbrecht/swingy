package swingy.model;

import swingy.model.characters.Hero;
import swingy.model.characters.Mage;
import swingy.model.characters.Paladin;
import swingy.model.characters.Warrior;

public class HeroFactory {
    public Hero newHero(String heroName, String heroClass, String weapon, String armor) {
        if (heroClass.equalsIgnoreCase("warrior")) {
            return new Warrior(heroName, heroClass, weapon, armor);
        } else if (heroClass.equalsIgnoreCase("mage")) {
            return new Mage(heroName, heroClass, weapon, armor);
        } else if (heroClass.equalsIgnoreCase("paladin")) {
            return new Paladin(heroName, heroClass, weapon, armor);
        }
        return null;
    }

    public Hero loadHero(String heroInfo) {
        String[] stats = heroInfo.split(",");
        String name = stats[0],
                heroClass = stats[1];

        int lvl = Integer.parseInt(stats[2]),
            xp = Integer.parseInt(stats[3]),
            hp = Integer.parseInt(stats[4]);

        String weapon = stats[7],
                armor = stats[8];

        Hero hero = newHero(name, heroClass, weapon, armor);
        hero.setLvl(lvl);
        hero.setXp(xp);
        hero.setHp(hp);

        return hero;
    }
}
