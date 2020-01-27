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
            System.out.println("ConsoleMenu: command = " + command + " menuState = " + menuState);
            gameController.interpretConsole(command, menuState);
        } catch (IOException e) {
            System.out.println("Error: Invalid input.");
        }
    }

    public void printMainMenu() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Welcome to Swingy: Origin of the Infinite Revengening The Movie The Game");
        System.out.println("\n1 - Start a new game\n2 - Load a game.");
    }

    //TODO: validate input
    public void printCreateChar() {
        System.out.println("Create new hero: ");
        menuState = 1;
        gameController.interpretConsole("new", menuState);
    }

    public void printLoadHero(ArrayList<String> saves) {
        menuState = 2;
        System.out.println("Load a hero: ");
        if (saves != null) {
            for (int i = 0; i < saves.size(); i++) {
                System.out.println((i + 1) + saves.get(i));
            }
        }
        String command = "0";
        try {
            while (Integer.parseInt(command) < 1) {
                command = reader.readLine();
            }
            gameController.interpretConsole(command, menuState);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String getHeroName() {
        @NotBlank
        @NotNull
        String heroName;
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Choose a name: ");
        try {
            heroName = reader.readLine();
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
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Choose a class:" +
                "\n\t 1 - Warrior" +
                "\n\t 2 - Mage" +
                "\n\t 3 - Paladin");
        try {
            while (!heroClass.equals("1") && !heroClass.equals("2") && !heroClass.equals("3")) {
                heroClass = reader.readLine();
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("Choose a class:" +
                        "\n\t 1 - Warrior" +
                        "\n\t 2 - Mage" +
                        "\n\t 3 - Paladin");
            }
            return heroClass;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createNewHero() {
        String heroName = getHeroName();
        String heroClass = getHeroClass();
        gameController.newHero(heroName, heroClass);
//        gameController.generateMap();
//        gameController.interpretConsole("start", menuState);
    }

    public void startGame() {
        menuState = 3;
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("You find yourself in the middle of a field...");
        System.out.println("Type \'north\', \'east\', \'south\' or \'west\' to move in a direction.\nOr type \'exit\' to quit.");
        String command = "0";
        try {
            while (!command.equals("north") &&
                    !command.equals("south") &&
                    !command.equals("east") &&
                    !command.equals("west")) {
                command = reader.readLine();
            }
            gameController.interpretConsole(command, menuState);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void continueGame(int state) {
        //state: open field = 0, enemy = 2, artifact = 3
        //TODO: move directions on 0; fight or run on 2; pick up or ignore on 3
        String command = "0";
        try {
            if (state == 0) {
                System.out.println("There is nothing here...");
            } else if (state == 2) {
//                System.out.print("\033[H\033[2J");
//                System.out.flush();
                System.out.println("You come across a fascist!\n Do you want to fight or run away?");
                while (!command.equalsIgnoreCase("fight") &&
                        !command.equalsIgnoreCase("run")) {
                    command = reader.readLine();
                }
                gameController.fightOrFlight(command);
            } else if (state == 3) {
//                System.out.print("\033[H\033[2J");
//                System.out.flush();
                System.out.println("You come across a spoon!\nDo you equip or ignore it?");
                while (!command.equalsIgnoreCase("equip") &&
                        !command.equalsIgnoreCase("ignore")) {
                    command = reader.readLine();
                }
                if (command.equalsIgnoreCase("equip"))
                    gameController.interpretConsole(command, menuState);
            }
//            System.out.print("\033[H\033[2J");
//            System.out.flush();
            System.out.println("Type \'north\', \'east\', \'south\' or \'west\' to move in a direction.\nOr type \'exit\' to quit.");
            while (!command.equals("north") &&
                    !command.equals("south") &&
                    !command.equals("east") &&
                    !command.equals("west")) {
                command = reader.readLine();
            }
        }catch (IOException e) {
                e.printStackTrace();
        }
        gameController.interpretConsole(command, menuState);
    }

    public int getMenuState() {
        return this.menuState;
    }

    public void setMenuState(int state) {
        this.menuState = state;
    }
}
