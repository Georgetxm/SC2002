package camsAction;

import java.util.HashMap;
import java.util.Scanner;

import controllers.CampController;
import controllers.EnquiryController;
import controllers.UserController;
import interactions.Interaction;

public final class doSubmitEnquiry extends Interaction {
	@Override
	public final Integer run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(data.get("Controller"))||
			!UserController.class.isInstance(data.get("Controller"))||
			!EnquiryController.class.isInstance(data.get("Controller"))
		)
			throw new Exception("Controller not able enough. Request Failed.");
		Object control = data.get("Controller");
		
		int campid = GetData.CampID(data);
		int userid = GetData.CurrentUser(data);
		Scanner s = getScanner(data);
		
		System.out.println("Please type your enquiry:");
		int enquiryid = ((EnquiryController)control).addEnquiry(s.nextLine(),userid,campid);
		System.out.println("Your enquiry has been submitted.");
		
		return enquiryid;
	}

}
