package camsAction;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Scanner;

import controllers.CampController;
import controllers.Controller;
import controllers.UserController;
import interactions.Interaction;
import types.Perms;
/**
 * Interaction that represents the action of deleting a camp from the database
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public class doDeleteCamp extends Interaction {
	/**
	 * Requests the controller to remove a camp from its database.
	 * Controller is expected to cut off all links if any and ensure the database is consisten with no loose ends
	 *@return true if controller accepts the request(s)
	 *@throws MissingRequestedDataException if camp to be deleted cannot be found
	 */@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws MissingRequestedDataException {
		if(campid==null) throw new MissingRequestedDataException("Camp id is invalid");
		HashSet<String> participantlist = ((CampController)control).getCampComittees(campid);
		for(String participant:participantlist) {
			//Remove committee perms, but allow him to re-apply
			((UserController)control).denyPerms(participant, EnumSet.of(
					Perms.SUBMIT_CAMP_SUGGESTION,
					Perms.EDIT_CAMP_SUGGESTION,
					Perms.DELETE_CAMP_SUGGESTION
				));
			((UserController)control).grantPerms(participant, EnumSet.of(Perms.REGISTER_AS_COMMITTEE));
		}
		((CampController)control).deleteCamp(campid);
		System.out.println("Camp has been deleted. This change will be reflected for participants");
		next = new queryCampsMenu();
		if(this.userid!=null) next = next.withuser(userid);
		if(this.filters!=null) next = next.withfilter(filters);
		return next;
	}

}
