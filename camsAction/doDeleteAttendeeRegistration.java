package camsAction;

import java.util.NoSuchElementException;

import controllers.CampController;
import controllers.UserController;
import entities.Data;
import entities.UserInfoMissingException;
import interactions.Interaction;
/**
 * Interaction that represents the action of removing a user from a camp.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public class doDeleteAttendeeRegistration extends Interaction {

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
		
		((CampController)control).removeAttendeeFromCamp(campid, userid);
		System.out.println("Withdrawn Successfully");
		return true;
	}

}