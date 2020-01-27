package swingy.controller;

import swingy.model.DataHandler;
import swingy.model.HeroFactory;
import swingy.model.characters.Hero;
import swingy.view.ConsoleMenu;
import swingy.view.UserInterface;
import swingy.model.Game;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.ArrayList;

// Either holds instance of GUIController or acts as controller for all console-related actions.
// Also interacts with Game
public class GameController {
    protected static Game game;
//    private GUIController guiController;
    private int controllerType;

    private static DataHandler dataHandler = new DataHandler();
    private static HeroFactory heroFactory = new HeroFactory();
    private static ConsoleMenu consoleMenu;
    private static File saveFile;


    public GameController(UserInterface userInterface) {
        controllerType = userInterface.getInterfaceType();
        if (controllerType == 0) {
            consoleMenu = new ConsoleMenu(this);
            game = new Game(this);
            consoleMenu.start();
        } else {
            game = new Game(this);
        }
    }

//    private void newGUIController(GUIMenu frame) {
//        guiController = new GUIController(frame);
//    }

    public Game getGame() {
        return this.game;
    }

    public void interpretConsole(String command, int menuType) {
        if (menuType == 0) {
            if (command.equals("1")) {
                consoleMenu.printCreateChar();
//                consoleMenu.setMenuState(1);
//                consoleMenu.createNewHero();
            } else if (command.equals("2")) {
                ArrayList<String> saves = getPrintableSaves();
                consoleMenu.printLoadHero(saves);
            }
        } else if (menuType == 1) {
            consoleMenu.createNewHero();
            generateMap();
            consoleMenu.startGame();
        }
        else if (menuType == 2) {

        }
        else if (menuType == 3) {
            //TODO:
            int positionState = 0;
            game.printMap();
            if (command.equalsIgnoreCase("fight") || command.equalsIgnoreCase("run")) {
                fightOrFlight(command);
            } else if (command.equalsIgnoreCase("equip")) {
                equipArtifact();
            } else {
                positionState = moveHero(command);
                System.out.println("GameController: positionState = "+positionState);
            }
            consoleMenu.continueGame(positionState);
        } else if (menuType == 4)
            System.exit(0);
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

    //TODO: fix load on empty
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

    public ArrayList<String> getPrintableSaves() {
        return dataHandler.getPrintableSaves(saveFile);
    }

    public void loadGame(String gameNumberStr) {
        ArrayList<String> saveGames = getSavedGames();
        int gameNumberInt = Integer.parseInt(gameNumberStr) - 1;
        String gameToLoad = saveGames.get(gameNumberInt);
        loadHero(gameToLoad);
    }

    public int moveHero(String direction) {
        return game.moveHero(direction);
    }

    public void fightOrFlight(String command) {
        if (command.equalsIgnoreCase("fight")) {
            game.fight();
        } else {
            game.run();
        }
    }

    // TODO: randomly generate weapon/armor, equip and update atk/def
    public void equipArtifact() {

    }
}
