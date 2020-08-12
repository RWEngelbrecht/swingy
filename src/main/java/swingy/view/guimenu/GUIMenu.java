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

public class GUIMenu extends JFrame {
    private CharacterCreationPanel charCreatePanel;
    private MainMenu mainMenu;
    private GamePanel gamePanel;
    private LoadMenu loadMenu;

    private static JTextArea outputField = new JTextArea(140, 20);

    private static GameController gameController;
    private static String outputText = new String("Create New Character:");
    private static JLabel label;
    private static String charName;

    public GUIMenu(String title, final GameController gameController) {
        // Creates JFrame with given title
        super(title);
        // Instantiate gameController so view can interact with model
        GUIMenu.gameController = gameController;

        setSize(700, 600);
        mainMenu = new MainMenu();

        // Non-editable textfield to wherein to display instructions/story/art
        outputField.setEditable(false);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        displayMainMenu();

        setVisible(true);
    }

    public void displayMainMenu() {
        final Container con = this.getContentPane();
        con.removeAll();

        mainMenu.newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                charCreatePanel = new CharacterCreationPanel(gameController, con);
                con.removeAll();
                con.add(charCreatePanel);
                charCreatePanel.displayOutput("Create new hero: \n" +
						"Choose a name:");
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

    public void displayOutput(@NotNull JTextArea outputField , String output) {
        outputField.setText(output);
        outputField.revalidate();
        outputField.repaint();
    }

    public void setGamePanelOutput(String gameText, int positionState) {
        if (this.gamePanel == null) {
			if (loadMenu != null)
				gamePanel = loadMenu.getGamePanel();
			else
				gamePanel = charCreatePanel.getGamePanel();
        }
        if (positionState == 0) {
            gamePanel.emptySpace(gameText);
        } else if (positionState == 2) {
            gamePanel.enemySpace(gameText);
        } else if (positionState == 3) {
            gamePanel.artifactSpace(gameText);
        } else if (positionState == 4) {
            gamePanel.youDied(gameText);
            gamePanel = null;
        } else if (positionState < 0) {
            gamePanel.outOfBounds();
        }
    }
}

