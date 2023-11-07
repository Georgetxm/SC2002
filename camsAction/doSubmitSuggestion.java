package camsAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import controllers.CampController;
import controllers.UserController;
import core.CampInfo;
import interactions.Interaction;
import types.CampAspects;

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
		
		int campid = GetData.CampID(data);
		int userid = GetData.CurrentUser(data);
		CampInfo campinfo = GetData.CampInfo(data);

		Scanner s = getScanner(data);
		int counter = 1;
		System.out.println("What would you like to amend:");
		for(CampAspects aspect: campinfo.info().keySet()) {
			System.out.printf("%d: %s", counter, aspect.name());
			counter++;
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		}
		int choice = s.nextInt();
		new ArrayList<Entry<CampAspects, Object>>(campinfo.info().entrySet()).get(choice-1);
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
