package camsAction;

import java.io.Serializable;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import cams.CamsInteraction;
import controllers.Controller;
import entities.UserInfoMissingException;
import interactions.Interaction;
import types.Perms;
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
		HashSet<Serializable> camplist = null;
		camplist = control.Directory().sync().with(entities.User.class, currentuser).get(entities.Camp.class);
		if(control.Camp().isCommitteeFull(campid)) {
			System.out.println("Committee is full. Please apply again next time.");
		}
		else if(control.User().getCampCommitteeOfStudent(userid)!=null&&control.User().getCampCommitteeOfStudent(userid)>=0)
				System.out.println("Already registered for an existing camp commitee");
		else if(camplist!=null&&camplist.contains(campid)) 
				System.out.println("Already registered for this camp as an attendee.");
		else {
			control.Directory().sync().link(Arrays.asList(
					new HashMap.SimpleEntry<Class<?>,Serializable>(entities.Camp.class,campid),
					new HashMap.SimpleEntry<Class<?>,Serializable>(entities.User.class,currentuser)
			));
			control.User().grantPerms(userid, EnumSet.of(
				Perms.SUBMIT_CAMP_SUGGESTION,
				Perms.EDIT_CAMP_SUGGESTION,
				Perms.DELETE_CAMP_SUGGESTION,
				Perms.VIEW_CAMP_ENQUIRY,
				Perms.REPLY_CAMP_ENQUIRY
			));
			control.User().denyPerms(userid, EnumSet.of(Perms.REGISTER_AS_COMMITTEE));
			System.out.println("Registered successfully as a comittee member.");
		}
		camplist = control.Directory().sync().with(entities.User.class, currentuser).get(entities.Camp.class);
		next = (camplist!=null&&camplist.contains(campid))?CamsInteraction.OwnCampMenu(campid, currentuser):CamsInteraction.OtherCampMenu(campid,currentuser);
		if(this.userid!=null) next = next.withuser(userid);
		if(this.campid!=null) next = next.withcamp(campid);
		if(this.filters!=null) next = next.withfilter(filters);
		return next;
	}

}
