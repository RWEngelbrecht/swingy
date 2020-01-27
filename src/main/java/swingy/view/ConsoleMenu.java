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
        printMainMenu("0");
    }

    public void generateMenu(String command) {
        if (menuState == 0)
            printMainMenu(command);
        else if (menuState == 1)
            printCreateChar(command);
        else if (menuState == 2)
            printLoadChar(command);
        else if (menuState == 3)
            printGameMenu(command);
        else if (command.equalsIgnoreCase("exit"))
            menuState = 4;
    }

    private void printMainMenu(String command) {
        if (command.equals("0")) {
            System.out.println("Welcome to Swingy: Origin of the Infinite Revengening The Movie The Game");
            System.out.println("\n1 - Start a new game\n2 - Load a game.");
        } else if (command.equals("1")) {
            menuState = 1;
            generateMenu("0");
        } else if (command.equals("2")) {
            menuState = 2;
            generateMenu("0");
        } else if (command.equalsIgnoreCase("exit")) {
            menuState = 4;
        } else {
            System.out.println("Choose valid action.");
        }
    }

    private void printCreateChar(String command) {
        @NotBlank
        @NotNull
        String name;
        @NotBlank
        @NotNull
        String heroClass;
        try {
            System.out.println("Create new hero: ");
            System.out.println("Choose a name: ");

            name = reader.readLine();

            System.out.println("Choose a class: " +
                    "\n\t 1 - Warrior" +
                    "\n\t 2 - Mage" +
                    "\n\t 3 - Paladin");

            heroClass = reader.readLine();

            gameController.newHero(name, heroClass);
            gameController.generateMap();
            menuState = 3;

            generateMenu("0");

        } catch (IOException e) {
            System.out.println("Not valid.");
        }
    }

    private void printLoadChar(String command) {
        ArrayList<String> savedGames = gameController.getPrintableSaves();
//        String saveStates = new String();
        if (savedGames != null) {
            for (int i = 0; i < savedGames.size(); i++) {
                System.out.println((i+1)+" - "+savedGames.get(i));
            }
        } else {
            System.out.println("You have no loadable games.");
            menuState = 0;
            generateMenu("0");
        }
        String loadCommand;
        try {
            loadCommand = reader.readLine();

            gameController.loadGame(loadCommand);
            gameController.generateMap();

            menuState = 3;
            generateMenu("0");
        } catch (IOException e) {
            System.out.println("Could not read command.");
        }
    }

    private void printGameMenu(String command) {
        if (command.equals("0")) {
            System.out.println("You find yourself in the middle of a field.");
            System.out.println("You can go North, East, South or West.");
        } else if (command.equalsIgnoreCase("North") ||
            command.equalsIgnoreCase("east") ||
            command.equalsIgnoreCase("south") ||
            command.equalsIgnoreCase("west")) {
            gameController.moveHero(command);
        } else if (command.equalsIgnoreCase("exit")) {
            menuState = 4;
        } else {
            System.out.println("Please enter a valid direction or type \"exit\" to quit game.");
        }
    }

    public int getMenuState() {
        return this.menuState;
    }
}
