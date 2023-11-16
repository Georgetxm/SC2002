package interactions;
import java.util.Scanner;

import camsAction.MissingRequestedDataException;
import entities.Data;
import entities.UserInfoMissingException;

/**
 * 
 */
public abstract class Interaction {
	/**
	 * Represents a function object, which doesn't exist in Java, sadly.
	 * <p>
	 * The purpose of these is to allow the passing of functions as a parameters to other functions.
	 * This would, in theory, allow easier dependency injection, a stronger version of dependency inversion that allows hot-swapping of dependencies.
	 * As this is an interface, we cannot specify a constructor (and it would be terrible to do so anyway).
	 * As such we will be relying on setter injection.
	 * For an example of how setter injection allows us to specify dependencies at runtime, check queryAllCampsMenu
	 * @see camsAction.queryAllCampsMenu
	 */
	protected String message;
	/**
	 * Function to be run
	 * @return the result of the run
	 * @throws UserInfoMissingException if the user id of the current user cannot be found
	 * @throws MissingRequestedDataException if the function requested data from the controller or Data, and it was invalid or missing.
	 */
	protected abstract Object run() throws UserInfoMissingException,MissingRequestedDataException;
	/**
	 * Gets the global scanner if initialised, if not initialises and returns the global scanner.
	 * @return Scanner
	 */
	protected static Scanner getScanner(){
		if(Data.containsKey("Scanner")&&Scanner.class.isInstance(Data.get("Scanner")))
		return (Scanner) Data.get("Scanner");
		return new Scanner(System.in);
	}
}
