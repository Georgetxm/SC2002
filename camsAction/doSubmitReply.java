package camsAction;

import java.util.NoSuchElementException;
import java.util.Scanner;

import controllers.EnquiryController;
import controllers.UserController;
import entities.Data;
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
	 */
	@Override
	public final Boolean run() throws MissingRequestedDataException, UserInfoMissingException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(
			!EnquiryController.class.isInstance(Data.get("Controller"))||
			!UserController.class.isInstance(Data.get("Controller"))
		)	throw new NoSuchElementException("Controller not able enough. Request Failed.");
		EnquiryController enquirycontrol = (EnquiryController) Data.get("Controller");
		UserController usercontrol = (UserController) Data.get("Controller");
		
		int enquiryid = GetData.EnquiryID();
		String userid = GetData.CurrentUser();
		
		Scanner s = getScanner();
		System.out.println("Please type your reply.");
		
		enquirycontrol.saveReply(enquiryid, s.nextLine());
		System.out.println("Reply Submitted");
		usercontrol.incrementPoints(userid, 1);
		return true;
	}

}
