package swingy.controller;

import swingy.view.Menu;
import swingy.model.characters.Hero;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// If gui is in use, this class is used to communicate with models/other controllers
public class GUIController {
    private static Menu menu;
    private static File saveFile = new File("./save.txt");
    private static PrintWriter printWriter;

    static {
        try {
            printWriter = new PrintWriter(saveFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public GUIController(Menu frame) {
        System.out.println("From GUIController: Creating new GUIController");
        menu = frame;
    }

    public static void interpretMenuCommand(String command) {
        if (command.equals("newGame")) {
            System.out.println("GUIController: Interpreted command");
        }
    }

//    public Hero newHero(String characterName, String characterClass) {
//        return new Hero(characterName, characterClass);
//    }

}
