package camsAction;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import cams.CamsInteraction;
import controllers.Controller;
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
		//Get list of camps user is in
		HashSet<Serializable> campset = control.Directory().sync().with(entities.User.class,currentuser).get(entities.Camp.class);
		if(campset!=null&&!campset.contains(campid)) { //If user's camps does not contain this camp
			System.out.println("You cannot withdraw from a camp you are not in");
			return CamsInteraction.OtherCampMenu(campid,currentuser);
		}
		else if(control.User().getCampCommitteeOfStudent(currentuser)==campid) { //if user is camp committee of this camp
			System.out.println("Cannot withdraw from camp as a camp committee member");
			next = CamsInteraction.OwnCampMenu(campid,currentuser);
		}
		else{
			control.Directory().sync().delink(Arrays.asList(
				new HashMap.SimpleEntry<Class<?>,Serializable>(entities.Camp.class,campid),
				new HashMap.SimpleEntry<Class<?>,Serializable>(entities.User.class,currentuser)
			));
			System.out.println("Withdrawn Successfully");
			next = CamsInteraction.OtherCampMenu(campid,currentuser);
		}
		if(this.userid!=null) next = next.withuser(userid);
		if(this.filters!=null) next = next.withfilter(filters);
		return next.withcamp(campid);
	}

}
