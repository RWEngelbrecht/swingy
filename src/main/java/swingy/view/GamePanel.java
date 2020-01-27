package swingy.view;

import swingy.controller.GameController;
import swingy.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.activation.ActivationInstantiator;

public class GamePanel extends JPanel {
    private static GameController gameController;
    private static JPanel freeRoamTools = new JPanel();
    private static JButton north = new JButton("North");
    private static JButton east = new JButton("East");
    private static JButton west = new JButton("West");
    private static JButton south = new JButton("South");

    private static JPanel systemTools = new JPanel();
    private static JButton save = new JButton("Save");
    private static JButton exit = new JButton("Exit");

    private static JTextArea gameOutput = new JTextArea(26, 20);

    public GamePanel(GameController gameController, Game game) {
        this.gameController = gameController;
        setLayout(new BorderLayout());
        setBounds(150, 150, 100, 50);

        freeRoamTools.setLayout(new FlowLayout(FlowLayout.LEADING));
        north.addActionListener(new moveActionListener("north"));
        east.addActionListener(new moveActionListener("east"));
        west.addActionListener(new moveActionListener("west"));
        south.addActionListener(new moveActionListener("south"));
        freeRoamTools.add(north);
        freeRoamTools.add(east);
        freeRoamTools.add(west);
        freeRoamTools.add(south);

        systemTools.setLayout(new BorderLayout());

        save.addActionListener(new saveActionListener());
        exit.addActionListener(new exitActionListener());

        systemTools.add(save, BorderLayout.NORTH);
        systemTools.add(exit, BorderLayout.SOUTH);
        freeRoamTools.add(systemTools);

        gameOutput.setEditable(false);

        add(gameOutput, BorderLayout.NORTH);
        add(freeRoamTools, BorderLayout.SOUTH);
    }

    public void displayGame() {
        gameController.generateMap();
        String gameText = "You find yourself in the middle of a field.";
        gameOutput.setText(gameText);
        revalidate();
        repaint();
    }

    private class saveActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            gameController.saveGame();
        }
    }

    private class exitActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

        }
    }

    private class moveActionListener implements ActionListener {
        String direction;

        public moveActionListener(String direction) {
            this.direction = direction;
        }

        public void actionPerformed(ActionEvent e) {
            gameController.moveHero(direction);
        }
    }
}
