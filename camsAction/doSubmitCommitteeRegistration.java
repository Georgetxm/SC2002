package camsAction;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.NoSuchElementException;

import controllers.CampController;
import controllers.UserController;
import entities.Data;
import entities.UserInfoMissingException;
import interactions.Interaction;
import types.Perms;
import types.Role;

public final class doSubmitCommitteeRegistration extends Interaction {

	/**
	 *
	 */
	@Override
	public final Boolean run() throws UserInfoMissingException, MissingRequestedDataException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		Object control=Data.get("Controller");
		if(
			!CampController.class.isInstance(control)||
			!UserController.class.isInstance(control)
		)	throw new NoSuchElementException("Controller not able enough. Request Failed.");

		
		String userid=GetData.CurrentUser();
		int campid=GetData.CampID();

		HashMap<Integer, String> camplist = ((CampController) ((CampController) control).FilterUser(userid)).getCamps();
		
		if(camplist.keySet().contains(campid)) {
			System.out.println("Already registered");
			return false;
		}
		
		if(((CampController) control).isCommiteeFull(campid)) {
			System.out.println("Committee is full. Please apply again next time.");
			return false;
		}
		((CampController) control).joinCamp(campid, userid, Role.COMMITTEE);
		((UserController) control).grantPerms(userid, EnumSet.of(
			Perms.SUBMIT_CAMP_SUGGESTION,
			Perms.EDIT_CAMP_SUGGESTION,
			Perms.DELETE_CAMP_SUGGESTION
		));
		((UserController) control).denyPerms(userid, EnumSet.of(Perms.REGISTER_AS_COMITTEE));
		System.out.println("Registered successfully as a comittee member.");
		
		return true;
	}

}
