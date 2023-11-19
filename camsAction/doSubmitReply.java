package camsAction;

import java.util.Scanner;

import cams.CamsInteraction;
import controllers.Controller;
import controllers.ControllerItemMissingException;
import controllers.EnquiryController;
import controllers.UserController;
import entities.UserInfoMissingException;
import interactions.Interaction;
/**
 * Interaction that represents the action creating and saving a reply to some enquiry to the database.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doSubmitReply extends Interaction {
	/**
	 * Requests the controller to save a reply by the current user to a given enquiry into the database.
	 * Requests the user to increment the points of the replier by 1
	 * Prompts the current user for the reply before requesting.
	 * Controller is expected to minimally make a link from the suggestion to the replies if applicable.
	 *@return true if controller accepts the request(s) and false if otherwise.
	 *@throws MissingRequestedDataException if the enquiry to be replied to cannot be found
	 *@throws UserInfoMissingException if the user id of the current user cannot be found
	 */@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException, MissingRequestedDataException {
		System.out.println("Please type your reply.");
		try {
			((EnquiryController) control).saveReply(enquiryid, s.nextLine());
		} catch (ControllerItemMissingException e) {
			throw new MissingRequestedDataException("This enquiry cannot be found");
		}
		System.out.println("Reply Submitted");
		((UserController) control).incrementPoints(currentuser, 1);
		next = CamsInteraction.SingleEnquiryMenu(enquiryid);
		if(this.userid!=null) next = next.withuser(userid);
		if(this.campid!=null) next = next.withcamp(campid);
		if(this.filters!=null) next = next.withfilter(filters);
		if(this.ownerid!=null) next = next.withowner(this.ownerid);
		return next.withenquiry(enquiryid);
	}

}
