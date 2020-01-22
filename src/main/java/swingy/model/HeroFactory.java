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
}
