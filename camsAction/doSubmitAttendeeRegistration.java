package camsAction;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import cams.CamsInteraction;
import controllers.Controller;
import interactions.Interaction;
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
	 *@return the appropriate single camp menu with user and filter tags
	 *@throws MissingRequestedDataException if the camp to be registered for cannot be found
	 */@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws MissingRequestedDataException {
		if(campid==null) throw new MissingRequestedDataException("No camp found");
		HashSet<Serializable> camplist;
		camplist = control.Directory().sync().with(entities.User.class, currentuser).get(entities.Camp.class);
		
		if(camplist!=null&&camplist.contains(campid))
			System.out.println("Already registered");
		else if(control.Camp().isAttendeeFull(campid))
			System.out.println("Camp is currently full. Please wait for others to withdraw.");
		else {
			control.Directory().sync().link(Arrays.asList(
				new HashMap.SimpleEntry<Class<?>,Serializable>(entities.Camp.class,campid),
				new HashMap.SimpleEntry<Class<?>,Serializable>(entities.User.class,currentuser)
			));
			System.out.println("Registered successfully as an attendee.");
		}
		HashSet<Serializable> usercamps = null;
		usercamps = control.Directory().sync().with(entities.User.class, currentuser).get(entities.Camp.class);
		next = (usercamps!=null&&usercamps.contains(campid))?CamsInteraction.OwnCampMenu(campid, currentuser):CamsInteraction.OtherCampMenu(campid,currentuser);
		if(this.userid!=null) next = next.withuser(userid);
		if(this.campid!=null) next = next.withcamp(campid);
		if(this.filters!=null) next = next.withfilter(filters);
		return next;
	}

}
