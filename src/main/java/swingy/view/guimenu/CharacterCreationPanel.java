package swingy.view.guimenu;

import org.jetbrains.annotations.NotNull;
import swingy.controller.GameController;

import javax.swing.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CharacterCreationPanel extends JPanel {

    private static GameController gameController;
    private Container con;
    private GamePanel gamePanel;
    protected JPanel toolbar = new JPanel();
    protected JTextArea output = new JTextArea(33, 20);
    private JTextField textField = new JTextField(20);
//    protected JButton submitName;
    protected JButton submitClass;
    protected JButton finish;
    private String charName;

    public CharacterCreationPanel(GameController gameController, Container container) {
        CharacterCreationPanel.gameController = gameController;
        con = container;
        setLayout(new BorderLayout());

        toolbar = new JPanel();
        toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
        output.setEditable(false);
        toolbar.add(textField);
        JButton submitName = new JButton("Confirm");
        submitName.addActionListener(new CharNameActionListener());
        submitClass = new JButton("Confirm");
        submitClass.addActionListener(new CharClassActionListener());
        finish = new JButton("Start Game");
        finish.addActionListener(new startNewGameListener());

        toolbar.add(submitName);

        add(output, BorderLayout.NORTH);
        add(toolbar, BorderLayout.SOUTH);
    }

    public void classSelection() {
        toolbar.removeAll();
        JLabel label = new JLabel("Choose a class");
        toolbar.add(label);
        toolbar.add(textField);
        toolbar.add(submitClass);
        toolbar.revalidate();
        toolbar.repaint();
    }

    public void charCreateFinish() {
        toolbar.removeAll();
        toolbar.add(finish);

        displayOutput("You are now ready to delve into Swingy: Origin of the Infinite Revengening The Movie The Game");
        toolbar.revalidate();
        toolbar.repaint();
    }

    public void displayOutput(@NotNull String outputText) {
        output.setText(outputText);
        output.revalidate();
        output.repaint();
    }

    private class CharNameActionListener implements ActionListener {
        @NotEmpty
        @NotBlank
        private String name;

        public void actionPerformed(ActionEvent e) {
            name = textField.getText();
            charName = name;
//            gameController.validateInput(charName, "heroName");
            textField.setText("");
            classSelection();

            displayOutput("\n\nChoose a class:" +
                    "\n\t 1 - Warrior"+
                    "\n\t 2 - Mage"+
                    "\n\t 3 - Paladin");
        }
    }

    private class CharClassActionListener implements ActionListener {
        @NotBlank private String charClass;

        public void actionPerformed(ActionEvent e) {
            charClass = textField.getText();
            textField.setText("");
            gameController.newHero(charName, charClass);
            charCreateFinish();

        }
    }

    private class startNewGameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            con.removeAll();
            gamePanel = new GamePanel(gameController, gameController.getGame());
            con.add(gamePanel);
            gamePanel.displayGame("You find yourself in the middle of a field...");
        }
    }
}
