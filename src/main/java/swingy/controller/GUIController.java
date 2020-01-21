package swingy.controller;

import swingy.view.Menu;

public class GUIController {
    private Menu menu;
    public GUIController(Menu frame) {
            menu = frame;
    }

    String command = menu.getCommand();

}
