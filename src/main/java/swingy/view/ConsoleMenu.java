package swingy.view;

import swingy.controller.GameController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;

//TODO: Clear console before println

public class ConsoleMenu {
    // menuState:  mainMenu = 0; charCreate = 1; loadChar = 2; gameMenu = 3; quit = 4
    private int menuState;
    private static GameController gameController;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public ConsoleMenu(GameController gameController) {
        menuState = 0;
        this.gameController = gameController;
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
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
        System.out.println("Welcome to Swingy: Origin of the Infinite Revengening The Movie The Game");
        System.out.println("\n1 - Start a new game\n2 - Load a game.");
    }

    public void printLoadHero(ArrayList<String> saves) {
        System.out.println("Load a hero: ");
        if (saves != null) for (int i = 0; i < saves.size(); i++) {
            System.out.println((i + 1) +" - "+ saves.get(i));
        }
    }

    public String getLoadNumber() {
        String command = "0";
        try {
            while (Integer.parseInt(command) < 1) {
                command = reader.readLine();
            }
//            gameController.loadGame(command);
            return command;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getHeroName() {
        @NotBlank
        @NotNull
        String heroName = "0";
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
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
        @NotBlank
        @NotNull
        String heroClass = "0";
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
        System.out.println("Choose a class:" +
                "\n\t 1 - Warrior" +
                "\n\t 2 - Mage" +
                "\n\t 3 - Paladin");
        try {
            while (!heroClass.equals("1") &&
                    !heroClass.equals("2") &&
                    !heroClass.equals("3") &&
                    !heroClass.equalsIgnoreCase("exit")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
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
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
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
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
        System.out.println("There is nothing here...");
    }

    public void enemySpace() {
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
        System.out.println("You come across a fascist!\n Do you want to fight or run away?");
        String command = "0";
        try {
            while (!command.equalsIgnoreCase("fight") &&
                    !command.equalsIgnoreCase("run") &&
                    !command.equalsIgnoreCase("exit")) {
                command = reader.readLine();
            }
            gameController.consoleGameControls(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void artifactSpace() {
        String command = "0";
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
        System.out.println("You come across a spoon!\nDo you equip or ignore it?");
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

    public void freeRoam() {
        String command = "0";
        System.out.println("Type \'north\', \'east\', \'south\' or \'west\' to move in a direction.\nOr type \'exit\' to quit.");
        try{
            while (!command.equals("north") &&
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

    public int getMenuState() {
        return this.menuState;
    }

    public void setMenuState(int state) {
        this.menuState = state;
    }
}