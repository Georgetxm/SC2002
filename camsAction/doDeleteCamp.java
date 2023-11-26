package camsAction;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Scanner;

import controllers.Controller;
import controllers.ControllerItemMissingException;
import entities.Student;
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
	 * Asks controller for all the camp committee members associated with the camp, and resets their permissions to attendee permissions
	 * Requests the controller to remove a camp from its database inclusive of links
	 *@return querycamps menu with user and filter tags
	 *@throws MissingRequestedDataException if camp to be deleted cannot be found
	 */@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws MissingRequestedDataException {
		if(campid==null) throw new MissingRequestedDataException("Camp id is invalid");
		
		//Get everyone, staff, committee or whatever associated with the camp. Is a set of strings.
		HashSet<Serializable> everyone = control.Directory().sync().with(entities.Camp.class, campid).get(entities.User.class);
		for(Serializable name: everyone)
			//If dude is a student and camp committee of this camp
			if(control.User().getClass((String) name)==Student.class&&control.User().getCampCommitteeOfStudent((String) name)==campid) {
				control.User().denyPerms((String)name, EnumSet.of(
						Perms.SUBMIT_CAMP_SUGGESTION,
						Perms.EDIT_CAMP_SUGGESTION,
						Perms.DELETE_CAMP_SUGGESTION
					));
				control.User().grantPerms((String)name, EnumSet.of(Perms.REGISTER_AS_COMMITTEE));
				control.User().setCampCommittee((String)name, -1);
			}
		
		for(Serializable id: control.Directory().sync().with(entities.Camp.class, campid).get(entities.Enquiry.class)) {
			try {
				control.Enquiry().delete((int)id);
			} catch (ControllerItemMissingException e) {
				throw new MissingRequestedDataException("Camp ID incorrect");
			}
			control.Directory().sync().remove(entities.Enquiry.class, id);
		}
		for(Serializable id: control.Directory().sync().with(entities.Camp.class, campid).get(entities.Suggestion.class)) {
			try {
				control.Suggestion().delete((int)id);
			} catch (ControllerItemMissingException e) {
				System.out.println("Suggestion already deleted.");
			}
			control.Directory().sync().remove(entities.Enquiry.class, id);
		}
		control.Camp().delete(campid);
		control.Directory().sync().remove(entities.Camp.class, campid);
		System.out.println("Camp has been deleted. This change will be reflected for participants");
		next = new queryCampsMenu();
		if(this.userid!=null) next = next.withuser(userid);
		if(this.filters!=null) next = next.withfilter(filters);
		return next;
	}

}
