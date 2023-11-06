package camsAction;

import java.util.HashMap;
import java.util.Scanner;

import controllers.CampController;
import controllers.UserController;
import core.CampAspectValue;
import core.CampInfo;
import interactions.Interaction;

public final class doSubmitSuggestion extends Interaction{
	//Currently using distinct control interfaces for dual inheritance
	@Override
	public final Integer run(HashMap<String, Object> data) throws Exception {
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
		
		CampInfo campinfo;
		if(!data.containsKey("CampInfo")) throw new Exception("Camp info not retrieved");
		try {campinfo = (CampInfo) data.get("CampInfo");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Camp Info. Request Failed.");
		}

		Scanner s = getScanner(data);
		int counter = 1;
		System.out.println("What would you like to amend:");
		for(CampAspectValue aspect: campinfo.info()) {
			System.out.printf("%d: %s", counter, aspect.name().name());
			counter++;
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		}
		int choice = s.nextInt();
		campinfo.info().get(choice-1);
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		String reason;
		System.out.println("Please type your rationale:");
		reason = s.nextLine();
		
		int suggestionid = campcontrol.submitSuggestion(campid, campinfo, reason, userid);
		System.out.println("Your suggestion has been submitted.");
		
		usercontrol.incrementPoints(userid, 1);
		System.out.println("Your points have been incremented");
		System.out.println("This function is incomplete. Await CampInfo finalisation");
		return suggestionid;
	}

}
