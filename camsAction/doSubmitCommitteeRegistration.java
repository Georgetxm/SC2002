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
/**
 * Interaction that represents the action of adding a user to a camp as a camp committee member.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doSubmitCommitteeRegistration extends Interaction {

	/**
	 * Requests the controller to register the current user as a camp committee member of a given camp
	 * Asks the controller if the camp committee is full or the user is already part of an existing camp committee or the user has already registered for this camp as another role before requesting.
	 * Grants the user permissions afforded to a camp committee member, and removes the permission to reapply for a camp committee
	 *@return true if controller accepts the request(s) and false if otherwise, or the camp is full, or the user is already an existing camp committee member, or the user has already registered for the given camp as another role.
	 *@throws MissingRequestedDataException if the camp to be registered for cannot be found
	 *@throws UserInfoMissingException if the user id of the current user cannot be found
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
		
		if(((UserController) control).getCampCommitteeOfStudent(userid)<0) {
			System.out.println("Already registered for an existing camp commitee");
			return false;
		}
		
		if(camplist.keySet().contains(campid)) {
			System.out.println("Already registered for this camp as an attendee.");
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
