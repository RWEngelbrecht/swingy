package swingy.view;

import swingy.controller.GameController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;


public class ConsoleMenu {
    // menuState:  mainMenu = 0; charCreate = 1; loadChar = 2; gameMenu = 3; quit = 4
    private int menuState;
    private static GameController gameController;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public ConsoleMenu(GameController gameController) {
        menuState = 0;
        this.gameController = gameController;
    }

    private static void flushConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void start() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String command = "0";
            while (!command.equals("1") && !command.equals("2")) {
                printMainMenu();
                command = reader.readLine();
            }
            gameController.consoleMainMenuControls(command);
        } catch (IOException e) {
            System.out.println("Error: Invalid input.");
        }
    }

    public void printMainMenu() {
//        flushConsole();
        System.out.println("Welcome to Swingy: Origin of the Infinite Revengening The Movie The Game");
        System.out.println("\n1 - Start a new game\n2 - Load a game.");
    }

    public void printLoadHero(ArrayList<String> saves) {
//        flushConsole();
        System.out.println("Load a hero: ");
        if (saves != null)
            for (int i = 0; i < saves.size(); i++) {
                System.out.println((i + 1) +" - "+ saves.get(i));
        }
    }

    public String getLoadNumber() {
        String command = "";
        try {
            while (!gameController.validateSaveNumber(command)) {
                if (command.equalsIgnoreCase("exit"))
                    break;
                command = reader.readLine();
            }
            return command;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getHeroName() {
//        flushConsole();
        @NotBlank
        @NotNull
        String heroName = "0";
        System.out.println("Create new hero: ");
        System.out.println("Choose a name: ");
        try {
            heroName = reader.readLine();
            if (heroName.equalsIgnoreCase("exit"))
                gameController.consoleGameControls(heroName);
            return heroName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getHeroClass() {
//        flushConsole();
        @NotBlank
        @NotNull
        String heroClass = "0";
        System.out.println("Choose a class:" +
                "\n\t 1 - Warrior" +
                "\n\t 2 - Mage" +
                "\n\t 3 - Paladin");
        try {
            while (!heroClass.equals("1") &&
                    !heroClass.equals("2") &&
                    !heroClass.equals("3") &&
                    !heroClass.equalsIgnoreCase("exit")) {
                flushConsole();
                System.out.println("Choose a class:" +
                        "\n\t 1 - Warrior" +
                        "\n\t 2 - Mage" +
                        "\n\t 3 - Paladin");
                heroClass = reader.readLine();
                if (heroClass.equalsIgnoreCase("exit"))
                    gameController.consoleGameControls(heroClass);
            }
            return heroClass;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void startGame() {
//        flushConsole();
        System.out.println("You find yourself in the middle of a field...");
        System.out.println("Type \'north\', \'east\', \'south\' or \'west\' to move in a direction.\nOr type \'exit\' to quit.");
        String command = "0";
        try {
            while (!command.equals("north") &&
                    !command.equals("south") &&
                    !command.equals("east") &&
                    !command.equals("west") &&
                    !command.equalsIgnoreCase("exit") &&
                    !command.equalsIgnoreCase("save")) {
                command = reader.readLine();
            }
            gameController.consoleGameControls(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void emptySpace() {
//        flushConsole();
        System.out.println("There is nothing here...");
    }

    public void enemySpace(String enemyString) {
//        flushConsole();
        System.out.println(enemyString+"\n Do you want to fight or run away?");
        String command = "0";
        try {
            while (!command.equalsIgnoreCase("fight") &&
                    !command.equalsIgnoreCase("run") &&
                    !command.equalsIgnoreCase("exit")) {
                command = reader.readLine();
            }
            gameController.fightControls(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void artifactSpace(String artifactString) {
//        flushConsole();
        String command = "0";
        System.out.println(artifactString+"\nDo you equip or ignore it?");
        try {
            while (!command.equalsIgnoreCase("equip") &&
                    !command.equalsIgnoreCase("ignore") &&
                    !command.equalsIgnoreCase("exit") &&
                    !command.equalsIgnoreCase("save")) {
                command = reader.readLine();
            }
            gameController.consoleGameControls(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void outOfBounds() {
//        flushConsole();
        System.out.println("You cannot move in that direction...");
    }

    public void freeRoam() {
        String command = "0";
        System.out.println("Type \'north\', \'east\', \'south\' or \'west\' to move in a direction.\nOr type \'exit\' to quit.");
        try{
            while (!command.equalsIgnoreCase("north") &&
                !command.equalsIgnoreCase("south") &&
                !command.equalsIgnoreCase("east") &&
                !command.equalsIgnoreCase("west") &&
                !command.equalsIgnoreCase("exit") &&
                !command.equalsIgnoreCase("save")) {
                command = reader.readLine();
            }
            gameController.consoleGameControls(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fight() {
        String command = "0";
        System.out.println("Type \'fight\'to continue fight or \'run\' to flee.\nOr type \'exit\' to quit.");
        try{
            while (!command.equalsIgnoreCase("fight") &&
                    !command.equalsIgnoreCase("run") &&
                    !command.equalsIgnoreCase("exit")) {
                command = reader.readLine();
            }
            gameController.fightControls(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void killedEnemy(String victoryMessage) {
//        flushConsole();
        System.out.println(victoryMessage);
    }

    public void retreated() {
//        flushConsole();
        System.out.println("You take a step back to reevaluate your life decisions.");
    }

    public void youDied(String deathMessage) {
//        flushConsole();
        System.out.println(deathMessage);
    }
    public int getMenuState() {
        return this.menuState;
    }

    public void setMenuState(int state) {
        this.menuState = state;
    }
}
