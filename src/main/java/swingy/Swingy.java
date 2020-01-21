package swingy;

import swingy.controller.GameController;
import swingy.view.UserInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.validation.*;

public class Swingy {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static UserInterface userInterface;
    private static GameController controller;

    public static void main (String[] args) {
        if (args.length > 0) {
            userInterface = new UserInterface(args[0]);
            controller = new GameController(userInterface);
        } else {
            System.out.println("Your arguments are invalid");
            System.exit(1);
        }
    }

}

// LOAD CHARACTER FROM INPUT: <name>,<class>,<x>,<y>,<weapon>,<armor>
