package swingy.view;

import javax.swing.*;

public class UserInterface  {
    private Menu frame;
    public int interfaceType;
//    private GameController gameController = new GameController();
    public UserInterface(final String type) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (type.equalsIgnoreCase("gui")) {
                    interfaceType = 1;
                    frame = new Menu("Swingy: Origin of the Revengening Infinite The Movie The Game");
                    frame.displayOutput("Welcome to swingy!\nWould you like to:\n\t1 - Start a new game\n\t2 - Load an existing game");
                } else if (type.equalsIgnoreCase("console")) {
                    interfaceType = 0;
                    System.out.println("Welcome to Swingy: Origin of the Revengening Infinite The Movie The Game");
                }
            }
        });
    }

    public int getInterfaceType() {
        return this.interfaceType;
    }

//    public void getInterface(int type) {
//        if (type == 1) {
//            this.getFrame();
//        }
//    }

    public Menu getFrame() {
        return frame;
    }
}
