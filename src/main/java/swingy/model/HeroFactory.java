package swingy.model;

import swingy.model.characters.Hero;
import swingy.model.characters.Mage;
import swingy.model.characters.Paladin;
import swingy.model.characters.Warrior;

import javax.validation.constraints.NotBlank;

public class HeroFactory {
    public Hero newHero(@NotBlank String heroName, @NotBlank String heroClass) {
        if (heroClass.equalsIgnoreCase("warrior")) {
            return new Warrior(heroName, heroClass);
        } else if (heroClass.equalsIgnoreCase("mage")) {
            return new Mage(heroName, heroClass);
        } else if (heroClass.equalsIgnoreCase("paladin")) {
            return new Paladin(heroName, heroClass);
        }
        return null;
    }

    public Hero loadHero(String heroInfo) {
        String[] stats = heroInfo.split(",");
        String name = stats[0],
                heroClass = stats[1];

        int lvl = Integer.parseInt(stats[2]),
            xp = Integer.parseInt(stats[3]),
            hp = Integer.parseInt(stats[4]),
            atk = Integer.parseInt(stats[5]),
            def = Integer.parseInt(stats[6]);

        Hero hero = newHero(name, heroClass);
        hero.setLvl(lvl);
        hero.setXp(xp);
        hero.setHp(hp);
        hero.setAtk(atk);
        hero.setDef(def);

        return hero;
    }
}
