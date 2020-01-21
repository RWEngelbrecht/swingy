package swingy.controller;

import javax.swing.*;
import swingy.view.MainMenu;

public class GUIController {
    private MainMenu menu;
    public GUIController(MainMenu frame) {
            menu = frame;
    }

    String command = menu.getCommand();

}
