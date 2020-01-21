package swingy.view;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {
    private JPanel mainPanel = new JPanel();
    private JTextArea output = new JTextArea();
    private JTextField command = new JTextField();
    public Menu(String title) {
        super(title);

//        JPanel mainPanel = new JPanel();
        add(mainPanel);
        mainPanel.setLayout(new BorderLayout());

//        JTextArea output = new JTextArea();
        output.setRows(26);
        output.setEditable(false);
//        JTextField command = new JTextField();
        JButton confirm = new JButton("Confirm");

        mainPanel.add(output, BorderLayout.NORTH);
        mainPanel.add(command, BorderLayout.CENTER);
        mainPanel.add(confirm, BorderLayout.SOUTH);
        setSize(600, 500);
        setResizable(true);
        setVisible(true);
    }

    public String getCommand() {
        return command.getText();
    }

    public void displayOutput(String outputText) {
        output.setText(outputText);
    }
}
