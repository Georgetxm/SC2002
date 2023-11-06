package camsAction;

import java.util.HashMap;
import java.util.Scanner;

import controllers.CampController;
import controllers.UserController;
import interactions.Interaction;

public class doSubmitEnquiryReply extends Interaction {

	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(data.get("Controller"))||
			!UserController.class.isInstance(data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		CampController campcontrol = (CampController) data.get("Controller");
		UserController usercontrol = (UserController) data.get("Controller");
		
		int campid;
		if(!data.containsKey("CurrentCamp")) throw new Exception("Did not select camp. Request Failed.");
		try {campid = (int) data.get("CurrentCamp");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Camp ID. Request Failed.");
		}
		
		int userid;
		if(!data.containsKey("CurrentUser")) throw new Exception("User not identified. Request Failed.");
		try {userid = (int) data.get("CurrentUser");}
		catch(ClassCastException e) {
			throw new Exception("Invalid User ID. Request Failed.");
		}
		
		Scanner s = getScanner(data);
		System.out.println("Please type your reply.");
		
		campcontrol.submitReply(campid, userid, s.nextLine());
		System.out.println("Reply Submitted");
		return true;
	}

}
