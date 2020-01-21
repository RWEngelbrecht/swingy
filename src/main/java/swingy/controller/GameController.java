package swingy.controller;

import swingy.view.Menu;
import swingy.view.UserInterface;

public class GameController {
    private GUIController guiController;
    public GameController(UserInterface userInterface) {
        if (userInterface.getInterfaceType() == 1) {
            newGUIController(userInterface.getFrame());
        }
    }

    private void newGUIController(Menu frame) {
        guiController = new GUIController(frame);
    }

    public void startGame() {
//        guiController.
    }
}
