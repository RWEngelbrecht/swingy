package swingy.view;

import org.jetbrains.annotations.NotNull;
import swingy.controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO: Display game from here or  GUIMenu class?
public class LoadMenu extends JPanel {
    private static GameController gameController;
    private JTextArea output = new JTextArea(26, 20);
    private JPanel toolbar = new JPanel();
    private JTextField commandText = new JTextField(20);
    private JButton loadBt = new JButton("Load");

    public LoadMenu(GameController gameController) {
        this.gameController = gameController;
        setLayout(new BorderLayout());

        toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
        output.setEditable(false);
        toolbar.add(commandText);
        toolbar.add(loadBt);

        loadBt.addActionListener(new loadGameActionListener());

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
            String command = commandText.getText();
            gameController.loadGame(command);
            gameController.generateMap();
        }
    }

    public String getCommandText() {
        return commandText.getText();
    }
}
