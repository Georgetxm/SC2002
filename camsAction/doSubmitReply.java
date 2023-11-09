package camsAction;

import java.util.HashMap;
import java.util.Scanner;

import controllers.EnquiryController;
import controllers.UserController;
import interactions.Interaction;

public final class doSubmitReply extends Interaction {

	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!EnquiryController.class.isInstance(data.get("Controller"))||
			!UserController.class.isInstance(data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		EnquiryController enquirycontrol = (EnquiryController) data.get("Controller");
		UserController usercontrol = (UserController) data.get("Controller");
		
		int enquiryid = GetData.EnquiryID(data);
		String userid = GetData.CurrentUser(data);
		
		Scanner s = getScanner(data);
		System.out.println("Please type your reply.");
		
		enquirycontrol.saveReply(enquiryid, s.nextLine());
		System.out.println("Reply Submitted");
		usercontrol.incrementPoints(userid, 1);
		return true;
	}

}
