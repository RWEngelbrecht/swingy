package swingy.controller;

import org.jetbrains.annotations.NotNull;
import swingy.model.InputHandler;
import swingy.model.DataHandler;
import swingy.model.HeroFactory;
import swingy.model.artifacts.Artifact;
import swingy.model.characters.Hero;
import swingy.view.ConsoleMenu;
import swingy.view.GUIMenu;
import swingy.view.UserInterface;
import swingy.model.Game;

import javax.swing.*;
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
    private static InputHandler inputHandler;
    private static ConsoleMenu consoleMenu;
    private static GUIMenu guiMenu;
    private static File saveFile;


    public GameController(@NotNull UserInterface userInterface) {
        controllerType = userInterface.getInterfaceType();
        System.out.println("GameController: controllerType = "+controllerType);
        if (controllerType == 0) {
            consoleMenu = new ConsoleMenu(this);
        } else {
            System.out.println("GameController: guiMenu about to be set");
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    guiMenu = new GUIMenu("Swingy: Origin of the Revengening Infinite The Movie The Game", GameController.this);
                }
            });
        }
        inputHandler = new InputHandler(this);  //Might be used by both console and gui
        game = new Game(this);
    }

    public Game getGame() {
        return game;
    }

    public void consoleGenerateMainMenu() {
        consoleMenu.start();
    }

    public void consoleMainMenuControls(@NotNull String command) {
        if (command.equals("1")) {
            inputHandler.startCreateHero();
        } else if (command.equals("2")) {
            inputHandler.startLoadHero();
        } else if (command.equalsIgnoreCase("exit")) {
            System.exit(1);
        }
    }

    public void consoleCreateHeroControls() {
        String heroName = consoleMenu.getHeroName();
        String heroClass = consoleMenu.getHeroClass();
        if (inputHandler.checkHeroName(heroName) && inputHandler.checkHeroClass(heroClass)) {
            newHero(heroName, heroClass);
            inputHandler.startGame();
        }
    }

    public void consoleLoadHeroControls(ArrayList<String> saves) {
        consoleMenu.printLoadHero(saves);
        String loadNumber = consoleMenu.getLoadNumber();
        if (loadNumber.equalsIgnoreCase("exit"))
            System.exit(1);
        else
            loadGame(loadNumber);
        inputHandler.startGame();
    }

    public void consoleStartGame() {
        consoleMenu.startGame();
    }

    public void consoleGameControls(@NotNull String command) {
        int positionState = 0;

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
        inputHandler.continueGame(positionState);
    }

    public void reactEmptySpace() {
        if (controllerType == 0) {
            consoleMenu.emptySpace();
            consoleMenu.freeRoam();
        } else {
            guiMenu.setGamePanelOutput("", 0);
        }
    }

    public void reactEnemySpace(String enemyString) {
        if (controllerType == 0) {
            consoleMenu.enemySpace(enemyString);
            consoleMenu.freeRoam();
        } else {
            guiMenu.setGamePanelOutput(enemyString, 2);
        }
    }

    public void reactArtifactSpace(String artifactString) {
        if (controllerType == 0) {
            consoleMenu.artifactSpace(artifactString);
            consoleMenu.freeRoam();
        } else {
            guiMenu.setGamePanelOutput(artifactString, 3);
        }
    }

    public void reactOutOfBounds() {
        if (controllerType == 0) {
            consoleMenu.outOfBounds();
            consoleMenu.freeRoam();
        } else {
            guiMenu.setGamePanelOutput("", -1);
        }
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

        if (gameNumberInt + 1 <= saveGames.size()) {
            String gameToLoad = saveGames.get(gameNumberInt);
            loadHero(gameToLoad);
        }
    }

    public int moveHero(String direction) {
        System.out.println("GameController: moveHero: hero about to move");
        return game.moveHero(direction);
    }

    public void fightOrFlight(@NotNull String command) {
        if (command.equalsIgnoreCase("fight")) {
            game.fight();
        } else {
            game.run();
        }
    }

    public String getCurrEnemy() {
        return game.getCurrEnemy();
    }

    public Artifact getCurrArtifact() {
        return game.getCurrArtifact();
    }

    public void equipArtifact() {
        game.equipArtifact();
    }

}
