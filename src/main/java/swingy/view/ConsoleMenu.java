package swingy.view;

public class ConsoleMenu {
    public void getMenuControls(String type) {
        if (type.equalsIgnoreCase("Main"))
            printMainMenu();
    }

    private void printMainMenu() {
        System.out.println("Welcome to Swingy: Origin of the Infinite Revengening The Movie The Game");

    }
}
