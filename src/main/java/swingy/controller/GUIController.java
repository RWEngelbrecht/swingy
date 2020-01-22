package swingy.controller;

import swingy.model.InputValidator;
import swingy.view.Menu;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

// If gui is in use, this class is used to communicate with models/other controllers
public class GUIController {
    private static Menu menu;
    private static File saveFile = new File("./save.txt");
    private static PrintWriter printWriter;
    private static Map<String, String> menuMap = new HashMap<String, String>();
    private static InputValidator inputValidator = new InputValidator(1);


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
        menuMap.put("1", "Warrior");
        menuMap.put("2", "Mage");
        menuMap.put("3", "Paladin");
    }

    public static void validateInput(String value, String context) {
        inputValidator.validateWithContext(value, context);
    }

    public String getClassFromGui(@NotNull String key) {
        System.out.println("GUIController: class == "+menuMap.get(key));
        return menuMap.get(key);
    }
//    public Hero newHero(String characterName, String characterClass) {
//        return new Hero(characterName, characterClass);
//    }

}
