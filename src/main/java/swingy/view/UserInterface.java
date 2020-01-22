package swingy.view;

import swingy.controller.GameController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface {
    private static Menu frame;
    public int interfaceType;
    private static String command;
    private static GameController gameController;
    public UserInterface(final String type) {
        // Check in what mode app is started. gui: 1, console: 0
        if (type.equalsIgnoreCase("gui")) {
            interfaceType = 1;
            gameController = new GameController(this);
        } else if (type.equalsIgnoreCase("console")) {
            interfaceType = 0;
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (interfaceType == 1) {
                    // Instantiate gui
                    frame = new Menu("Swingy: Origin of the Revengening Infinite The Movie The Game", gameController);
                } else if (interfaceType == 0) {
                    System.out.println("Welcome to Swingy: Origin of the Infinite Revengening The Movie The Game");
                }
            }
        });
    }

    public int getInterfaceType() { return interfaceType; }

    public Menu getFrame() { return this.frame; }
}
