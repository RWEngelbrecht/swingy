package swingy.view;

import swingy.controller.GameController;
import swingy.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.activation.ActivationInstantiator;

//TODO: MORE Visual output for movement/fighting/loot
public class GamePanel extends JPanel {
    private static GameController gameController;
    private static JPanel freeRoamTools = new JPanel();
//    private static JPanel enemyEncounterTools = new JPanel();
//    private static JPanel lootingTools = new JPanel();

    private static JButton north = new JButton("North");
    private static JButton east = new JButton("East");
    private static JButton west = new JButton("West");
    private static JButton south = new JButton("South");

    private static JPanel systemTools = new JPanel();
    private static JButton save = new JButton("Save");
    private static JButton exit = new JButton("Exit");

    private static JButton fight = new JButton("Fight");
    private static JButton run = new JButton("Run");

    private static JButton equip = new JButton("Equip");
    private static JButton ignore = new JButton("Ignore");

    private static JTextArea gameOutput = new JTextArea(26, 20);

    public GamePanel(GameController gameController, Game game) {
        this.gameController = gameController;
        gameController.generateMap();
        setLayout(new BorderLayout());
        setBounds(150, 150, 100, 50);

        freeRoamTools.setLayout(new FlowLayout(FlowLayout.LEADING));
//        enemyEncounterTools.setLayout(new FlowLayout(FlowLayout.LEADING));
//        lootingTools.setLayout(new FlowLayout(FlowLayout.LEADING));

        north.addActionListener(new commandActionListener("north"));
        east.addActionListener(new commandActionListener("east"));
        west.addActionListener(new commandActionListener("west"));
        south.addActionListener(new commandActionListener("south"));
        freeRoamTools.add(north);
        freeRoamTools.add(east);
        freeRoamTools.add(west);
        freeRoamTools.add(south);

        // Save or exit game tools
        systemTools.setLayout(new BorderLayout());

        save.addActionListener(new commandActionListener("save"));
        exit.addActionListener(new commandActionListener("exit"));
        systemTools.add(save, BorderLayout.NORTH);
        systemTools.add(exit, BorderLayout.SOUTH);

        freeRoamTools.add(systemTools);

        gameOutput.setEditable(false);

        fight.addActionListener(new commandActionListener("fight"));
        run.addActionListener(new commandActionListener("run"));
//        enemyEncounterTools.add(fight);
//        enemyEncounterTools.add(run);

        equip.addActionListener(new commandActionListener("equip"));
        ignore.addActionListener(new commandActionListener("ignore"));
//        lootingTools.add(equip);
//        lootingTools.add(ignore);

        add(gameOutput, BorderLayout.NORTH);
        add(freeRoamTools, BorderLayout.SOUTH);
    }

    private void setFreeRoam() {
//        remove(enemyEncounterTools);
//        remove(lootingTools);
//        add(freeRoamTools, BorderLayout.SOUTH);
        freeRoamTools.remove(fight);
        freeRoamTools.remove(run);
        freeRoamTools.remove(equip);
        freeRoamTools.remove(ignore);
        freeRoamTools.revalidate();
        freeRoamTools.repaint();
    }

    private void setFightOrFlight() {
//        remove(freeRoamTools);
//        add(enemyEncounterTools);
        freeRoamTools.add(fight);
        freeRoamTools.add(run);
        freeRoamTools.revalidate();
        freeRoamTools.repaint();
    }

    private void setLootingMode() {
//        remove(freeRoamTools);
//        add(lootingTools);
        freeRoamTools.add(equip);
        freeRoamTools.add(ignore);
        freeRoamTools.revalidate();
        freeRoamTools.repaint();
    }

    public void displayGame(String gameText) {
//        gameController.generateMap();
        gameOutput.setText(gameText);
        revalidate();
        repaint();
    }

    public void emptySpace() {
        System.out.println("GamePanel: emptySpace reached");
        displayGame("There is nothing here...");
    }

    public void enemySpace(String gameText) {
        displayGame(gameText);
        setFightOrFlight();
    }

    public void artifactSpace(String gameText) {
        displayGame(gameText);
        setLootingMode();
    }

    public void outOfBounds() {
        displayGame("You cannot move in that direction...");
    }

    private class commandActionListener implements ActionListener {
        String command;
//        int positionState = 0;

        public commandActionListener(String command) {
            this.command = command;
        }

        public void actionPerformed(ActionEvent e) {
//            positionState = gameController.moveHero(direction);
            gameController.consoleGameControls(command);
            if (command.equals("fight") || command.equals("run") ||
                    command.equals("equip") || command.equals("ignore")) {
                setFreeRoam();
            }
        }
    }
}
