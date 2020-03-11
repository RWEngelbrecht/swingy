package swingy.controller;

import org.jetbrains.annotations.NotNull;
import swingy.model.InputHandler;
import swingy.model.DataHandler;
import swingy.model.HeroFactory;
import swingy.model.artifacts.Artifact;
import swingy.model.characters.Hero;
import swingy.view.ConsoleMenu;
import swingy.view.guimenu.GUIMenu;
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
                    guiMenu = new GUIMenu("Swingy: Origin of the Infinite Revengening The Movie The Game", GameController.this);
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

    // TODO: add quit to main menu everywhere
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

    public void gameControls(@NotNull String command) {
        int positionState = 0;

//        if (command.equalsIgnoreCase("fight") || command.equalsIgnoreCase("run")) {
//            fightOrFlight(command);
//        }
        if (command.equalsIgnoreCase("equip")) {
            equipArtifact();
        } else if (command.equalsIgnoreCase("exit")) {
            System.out.println("GameController: gameControls: exit reached, doing stupid shit...");
            if (controllerType == 1) {
//                guiMenu.displayMainMenu();
                guiMenu.dispose();
                guiMenu = new GUIMenu("Swingy: Origin of the Infinite Revengening The Movie The Game", GameController.this);
            } else {
                System.exit(1);
            }
        } else if (command.equalsIgnoreCase("save")) {
            saveGame();
        } else {
            positionState = moveHero(command);
            game.printMap();
        }
        inputHandler.continueGame(positionState);
    }

    public void fightControls(@NotNull String command) {
        String outcome = "Absolutely nothing happened";
        if (command.equalsIgnoreCase("fight") ) {
            outcome = game.fight();
            inputHandler.continueFight(outcome);
        } else if (command.equalsIgnoreCase("run")) {
            inputHandler.retreat(game.run());
        } else if (command.equalsIgnoreCase("exit")) {
            System.exit(1);
        } else if (command.equalsIgnoreCase("save")) {
            saveGame();
        }
    }

    public void levelUp() {
        if (controllerType == 0)
            inputHandler.startGame();
        else {
//            System.out.println("GameController: levelUp: You gained enough experience to now be one better.");
            guiMenu.setGamePanelOutput(
                    "You gained enough experience to now be one better.\n" +
                            "You also have a bigger map to run around on.", 0);
            game.makeMap();
        }
    }

    public void reactEmptySpace() {
        if (controllerType == 0) {
            consoleMenu.emptySpace();
            consoleMenu.freeRoam();
        } else {
            guiMenu.setGamePanelOutput("There is nothing here...", 0);
        }
    }

    public boolean isOnEnemy() {
        return game.isOnEnemy();
    }
    public boolean isAlive() { return game.isAlive(); }

    public void reactEnemySpace(String enemyString) {

        if (controllerType == 0) {
            consoleMenu.enemySpace(enemyString);
//            if (isOnEnemy())
            consoleMenu.fight();
//            else
//                consoleMenu.freeRoam();
        } else {
            guiMenu.setGamePanelOutput(enemyString, 2);
        }
    }

    public void reactKilledEnemy(String killedMessage) {
        if (controllerType == 0) {
            consoleMenu.killedEnemy(killedMessage);
            consoleMenu.freeRoam();
        } else {
            guiMenu.setGamePanelOutput(killedMessage, 0);
        }
    }

    public void reactRetreated() {
        System.out.println("GameController: reactRetreated reached");
        if (controllerType == 0) {
            consoleMenu.retreated();
            consoleMenu.freeRoam();
        } else {
            guiMenu.setGamePanelOutput("You take a step back to reevaluate your life decisions", 0);
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
        Hero hero = heroFactory.newHero(characterName, charClass, "default", "default");
        game.addHero(hero);
    }

    public void loadHero(String heroInfo) {
        Hero hero = heroFactory.loadHero(heroInfo);
        game.addHero(hero);
    }

    public void generateMap() {
        game.makeMap();
//        System.out.println("Our hero "+game.getHero().getName()+" the "+game.getHero().getHeroClass()+" of level "+game.getHero().getLevel()+" sets off on their adventure!");
//        game.printMap();
    }

    public void youDied(String deathMessage) {
        if (controllerType == 0) {
            consoleMenu.youDied(deathMessage);
        } else {
            guiMenu.setGamePanelOutput(deathMessage, 4);
        }
    }

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

        if (validateSaveNumber(gameNumberStr)) {
            String gameToLoad = saveGames.get(gameNumberInt);
            loadHero(gameToLoad);
        }
    }

    public boolean validateSaveNumber(String number) {
        try {
            int saveNumber = Integer.parseInt(number);
            return saveNumber <= getSavedGames().size() && saveNumber != 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int moveHero(String direction) {
        System.out.println("GameController: moveHero: hero about to move");
        return game.moveHero(direction);
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
