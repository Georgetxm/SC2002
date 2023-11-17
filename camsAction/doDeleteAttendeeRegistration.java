package camsAction;

import java.util.NoSuchElementException;

import controllers.CampController;
import controllers.Controller;
import controllers.ControllerItemMissingException;
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
	/**
	 * Requests the controller to remove a bidirectional link between the current user and a camp specified.
	 * Does not check if such a link already exists
	 *@return true if controller accepts the request(s)
	 *@throws MissingRequestedDataException if camp specified cannot be found.
	 *@throws UserInfoMissingException if the current user's ID cannot be found
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
		try {
			if(!((CampController) ((Controller) control).FilterUser(userid)).getCamps().keySet().contains(campid)) {
				System.out.println("You cannot withdraw from a camp you are not in");
				return false;
			}
		} catch (ControllerItemMissingException e) {
			throw new MissingRequestedDataException("Camp invalid");
		}
		if(((UserController) control).getCampCommitteeOfStudent(userid)==campid) {
			System.out.println("Cannot withdraw from camp as a camp committee member");
			return false;
		}
		
		((CampController)control).removeAttendeeFromCamp(campid, userid);
		System.out.println("Withdrawn Successfully");
		return true;
	}

}
