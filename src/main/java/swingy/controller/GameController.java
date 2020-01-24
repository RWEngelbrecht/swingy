package swingy.controller;

import swingy.model.HeroFactory;
import swingy.model.characters.Hero;
import swingy.view.GUIMenu;
import swingy.view.UserInterface;
import swingy.model.Game;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.ArrayList;

// Either holds instance of GUIController or acts as controller for all console-related actions.
// Also interacts with Game
public class GameController {
    protected static Game game;
    private GUIController guiController;
    private int controllerType;
    private static HeroFactory heroFactory = new HeroFactory();
    private static File saveFile;

    public GameController(UserInterface userInterface) {
        controllerType = userInterface.getInterfaceType();
        if (controllerType == 1) {
            System.out.println("from gameController: creating new GUIController");
            newGUIController(userInterface.getFrame());
        }
        game = new Game(this);
    }

    private void newGUIController(GUIMenu frame) {
        guiController = new GUIController(frame);
    }

    public Game getGame() {
        return this.game;
    }

    public void newHero(@NotBlank String characterName, @NotBlank String characterClass) {
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
        Hero hero = heroFactory.newHero(characterName, charClass);
        game.addHero(hero);
    }

    public void loadHero(String heroInfo) {
        //TODO: CREATE LOADHERO
        Hero hero = heroFactory.loadHero(heroInfo);
        game.addHero(hero);
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

    public void createSaveFile(/*Hero hero*/) {
        if (controllerType == 1) {
            saveFile = guiController.createSaveFile();
        }
    }

    public void saveGame(Hero hero) {
        if (controllerType == 1) {
            System.out.println("GameController: save game reached");
            guiController.saveGame(saveFile, hero);
        }
    }

    public ArrayList<String> getSavedGames() {
        if (controllerType == 1) {
            return guiController.getSavedGames(saveFile);
        }
        return null;
    }

    public ArrayList<String> getSavesForGui() {
        if (controllerType == 1) {
            return guiController.getSavesForGui(saveFile);
        }
        return null;
    }

    public void loadGame(String gameNumberStr) {
        ArrayList<String> saveGames = getSavedGames();
        int gameNumberInt = Integer.parseInt(gameNumberStr) - 1;
        String gameToLoad = saveGames.get(gameNumberInt);
        loadHero(gameToLoad);
    }
}
