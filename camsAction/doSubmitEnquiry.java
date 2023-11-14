package camsAction;

import java.util.NoSuchElementException;
import java.util.Scanner;

import controllers.CampController;
import controllers.EnquiryController;
import controllers.UserController;
import entities.Data;
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
	public final Integer run() throws UserInfoMissingException, MissingRequestedDataException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(Data.get("Controller"))||
			!UserController.class.isInstance(Data.get("Controller"))||
			!EnquiryController.class.isInstance(Data.get("Controller"))
		)
			throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		int campid = GetData.CampID();
		String userid = GetData.CurrentUser();
		Scanner s = getScanner();
		
		System.out.println("Please type your enquiry:");
		int enquiryid = ((EnquiryController)control).addEnquiry(s.nextLine(),userid,campid);
		System.out.println("Your enquiry has been submitted.");
		
		return enquiryid;
	}

}
