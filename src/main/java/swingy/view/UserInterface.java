package swingy.view;

import org.jetbrains.annotations.NotNull;
import swingy.controller.GameController;

import javax.swing.*;
import java.util.Scanner;

public class UserInterface {
    private static GUIMenu frame;
    public int interfaceType;
    private static String command;
    private static GameController gameController;
    public UserInterface(@NotNull final String type) {
        // Check in what mode app is started. gui: 1, console: 0
        if (type.equalsIgnoreCase("gui")) {
            interfaceType = 1;
        } else if (type.equalsIgnoreCase("console")) {
            interfaceType = 0;
        }
        gameController = new GameController(this);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (interfaceType == 1) {
                    // Instantiate gui
                    frame = new GUIMenu("Swingy: Origin of the Revengening Infinite The Movie The Game", gameController);
                }
                else if (interfaceType == 0) {

                    gameController.interpretConsole();
                }
            }
        });
        gameController.createSaveFile();
        System.out.println("UserInterface: Interface created");
    }

    public int getInterfaceType() { return interfaceType; }

    public GUIMenu getFrame() { return this.frame; }
}
