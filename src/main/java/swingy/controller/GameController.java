package swingy.controller;

import swingy.model.InputHandler;
import swingy.model.DataHandler;
import swingy.model.HeroFactory;
import swingy.model.artifacts.Artifact;
import swingy.model.characters.Hero;
import swingy.view.ConsoleMenu;
import swingy.view.guimenu.GUIMenu;
import swingy.model.UserInterface;
import swingy.model.Game;

import javax.swing.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

// Either holds instance of GUIController or acts as controller for all console-related actions.
// Also interacts with Game
public class GameController {
    protected static Game game;
    private int controllerType;

    private static DataHandler dataHandler = new DataHandler();
    private static HeroFactory heroFactory = new HeroFactory();
    private static InputHandler inputHandler;
    private static ConsoleMenu consoleMenu;
    private static GUIMenu guiMenu;
    private static File saveFile;

    public GameController(UserInterface userInterface) {
        controllerType = userInterface.getInterfaceType();
        if (controllerType == 0) {
            consoleMenu = new ConsoleMenu(this);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    guiMenu = new GUIMenu("Swingy: Origin of the Infinite Revengening The Movie The Game", GameController.this);
                }
            });
        }
        inputHandler = new InputHandler(this);
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
        newHero(heroName, heroClass);
        inputHandler.startGame();
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

    public void gameControls(String command) {
        int positionState = 0;

        if (command.equalsIgnoreCase("equip")) {
            equipArtifact();
        } else if (command.equalsIgnoreCase("exit")) {
            if (controllerType == 1) {
                guiMenu.dispose();
                guiMenu = new GUIMenu("Swingy: Origin of the Infinite Revengening The Movie The Game", GameController.this);
            } else {
                consoleMenu.start();
            }
            game = new Game(this);
            return;
        } else if (command.equalsIgnoreCase("save")) {
            saveGame();
        } else {
            positionState = moveHero(command);
        //    game.printMap();    //  MAP BEING PRINTED
        }
        inputHandler.continueGame(positionState);
    }

    public void fightControls(String command) {
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

    public void reactEnemySpace(String enemyString) {

        if (controllerType == 0) {
            consoleMenu.enemySpace(enemyString);
            consoleMenu.fight();
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

    public void newHero(String characterName, String characterClass) {
		String charClass = new String(dataHandler.getClass(characterClass));
		Hero hero = heroFactory.newHero(characterName, charClass, "default", "default");

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Hero>> constraintViolations = validator.validate(hero);

        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<Hero> violation : constraintViolations) {
                System.out.println(violation.getMessage());
            }
            if (controllerType == 0) {
                consoleGenerateMainMenu();
            } else if (controllerType == 1) {
                guiMenu.dispose();
                guiMenu = new GUIMenu("Swingy: Origin of the Infinite Revengening The Movie The Game", GameController.this);
            }
        } else {
            game.addHero(hero);
        }
    }

    public void loadHero(String heroInfo) {
		String heroData = heroInfo.split("\t")[0];
		String[] loadMapArr = heroInfo.split("\t");
		ArrayList<String> loadMap = new ArrayList<String>();
		for (int i = 1; i < loadMapArr.length; i++) {
			loadMap.add(loadMapArr[i]);
		}
		Hero hero = heroFactory.loadHero(heroData);
        game.addHero(hero);
		game.loadMap(loadMap);
    }

    public void generateMap() {
        game.makeMap();
    }

	// public int[][] getMap() {
	// 	return game.getMap();
	// }

    public void youDied(String deathMessage) {
        if (controllerType == 0) {
            consoleMenu.youDied(deathMessage);
            System.out.println("Press Enter to acknowledge your failure and go back to the Main Menu");
            try {
                System.in.read();
                consoleMenu.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            guiMenu.setGamePanelOutput(deathMessage, 4);
        }
    }

    public void createSaveFile() {
        saveFile = dataHandler.createSaveFile();
    }

    public void saveGame() {
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
