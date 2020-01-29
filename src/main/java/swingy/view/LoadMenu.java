package swingy.view;

import org.jetbrains.annotations.NotNull;
import swingy.controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO: Fix problem where loading game 1 from file with multiple saves breaks swingy
public class LoadMenu extends JPanel {
    private static GameController gameController;
    private JTextArea output = new JTextArea(26, 20);
    private JPanel toolbar = new JPanel();
    private JTextField commandText = new JTextField(20);
    private JButton loadBt = new JButton("Load");
//    private JButton noLoadsBt = new JButton("Go Back");
    private Container con;

    public LoadMenu(GameController gameController, Container container) {
        this.gameController = gameController;
        con = container;
        setLayout(new BorderLayout());

        toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
        output.setEditable(false);
        toolbar.add(commandText);
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
            if (command.length() > 0) {
                gameController.loadGame(command);
                con.removeAll();
                GamePanel gamePanel = new GamePanel(gameController, gameController.getGame());
                con.add(gamePanel);
                gamePanel.displayGame();
            }
        }
    }

    public String getCommandText() {
        return commandText.getText();
    }
}
