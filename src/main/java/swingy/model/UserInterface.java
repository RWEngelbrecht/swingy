package swingy.model;

import swingy.controller.GameController;
import swingy.view.ConsoleMenu;
import swingy.view.guimenu.GUIMenu;

public class UserInterface {
    public int interfaceType;
	private static GameController gameController;

    public UserInterface(final String type) {
        // Check in what mode app is started. gui: 1, console: 0
        if (type.equalsIgnoreCase("gui")) {
            interfaceType = 1;
        } else if (type.equalsIgnoreCase("console")) {
            interfaceType = 0;
        }
        gameController = new GameController(this);
        gameController.createSaveFile();
        if (interfaceType == 0)
            gameController.consoleGenerateMainMenu();
    }

    public int getInterfaceType() { return interfaceType; }
}
