package swingy.view.guimenu;

import swingy.controller.GameController;
import swingy.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO: MORE Visual output for movement/fighting/loot
public class GamePanel extends JPanel {
    private static GameController gameController;
    private static JPanel gamePanelControls = new JPanel();

    private JButton north = new JButton("North");
    private JButton east = new JButton("East");
    private JButton west = new JButton("West");
    private JButton south = new JButton("South");

    private JPanel systemTools = new JPanel();
    private JButton save = new JButton("Save");
    private JButton exit = new JButton("Exit");

    private JButton fight = new JButton("Fight");
    private JButton run = new JButton("Run");

    private JButton equip = new JButton("Equip");
    private JButton ignore = new JButton("Ignore");

    private JTextArea gameOutput = new JTextArea(32, 20);

    public GamePanel(GameController gameController, Game game) {
        GamePanel.gameController = gameController;
        gameController.generateMap();
        setLayout(new BorderLayout());
        setBounds(150, 150, 100, 50);

        gamePanelControls.setLayout(new FlowLayout(FlowLayout.CENTER));

        north.addActionListener(new commandActionListener("north"));
        east.addActionListener(new commandActionListener("east"));
        west.addActionListener(new commandActionListener("west"));
        south.addActionListener(new commandActionListener("south"));
        gamePanelControls.removeAll();
        gamePanelControls.add(north);
        gamePanelControls.add(west);
        gamePanelControls.add(east);
        gamePanelControls.add(south);

        // Save or exit game tools
        systemTools.setLayout(new BorderLayout());

        save.addActionListener(new commandActionListener("save"));
        exit.addActionListener(new commandActionListener("exit"));
        systemTools.add(save, BorderLayout.NORTH);
        systemTools.add(exit, BorderLayout.SOUTH);

        gamePanelControls.add(systemTools);

        gameOutput.setEditable(false);

        fight.addActionListener(new commandActionListener("fight"));
        run.addActionListener(new commandActionListener("run"));

        equip.addActionListener(new commandActionListener("equip"));
        ignore.addActionListener(new commandActionListener("ignore"));

        add(gameOutput, BorderLayout.NORTH);
        add(gamePanelControls, BorderLayout.SOUTH);
    }

    private void setFreeRoam() {
        gamePanelControls.removeAll();
        gamePanelControls.add(north);
        gamePanelControls.add(west);
        gamePanelControls.add(east);
        gamePanelControls.add(south);
        gamePanelControls.add(systemTools);
        gamePanelControls.revalidate();
        gamePanelControls.repaint();
    }

    private void setFightOrFlight() {
        gamePanelControls.add(fight);
        gamePanelControls.add(run);
        gamePanelControls.revalidate();
        gamePanelControls.repaint();
    }

    private void setLootingMode() {
        gamePanelControls.remove(north);
        gamePanelControls.remove(east);
        gamePanelControls.remove(west);
        gamePanelControls.remove(south);
        gamePanelControls.add(equip);
        gamePanelControls.add(ignore);
        gamePanelControls.revalidate();
        gamePanelControls.repaint();
    }

    private void setDeathScreenControls() {
        gamePanelControls.removeAll();
        gamePanelControls.add(exit);
        gamePanelControls.revalidate();
        gamePanelControls.repaint();
    }

    public void displayGame(String gameText) {
        gameOutput.setText(gameText);
        revalidate();
        repaint();
    }

    public void emptySpace(String gameText) {
        displayGame(gameText);
        setFreeRoam();
    }

    public void enemySpace(String gameText) {
        System.out.println("GamePanel: enemySpace reached");
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

    public void youDied(String deathMessage) {
        displayGame(deathMessage);
        setDeathScreenControls();
    }

    private class commandActionListener implements ActionListener {
        String command;

        public commandActionListener(String command) {
            this.command = command;
        }

        public void actionPerformed(ActionEvent e) {
            if (command.equals("fight") || command.equals("run")) {
                gameController.fightControls(command);
            } else if (command.equals("equip") || command.equals("ignore")) {
                gameController.gameControls(command);
                setFreeRoam();
            } else {
                gameController.gameControls(command);
            }
        }
    }
}
