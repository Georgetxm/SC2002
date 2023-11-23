package camsAction;

import java.util.HashMap;
import java.util.Scanner;

import cams.CamsInteraction;
import controllers.CampControlInterface;
import controllers.Controller;
import controllers.ControllerItemMissingException;
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
	 */@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException, MissingRequestedDataException {
		if(campid==null) throw new MissingRequestedDataException("No camp found");
		HashMap<Integer, String> camplist;
		try {
			camplist = ((CampControlInterface) ((CampControlInterface)control).FilterUser(currentuser)).getCamps();
		} catch (ControllerItemMissingException e) {
			throw new UserInfoMissingException("This user id cannot be found");
		}
		
		if(camplist!=null&&camplist.keySet().contains(campid))
			System.out.println("Already registered");
		else if(((CampControlInterface)control).isAttendeeFull(campid))
			System.out.println("Camp is currently full. Please wait for others to withdraw.");
		else {
			((CampControlInterface)control).joinCamp(campid, currentuser, Role.ATTENDEE);
			System.out.println("Registered successfully as an attendee.");
		}
		System.out.println(((CampControlInterface) control).report(campid));
		HashMap<Integer, String> usercamps = null;
		try {
			usercamps = ((CampControlInterface) ((CampControlInterface) control).FilterUser(currentuser)).getCamps();
		} catch (ControllerItemMissingException e) {
			throw new UserInfoMissingException("User id not valid");
		}
		next = (usercamps!=null&&usercamps.containsKey(campid))?CamsInteraction.OwnCampMenu(campid, currentuser):CamsInteraction.OtherCampMenu(campid,currentuser);
		if(this.userid!=null) next = next.withuser(userid);
		if(this.campid!=null) next = next.withcamp(campid);
		if(this.filters!=null) next = next.withfilter(filters);
		return next;
	}

}
