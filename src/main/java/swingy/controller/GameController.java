package swingy.controller;

import swingy.model.HeroFactory;
import swingy.model.characters.Hero;
import swingy.view.Menu;
import swingy.view.UserInterface;
import swingy.model.Game;

// Either holds instance of GUIController or acts as controller for all console-related actions.
// Also interacts with Game
public class GameController {
    protected static Game game;
    private GUIController guiController;
    private int controllerType;
    private static HeroFactory heroFactory = new HeroFactory();
    public GameController(UserInterface userInterface) {
        controllerType = userInterface.getInterfaceType();
        if (controllerType == 1) {
            System.out.println("from gameController: creating new GUIController");
            newGUIController(userInterface.getFrame());
        }
        game = new Game(this);
    }

    private void newGUIController(Menu frame) {
        guiController = new GUIController(frame);
    }

    public Game getGame() {
        return this.game;
    }

    public void newHero(String characterName, String characterClass) {
        String charClass;
        if (controllerType == 1) {
            charClass = new String(guiController.getClassFromGui(characterClass));
        } else {
            if (characterClass.equals("1"))
                charClass = new String("Warrior");
            else if (characterClass.equals("2"))
                charClass = new String("Mage");
            else if (characterClass.equals("3"))
                charClass = new String("Paladin");
            else
                charClass = new String("Warrior");
        }
        game.addHero(heroFactory.newHero(characterName, charClass));
    }

    public void generateMap() {
        game.makeMap();
        System.out.println("Our hero "+game.getHero().getName()+" the "+game.getHero().getHeroClass()+" sets off on their adventure!");
//        game.printMap();
    }

    public void validateInput(String value, String context) {
        if (controllerType == 1) {
            guiController.validateInput(value, context);
        }
    }

}
