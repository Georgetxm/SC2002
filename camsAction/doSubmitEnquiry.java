package camsAction;

import java.util.NoSuchElementException;
import java.util.Scanner;

import controllers.CampController;
import controllers.EnquiryController;
import controllers.UserController;
import entities.Data;
import entities.UserInfoMissingException;
import interactions.Interaction;

public final class doSubmitEnquiry extends Interaction {
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
