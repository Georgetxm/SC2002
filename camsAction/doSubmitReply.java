package camsAction;

import java.util.HashMap;
import java.util.Scanner;

import controllers.CampController;
import controllers.UserController;
import interactions.Interaction;

public final class doSubmitReply extends Interaction {

	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(data.get("Controller"))||
			!UserController.class.isInstance(data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		CampController campcontrol = (CampController) data.get("Controller");
		UserController usercontrol = (UserController) data.get("Controller");
		
		int campid = GetData.CampID(data);
		int userid = GetData.CurrentUser(data);
		
		Scanner s = getScanner(data);
		System.out.println("Please type your reply.");
		
		campcontrol.submitReply(campid, userid, s.nextLine());
		System.out.println("Reply Submitted");
		usercontrol.incrementPoints(userid, 1);
		return true;
	}

}
