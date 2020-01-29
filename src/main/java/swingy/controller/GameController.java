package swingy.controller;

import swingy.model.ConsoleHandler;
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
    private static ConsoleHandler consoleHandler;
    private static ConsoleMenu consoleMenu;
    private static File saveFile;


    public GameController(UserInterface userInterface) {
        controllerType = userInterface.getInterfaceType();
        if (controllerType == 0) {
            consoleHandler = new ConsoleHandler(this);
            consoleMenu = new ConsoleMenu(this);
        }
        game = new Game(this);
    }

    public Game getGame() {
        return game;
    }

    public void consoleGenerateMainMenu() {
        consoleMenu.start();
    }

    public void consoleMainMenuControls(String command) {
        if (command.equals("1")) {
            consoleHandler.startCreateHero();
        } else if (command.equals("2")) {
            consoleHandler.startLoadHero();
        } else if (command.equalsIgnoreCase("exit")) {
            System.exit(1);
        }
    }

    public void consoleCreateHeroControls() {
        String heroName = consoleMenu.getHeroName();
        String heroClass = consoleMenu.getHeroClass();
        if (consoleHandler.checkHeroName(heroName) && consoleHandler.checkHeroClass(heroClass)) {
            newHero(heroName, heroClass);
            consoleHandler.startGame();
        }
    }

    public void consoleLoadHeroControls(ArrayList<String> saves) {
        consoleMenu.printLoadHero(saves);
        String loadNumber = consoleMenu.getLoadNumber();
        loadGame(loadNumber);
        consoleHandler.startGame();
    }

    public void consoleStartGame() {
        consoleMenu.startGame();
    }

    public void consoleGameControls(String command) {
        int positionState = 0;
        game.printMap();
        System.out.println("GameController: consoleGameControls: command == "+command);
        if (command.equalsIgnoreCase("fight") || command.equalsIgnoreCase("run")) {
            fightOrFlight(command);
        } else if (command.equalsIgnoreCase("equip")) {
            equipArtifact();
        } else if (command.equalsIgnoreCase("exit")) {
            System.exit(1);
        } else if (command.equalsIgnoreCase("save")) {
            saveGame();
        } else {
            positionState = moveHero(command);
        }
        consoleHandler.continueGame(positionState);
    }

    public void reactEmptySpace() {
        consoleMenu.emptySpace();
        consoleMenu.freeRoam();
    }

    public void reactEnemySpace() {
        consoleMenu.enemySpace();
        consoleMenu.freeRoam();
    }

    public void reactArtifactSpace() {
        consoleMenu.artifactSpace();
        consoleMenu.freeRoam();
    }

    public void newHero(@NotBlank String characterName, @NotBlank String characterClass) {
        String charClass = new String(dataHandler.getClass(characterClass));
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

    public void createSaveFile() {
        saveFile = dataHandler.createSaveFile();
    }

    public void saveGame() {
        System.out.println("GameController: save game reached");
        Hero hero = game.getHero();
        dataHandler.saveGame(saveFile, hero);
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
        if (saveGames.size() <= gameNumberInt + 1) {
            String gameToLoad = saveGames.get(gameNumberInt);
            loadHero(gameToLoad);
        }
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
