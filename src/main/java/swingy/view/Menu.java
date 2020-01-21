package swingy.view;

import swingy.controller.GameController;
import swingy.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO: Add validation
public class Menu extends JFrame {
    private JPanel mainPanel = new JPanel();
    private JPanel charCreatePanel = new JPanel();

    private static JTextArea outputField = new JTextArea();
    private static JPanel mainToolbar = new JPanel();
    private static JPanel charCreateTools = new JPanel();
    private static GameController gameController;
    private static String outputText = new String("Create New Character:");
    private static JLabel label;
    private static String charName = new String();

    public Menu(String title, final GameController gameController) {
        // Creates JFrame with given title
        super(title);
        // Instantiate gameController so view can interact with model
        this.gameController = gameController;

        setSize(600, 500);
        final Container con = this.getContentPane();
        // Button that will generate character creation screen
        JButton newGame = new JButton("New Game");
        // Button that will generate save selection screen
        JButton loadGame = new JButton("Load Game");
        label = new JLabel("Swingy");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("James", Font.LAYOUT_LEFT_TO_RIGHT, 150));

        mainToolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
        // Add buttons to toolbar
        mainToolbar.add(newGame);
        mainToolbar.add(loadGame);
        charCreateTools.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBounds(150, 150, 100, 50);

        // Non-editable textfield to wherein to display instructions/story/art
        outputField.setRows(26);
        outputField.setEditable(false);
        // Action listener that generates character creation page
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                mainPanel.add(outputField, BorderLayout.CENTER);
                con.removeAll();
                con.add(charCreatePanel);
                displayCharCreation(outputText);
                // Doesn't do anything yet.
                gameController.interpretMenuCommand("newGame");
            }
        });
        // TODO: Action Listener that will generate page with saved game states from text file
        loadGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        mainPanel.add(label, BorderLayout.CENTER);
        mainPanel.add(mainToolbar, BorderLayout.SOUTH);
        con.add(mainPanel);

        charCreatePanel.setLayout(new BorderLayout());
        charCreatePanel.setBounds(150, 150, 100, 50);
        charCreatePanel.add(outputField, BorderLayout.NORTH);
        charCreatePanel.add(charCreateTools, BorderLayout.SOUTH);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private static void displayCharCreation(final String output) {
//        toolbar.removeAll();
        label = new JLabel("Choose a name");
        final JButton submitName = new JButton("Confirm");
        final JButton submitClass = new JButton("Confirm");
        final JButton finish = new JButton("Start Game");
        final JTextField textField = new JTextField(20);

        charCreateTools.add(label);
        submitName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                charName = new String(textField.getText());
                textField.setText("");
                charCreateTools.removeAll();
                label = new JLabel("Choose a class");
                charCreateTools.add(label);
                charCreateTools.add(textField);
                charCreateTools.add(submitClass);
                displayOutput(outputText.concat("\n\nChoose a class:" +
                        "\n\t 1 - Warrior"+
                        "\n\t 2 - Mage"+
                        "\n\t 3 - Paladin"));

                charCreateTools.revalidate();
                charCreateTools.repaint();
            }
        });

        submitClass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String response = new String(textField.getText());
                textField.setText("");
                if (response.equals("1"))
                    gameController.newHero(charName, "warrior");
                else if (response.equals("2"))
                    gameController.newHero(charName, "mage");
                else if (response.equals("3"))
                    gameController.newHero(charName, "paladin");
                charCreateTools.removeAll();
                charCreateTools.add(finish);
                displayOutput("Magnificent! You are now ready to delve into Swingy: Origin of the Infinite Revengening The Movie The Game");
                charCreateTools.revalidate();
                charCreateTools.repaint();
            }
        });

        finish.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayGame(gameController.getGame());
            }
        });

        charCreateTools.add(textField);
        charCreateTools.add(submitName);
        charCreateTools.revalidate();
        charCreateTools.repaint();
        displayOutput(output);
    }

    private static void displayGame(Game game) {
        gameController.generateMap();
    }

    public static void displayOutput(String output) {
        outputField.setText(output);
        outputField.revalidate();
        outputField.repaint();
    }

}
