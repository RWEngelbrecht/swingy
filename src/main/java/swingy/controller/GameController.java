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
        game.addHero(heroFactory.newHero(characterName, characterClass));
    }

    public void generateMap() {
        game.makeMap();
//        game.printMap();
    }

    public void interpretMenuCommand(String command) {
        if (controllerType == 1) {
            guiController.interpretMenuCommand(command);
        }
    }

}
