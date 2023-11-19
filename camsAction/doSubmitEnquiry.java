package camsAction;

import java.util.HashMap;
import java.util.Scanner;

import cams.CamsInteraction;
import controllers.CampController;
import controllers.Controller;
import controllers.ControllerItemMissingException;
import controllers.EnquiryController;
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
	 *@return true if controller accepts the request(s) and false if otherwise.
	 *@throws MissingRequestedDataException if the camp the enquiry is part of cannot be found
	 *@throws UserInfoMissingException if the user id of the current user cannot be found
	 */
	@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException, MissingRequestedDataException {
		if(campid==null) throw new MissingRequestedDataException("Camp ID wrong");
		System.out.println("Please type your enquiry:");
		((EnquiryController)control).addEnquiry(s.nextLine(),currentuser,campid);
		System.out.println("Your enquiry has been submitted.");
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
		if(this.ownerid!=null) next = next.withowner(this.ownerid);
		return next;
	}

}
