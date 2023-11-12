package camsAction;

import java.util.HashMap;
import java.util.NoSuchElementException;

import controllers.CampController;
import controllers.UserController;
import entities.Data;
import entities.UserInfoMissingException;
import interactions.Interaction;
import types.Role;
/**
 * Interaction that represents the action of adding a user to a camp as an attendee.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doSubmitAttendeeRegistration extends Interaction {

	@Override
	public final Boolean run() throws UserInfoMissingException, MissingRequestedDataException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(Data.get("Controller"))||
			!UserController.class.isInstance(Data.get("Controller"))
		)	throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		String userid=GetData.CurrentUser();
		int campid=GetData.CampID();
		//Gets list of camps the owner is in to check if he alr attending
		HashMap<Integer, String> camplist = ((CampController) ((CampController)control).FilterUser(userid)).getCamps();
		
		if(camplist.keySet().contains(campid)) {
			System.out.println("Already registered");
			return false;
		}
		
		if(((CampController)control).isAttendeeFull(campid)) {
			System.out.println("Camp is currently full. Please wait for others to withdraw.");
			return false;
		}
		
		((CampController)control).joinCamp(campid, userid, Role.ATTENDEE);
		System.out.println("Registered successfully as an attendee.");
		
		return true;
	}

}
