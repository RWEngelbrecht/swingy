package swingy.view.guimenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {

    private static JPanel mainToolbar = new JPanel();
    private static JLabel label;
    protected JButton loadGame;
    protected JButton newGame;

    public MainMenu() {
        // Button that will generate character creation screen
        newGame = new JButton("New Game");
        // Button that will generate save selection screen
        loadGame = new JButton("Load Game");

        label = new JLabel("Swingy");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Comic Sans", Font.LAYOUT_LEFT_TO_RIGHT, 150));

        mainToolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
        // Add buttons to toolbar
        mainToolbar.removeAll();
        mainToolbar.add(newGame);
        mainToolbar.add(loadGame);

        setLayout(new BorderLayout());
        setBounds(150, 150, 100, 50);
        add(label, BorderLayout.CENTER);
        add(mainToolbar, BorderLayout.SOUTH);
    }
}
