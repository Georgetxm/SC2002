package camsAction;
import java.util.Scanner;

import controllers.Controller;
import interactions.Interaction;
/**
 * Interaction that represents the no action.
 * Effectively serves as a function pointer to a blank function.
 * Used as a placeholder
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doNothing extends Interaction{
	/**
	 * Function does nothing.
	 * @return null
	 */
	@Override
	public Interaction run(String currentuser, Scanner s, Controller control){
		return null;
	}
}
