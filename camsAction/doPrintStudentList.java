package camsAction;

import java.util.HashMap;
import java.util.Scanner;

import cams.CamsInteraction;
import controllers.CampController;
import controllers.Controller;
import controllers.ControllerItemMissingException;
import entities.UserInfoMissingException;
import interactions.Interaction;
/**
 * Interaction that represents the action of generating and printing a camp report.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doPrintStudentList extends Interaction {
	/**
	 * Requests the controller to delete a given suggestion.
	 * Ask the controller if a suggestion may be edited before requesting the deletion.
	 *@return true if controller accepts the request(s) and false if otherwise, or the suggestion cannot be deleted
	 *@throws MissingRequestedDataException if suggestion to be deleted cannot be found.
	 */@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException, MissingRequestedDataException {
		if(campid==null) throw new MissingRequestedDataException("Camp is not valid");
		System.out.println(((CampController) control).getCampStudentList(campid));
		HashMap<Integer, String> usercamps = null;
		try {
			usercamps = ((CampController) ((CampController) control).FilterUser(currentuser)).getCamps();
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
