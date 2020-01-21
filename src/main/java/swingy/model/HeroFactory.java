package swingy.model;

import swingy.model.characters.Hero;
import swingy.model.characters.Warrior;

public class HeroFactory {
    public Hero newHero(String heroName, String heroClass) {
        if (heroClass.equalsIgnoreCase("warrior")) {
            return new Warrior(heroName, heroClass);
        }
        return null;
    }
}
