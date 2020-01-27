package swingy.controller;

import swingy.model.DataHandler;
import swingy.model.HeroFactory;
import swingy.model.characters.Hero;
import swingy.view.ConsoleMenu;
import swingy.view.GUIMenu;
import swingy.view.UserInterface;
import swingy.model.Game;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

// Either holds instance of GUIController or acts as controller for all console-related actions.
// Also interacts with Game
public class GameController {
    protected static Game game;
//    private GUIController guiController;
    private int controllerType;

    private static DataHandler dataHandler = new DataHandler();
    private static HeroFactory heroFactory = new HeroFactory();
    private static File saveFile;


    public GameController(UserInterface userInterface) {
        controllerType = userInterface.getInterfaceType();
//        if (controllerType == 1) {
//            System.out.println("from gameController: creating new GUIController");
//            newGUIController(userInterface.getFrame());
//        }
        game = new Game(this);
    }

//    private void newGUIController(GUIMenu frame) {
//        guiController = new GUIController(frame);
//    }

    public Game getGame() {
        return this.game;
    }

    public void interpretConsole() {
        Scanner scanner = new Scanner(System.in);
        ConsoleMenu consoleMenu = new ConsoleMenu();
        consoleMenu.getMenuControls("main");
        String command;
        while ((command = scanner.nextLine()) != null) {

        }
    }

    public void newHero(@NotBlank String characterName, @NotBlank String characterClass) {
        String charClass;
        if (controllerType == 1) {
            charClass = new String(dataHandler.getClassFromGui(characterClass));
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
        Hero hero = heroFactory.loadHero(heroInfo);
        game.addHero(hero);
    }

    public void generateMap() {
        game.makeMap();
        System.out.println("Our hero "+game.getHero().getName()+" the "+game.getHero().getHeroClass()+" of level "+game.getHero().getLevel()+" sets off on their adventure!");
//        game.printMap();
    }

//    public void validateInput(String value, String context) {
//        if (controllerType == 1) {
//            guiController.validateInput(value, context);
//        }
//    }

    public void createSaveFile(/*Hero hero*/) {
        saveFile = dataHandler.createSaveFile();
    }

    public void saveGame() {
//        if (controllerType == 1) {
            System.out.println("GameController: save game reached");
            Hero hero = game.getHero();
            dataHandler.saveGame(saveFile, hero);
//        }
    }

    public ArrayList<String> getSavedGames() {
        return dataHandler.getSavedGames(saveFile);
    }

    public ArrayList<String> getSavesForGui() {
        return dataHandler.getSavesForGui(saveFile);
    }

    public void loadGame(String gameNumberStr) {
        ArrayList<String> saveGames = getSavedGames();
        int gameNumberInt = Integer.parseInt(gameNumberStr) - 1;
        String gameToLoad = saveGames.get(gameNumberInt);
        loadHero(gameToLoad);
    }
}
