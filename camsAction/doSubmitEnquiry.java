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
 * Interaction that represents the action of creating and saving an enquiry regarding some camp, to the database.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doSubmitEnquiry extends Interaction {
	/**
	 * Prompts the user for an enquiry and requests the controller to save it.
	 * Controller is expected to make the relevant bidirectional links with the host camp, the user etc.
	 *@return the appropriate single camp menu with user, camp, filter tags
	 *@throws MissingRequestedDataException if the camp the enquiry is part of cannot be found
	 *@throws UserInfoMissingException if the user id of the current user cannot be found
	 */
	@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException, MissingRequestedDataException {
		if(campid==null) throw new MissingRequestedDataException("Camp ID wrong");
		if(control.User().getCampCommitteeOfStudent(currentuser)==campid)
			System.out.println("You are a committee member of this camp and may not submit an enquiry");
		System.out.println("Please type your enquiry:");
		int thisenquiry = control.Enquiry().add(s.nextLine());
		control.Directory().sync().add(entities.Enquiry.class, thisenquiry);
		control.Directory().sync().link(Arrays.asList(
				new HashMap.SimpleEntry<Class<?>,Serializable>(entities.Camp.class,campid),
				new HashMap.SimpleEntry<Class<?>,Serializable>(entities.Enquiry.class,thisenquiry)
		));
		control.Directory().sync().link(Arrays.asList(
				new HashMap.SimpleEntry<Class<?>,Serializable>(entities.User.class,currentuser),
				new HashMap.SimpleEntry<Class<?>,Serializable>(entities.Enquiry.class,thisenquiry)
		));
		System.out.println("Your enquiry has been submitted.");
		HashSet<Serializable> usercamps = null;
		usercamps = control.Directory().sync().with(entities.User.class, currentuser).get(entities.Camp.class);
		next = (usercamps!=null&&usercamps.contains(campid))?CamsInteraction.OwnCampMenu(campid, currentuser):CamsInteraction.OtherCampMenu(campid,currentuser);
		if(this.userid!=null) next = next.withuser(userid);
		if(this.campid!=null) next = next.withcamp(campid);
		if(this.filters!=null) next = next.withfilter(filters);
		return next;
	}

}
