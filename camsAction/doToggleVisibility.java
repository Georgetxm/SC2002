package camsAction;

import java.util.HashMap;
import java.util.Scanner;

import cams.CamsInteraction;
import controllers.CampControlInterface;
import controllers.Controller;
import controllers.ControllerItemMissingException;
import entities.UserInfoMissingException;
import interactions.Interaction;
/**
 * Interaction that represents the action of changing the visibility of a camp to a value other than its original value.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doToggleVisibility extends Interaction {
	/**
	 * Requests the controller to modify a camp's visibility between visible and not visible.
	 *@return true if controller accepts the request(s) and false if otherwise.
	 * @throws UserInfoMissingException 
	 *@throws MissingRequestedDataException if the camp to be made visible or not visible cannot be found.
	 */
@Override
	public Interaction run(String currentuser, Scanner s, Controller control) throws UserInfoMissingException{
		System.out.println("Camp visibility changed. Camp is now:");
		System.out.println(((CampControlInterface) control).toggleCampVisiblity(campid)?"Visible":"Not Visible");
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
