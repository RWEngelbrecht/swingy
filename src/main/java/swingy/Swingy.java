package swingy;

import swingy.controller.GameController;
import swingy.view.UserInterface;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.validation.*;

public class Swingy {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main (String[] args) {
        if (args.length > 0) {
            // Create user interface - generates gui or starts app in console
            UserInterface userInterface = new UserInterface(args[0]);
        } else {
            System.out.println("Your arguments are invalid");
            System.exit(1);
        }
    }

}
