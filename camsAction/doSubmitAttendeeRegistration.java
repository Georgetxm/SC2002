package camsAction;

import java.util.HashMap;
import java.util.NoSuchElementException;

import controllers.CampController;
import controllers.ControllerItemMissingException;
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
	/**
	 * Requests the controller to register the current user as an attendee of a given camp
	 * Asks the controller if the camp is full or the user has already joined before requesting.
	 *@return true if controller accepts the request(s) and false if otherwise, or the camp is full, or the user is already registered
	 *@throws MissingRequestedDataException if the camp to be registered for cannot be found
	 *@throws UserInfoMissingException if the user id of the current user cannot be found
	 */
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
		HashMap<Integer, String> camplist;
		try {
			camplist = ((CampController) ((CampController)control).FilterUser(userid)).getCamps();
		} catch (ControllerItemMissingException e) {
			System.out.print("This user id cannot be found");
			return false;
		}
		
		if(camplist!=null&&camplist.keySet().contains(campid)) {
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
