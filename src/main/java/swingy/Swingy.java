package swingy;

import java.security.InvalidParameterException;

import swingy.model.UserInterface;

public class Swingy {

	public static void main (String[] args) {
		try {
			if (!args[0].equalsIgnoreCase("console") && !args[0].equalsIgnoreCase("gui")) {
				throw new InvalidParameterException();
			}
			UserInterface userInterface = new UserInterface(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error: No interface argument. GUI or Console?");
			System.exit(1);
		} catch (InvalidParameterException e) {
			System.out.println("Error: I'd argue that that was a bad argument... GUI or Console?");
			System.exit(1);
		}
    }

}
