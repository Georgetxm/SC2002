package camsAction;

import java.util.Scanner;

import cams.CamsInteraction;
import controllers.Controller;
import entities.UserInfoMissingException;
import interactions.Interaction;

/**
 * Represents the action of changing the password
 */
public class doChangePassword extends Interaction {

	/**
	 *Prompts the user for a new password and requests the user controller to change it.
	 *@return startmenu
	 */
	@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException, MissingRequestedDataException {
		System.out.println("Type your new password");
		control.User().changePassword(s.nextLine(),currentuser);
		System.out.println("Password changed");
		return CamsInteraction.startmenu(currentuser);
	}

}
