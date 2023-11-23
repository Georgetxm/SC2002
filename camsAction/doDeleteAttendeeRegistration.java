package camsAction;

import java.util.HashMap;
import java.util.Scanner;

import cams.CamsInteraction;
import controllers.CampControlInterface;
import controllers.Controller;
import controllers.ControllerItemMissingException;
import controllers.UserControlInterface;
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
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws MissingRequestedDataException, UserInfoMissingException {
		if(campid==null) throw new MissingRequestedDataException("Camp not found");
		HashMap<Integer, String> campset = null;
		try {
			campset = ((CampControlInterface) ((CampControlInterface) control).FilterUser(currentuser)).getCamps();
		} catch (ControllerItemMissingException e) {
			throw new UserInfoMissingException("User invalid");
		}
		if(campset!=null&&campset.keySet().contains(campid)) {
			System.out.println("You cannot withdraw from a camp you are not in");
			return CamsInteraction.OtherCampMenu(campid,currentuser);
		}
		else if(((UserControlInterface) control).getCampCommitteeOfStudent(currentuser)==campid) {
			System.out.println("Cannot withdraw from camp as a camp committee member");
			next = CamsInteraction.OwnCampMenu(campid,currentuser);
		}
		else{
			((CampControlInterface)control).removeAttendeeFromCamp(campid, currentuser);
			System.out.println("Withdrawn Successfully");
			next = CamsInteraction.OtherCampMenu(campid,currentuser);
		}
		if(this.userid!=null) next = next.withuser(userid);
		if(this.filters!=null) next = next.withfilter(filters);
		return next.withcamp(campid);
	}

}
