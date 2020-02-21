package swingy.view;

import org.jetbrains.annotations.NotNull;
import swingy.controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadMenu extends JPanel {
    private static GameController gameController;
    private GamePanel gamePanel;
    private JTextArea output = new JTextArea(33, 20);
    private JTextField commandText = new JTextField(20);
    //    private JButton noLoadsBt = new JButton("Go Back");
    private Container con;

    public LoadMenu(GameController gameController, Container container) {
        LoadMenu.gameController = gameController;
        con = container;
        setLayout(new BorderLayout());

        JPanel toolbar = new JPanel();
        toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
        output.setEditable(false);
        toolbar.add(commandText);
        JButton loadBt = new JButton("Load");
        loadBt.addActionListener(new loadGameActionListener());
        toolbar.add(loadBt);

        add(output, BorderLayout.NORTH);
        add(toolbar, BorderLayout.SOUTH);
    }

    public void displayOutput(@NotNull String outputText) {
        output.setText(outputText);
        output.revalidate();
        output.repaint();
    }

    private class loadGameActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String command = getCommandText();
            if (command.length() > 0 && gameController.validateSaveNumber(command)) {
                gameController.loadGame(command);
                con.removeAll();
                gamePanel = new GamePanel(gameController, gameController.getGame());
                con.add(gamePanel);
                gamePanel.displayGame("You find yourself in the middle of a field...");
            } else {
                commandText.setText("");
            }
        }
    }

    public GamePanel getGamePanel() { return this.gamePanel; }

    public String getCommandText() {
        return commandText.getText();
    }
}
