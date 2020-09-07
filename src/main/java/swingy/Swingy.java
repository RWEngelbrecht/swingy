package swingy;

import swingy.model.UserInterface;

public class Swingy {

	public static void main (String[] args) {
		try {
			UserInterface userInterface = new UserInterface(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error: No interface argument. GUI or Console?");
			System.exit(1);
		}
    }

}
