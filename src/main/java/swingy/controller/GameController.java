package swingy.controller;

import swingy.view.MainMenu;
import swingy.view.UserInterface;

import javax.swing.JFrame;

public class GameController {
    private GUIController guiController;
    public GameController(UserInterface userInterface) {
        if (userInterface.getInterfaceType() == 1) {
            newGUIController(userInterface.getFrame());
        }
    }

    private void newGUIController(MainMenu frame) {
        guiController = new GUIController(frame);
    }

    public void startGame() {
//        guiController.
    }
}
