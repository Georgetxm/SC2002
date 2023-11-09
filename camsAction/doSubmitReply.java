package camsAction;

import java.util.Scanner;

import controllers.EnquiryController;
import controllers.UserController;
import entities.Data;
import interactions.Interaction;

public final class doSubmitReply extends Interaction {

	@Override
	public final Boolean run() throws Exception {
		if(!Data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!EnquiryController.class.isInstance(Data.get("Controller"))||
			!UserController.class.isInstance(Data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
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
