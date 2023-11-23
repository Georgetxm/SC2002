package camsAction;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Scanner;

import cams.CamsInteraction;
import controllers.CampControlInterface;
import controllers.Controller;
import controllers.ControllerItemMissingException;
import controllers.UserControlInterface;
import entities.UserInfoMissingException;
import interactions.Interaction;
import types.Perms;
import types.Role;
/**
 * Interaction that represents the action of adding a user to a camp as a camp committee member.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doSubmitCommitteeRegistration extends Interaction {

	/**
	 * Requests the controller to register the current user as a camp committee member of a given camp
	 * Asks the controller if the camp committee is full or the user is already part of an existing camp committee or the user has already registered for this camp as another role before requesting.
	 * Grants the user permissions afforded to a camp committee member, and removes the permission to reapply for a camp committee
	 *@return true if controller accepts the request(s) and false if otherwise, or the camp is full, or the user is already an existing camp committee member, or the user has already registered for the given camp as another role.
	 *@throws MissingRequestedDataException if the camp to be registered for cannot be found
	 *@throws UserInfoMissingException if the user id of the current user cannot be found
	 */@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException, MissingRequestedDataException {
		next = null;
		HashMap<Integer, String> camplist = null;
		try {
			camplist = ((CampControlInterface) ((CampControlInterface) control).FilterUser(currentuser)).getCamps();
		} catch (ControllerItemMissingException e) {
			throw new UserInfoMissingException("This userid cannot be found");
		}
		if(((CampControlInterface) control).isCommiteeFull(campid)) {
			System.out.println("Committee is full. Please apply again next time.");
		}
		else if(((UserControlInterface) control).getCampCommitteeOfStudent(userid)!=null&&((UserControlInterface) control).getCampCommitteeOfStudent(userid)>=0)
				System.out.println("Already registered for an existing camp commitee");
		else if(camplist!=null&&camplist.keySet().contains(campid)) 
				System.out.println("Already registered for this camp as an attendee.");
		else {
			((CampControlInterface) control).joinCamp(campid, userid, Role.COMMITTEE);
			((UserControlInterface) control).grantPerms(userid, EnumSet.of(
				Perms.SUBMIT_CAMP_SUGGESTION,
				Perms.EDIT_CAMP_SUGGESTION,
				Perms.DELETE_CAMP_SUGGESTION,
				Perms.VIEW_CAMP_ENQUIRY,
				Perms.REPLY_CAMP_ENQUIRY
			));
			((UserControlInterface) control).denyPerms(userid, EnumSet.of(Perms.REGISTER_AS_COMMITTEE));
			System.out.println("Registered successfully as a comittee member.");
		}
		try {
			camplist = ((CampControlInterface) ((CampControlInterface) control).FilterUser(currentuser)).getCamps();
		} catch (ControllerItemMissingException e) {
			throw new UserInfoMissingException("This userid cannot be found");
		}
		next = (camplist!=null&&camplist.keySet().contains(campid))?CamsInteraction.OwnCampMenu(campid, currentuser):CamsInteraction.OtherCampMenu(campid,currentuser);
		if(this.userid!=null) next = next.withuser(userid);
		if(this.campid!=null) next = next.withcamp(campid);
		if(this.filters!=null) next = next.withfilter(filters);
		return next;
	}

}
