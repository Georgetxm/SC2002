package camsAction;

import java.util.Scanner;

import cams.CamsInteraction;
import controllers.Controller;
import entities.UserInfoMissingException;
import interactions.Interaction;

public class doChangePassword extends Interaction {

	@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException, MissingRequestedDataException {
		System.out.println("Type your new password");
		control.User().changePassword(s.nextLine());
		System.out.println("Password changed");
		return CamsInteraction.startmenu(currentuser);
	}

}
