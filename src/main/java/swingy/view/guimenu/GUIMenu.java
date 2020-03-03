package swingy.view.guimenu;

import org.jetbrains.annotations.NotNull;
import swingy.controller.GameController;

import javax.swing.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// TODO: Have 2 textFields on same toolbar, take player name and class with one actionEvent
public class GUIMenu extends JFrame {
    private JPanel charCreatePanel = new JPanel();
    private MainMenu mainMenu;
    private GamePanel gamePanel;
    private LoadMenu loadMenu;


    private static JTextArea outputField = new JTextArea(140, 20);
//    private static JPanel mainToolbar = new JPanel();
    private static JPanel charCreateTools = new JPanel();

    private static GameController gameController;
    private static String outputText = new String("Create New Character:");
    private final JTextField textField = new JTextField(20);
    private final JButton submitName = new JButton("Confirm");
    private final JButton submitClass = new JButton("Confirm");
    private final JButton finish = new JButton("Start Game");
    private static JLabel label;
    private static String charName;

    public GUIMenu(String title, final GameController gameController) {
        // Creates JFrame with given title
        super(title);
        // Instantiate gameController so view can interact with model
        GUIMenu.gameController = gameController;

        setSize(700, 600);
        mainMenu = new MainMenu();

        // TODO: make charCreateTools Panel
        charCreateTools.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Non-editable textfield to wherein to display instructions/story/art
        outputField.setEditable(false);

//        finish.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                con.removeAll();
//                gamePanel = new GamePanel(gameController, gameController.getGame());
//                con.add(gamePanel);
//                gamePanel.displayGame("You find yourself in the middle of a field...");
//            }
//        });

        charCreatePanel.setLayout(new BorderLayout());
        charCreatePanel.setBounds(150, 150, 100, 50);
        charCreatePanel.add(outputField, BorderLayout.NORTH);
        charCreatePanel.add(charCreateTools, BorderLayout.SOUTH);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        displayMainMenu();

        setVisible(true);
    }

    public void displayMainMenu() {
        System.out.println("GUIMenu: displayMainMenu: reached.");
        final Container con = this.getContentPane();
        con.removeAll();

        mainMenu.newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                con.removeAll();
                con.add(charCreatePanel);
                displayCharCreation(outputText);
                // Doesn't do anything yet.
//                gameController.validateInput("newGame");
            }
        });

        mainMenu.loadGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> savedGames = gameController.getPrintableSaves();
                String saveStates = new String();
                if (savedGames != null) {
                    loadMenu = new LoadMenu(gameController, con);
                    con.removeAll();
                    con.add(loadMenu);
                    for (int i = 0; i < savedGames.size(); i++) {
                        saveStates = saveStates.concat((i+1)+" - "+savedGames.get(i)+"\n");
                    }
                    loadMenu.displayOutput(saveStates);
                }
            }
        });

        con.add(mainMenu);
    }

    private void displayCharCreation(final String output) {

        label = new JLabel("Choose a name");

        charCreateTools.add(label);
        submitName.addActionListener(new CharNameActionListener());
        submitClass.addActionListener(new CharClassActionListener());

        charCreateTools.add(textField);
        charCreateTools.add(submitName);
        charCreateTools.revalidate();
        charCreateTools.repaint();

        displayOutput(outputField, output);
    }

    public void displayOutput(@NotNull JTextArea outputField , String output) {
        outputField.setText(output);
        outputField.revalidate();
        outputField.repaint();
    }

    public void setGamePanelOutput(String gameText, int positionState) {
        System.out.println("GUIMenu: setGamePanelOutput: gameText = "+gameText+" positionState = "+positionState);
        if (this.gamePanel == null) {
            gamePanel = loadMenu.getGamePanel();
        }
        if (positionState == 0) {
            gamePanel.emptySpace(gameText);
        } else if (positionState == 2) {
            gamePanel.enemySpace(gameText);
        } else if (positionState == 3) {
            gamePanel.artifactSpace(gameText);
        } else if (positionState == 4) {
            gamePanel.youDied(gameText);
        } else if (positionState < 0) {
            gamePanel.outOfBounds();
        }
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
            charCreateTools.removeAll();
            label = new JLabel("Choose a class");
            charCreateTools.add(label);
            charCreateTools.add(textField);
            charCreateTools.add(submitClass);
            displayOutput(outputField,outputText.concat("\n\nChoose a class:" +
                    "\n\t 1 - Warrior"+
                    "\n\t 2 - Mage"+
                    "\n\t 3 - Paladin"));

            charCreateTools.revalidate();
            charCreateTools.repaint();
        }
    }

    private class CharClassActionListener implements ActionListener {
        @NotBlank private String charClass;

        public void actionPerformed(ActionEvent e) {
            charClass = textField.getText();
            textField.setText("");
            gameController.newHero(charName, charClass);
            charCreateTools.removeAll();
            charCreateTools.add(finish);
            displayOutput(outputField,"You are now ready to delve into Swingy: Origin of the Infinite Revengening The Movie The Game");
            charCreateTools.revalidate();
            charCreateTools.repaint();
        }
    }
}

