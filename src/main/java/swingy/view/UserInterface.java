package swingy.view;

import javax.swing.*;

public class UserInterface {

    public UserInterface() {
        JFrame frame = new JFrame("Swingy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}
