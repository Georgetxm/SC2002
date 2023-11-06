package camsAction;

import java.util.HashMap;
import java.util.Scanner;

import controllers.CampController;
import core.CampAspectValue;
import core.CampInfo;
import core.Utility;
import interactions.Interaction;

public class doSubmitSuggestion extends Interaction{

	@Override
	public final Integer run(HashMap<String, Object> data) throws Exception {
		CampController controller;
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		try {controller = (CampController) data.get("Controller");}
		catch(ClassCastException e) {
			throw new Exception("Controller lacking required methods. Request Failed.");
		}
		int campid;
		if(!data.containsKey("CurrentCamp")) throw new Exception("Did not select camp. Request Failed.");
		try {campid = (int) data.get("CurrentCamp");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Camp ID. Request Failed.");
		}
		
		CampInfo campinfo;
		if(!data.containsKey("CampInfo")) throw new Exception("Camp info not retrieved");
		try {campinfo = (CampInfo) data.get("CampInfo");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Camp Info. Request Failed.");
		}

		Scanner s = Utility.getScanner(data);
		
		System.out.println("What would you like to amend:");
		for(CampAspectValue aspect: campinfo.info()) {
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		}
		int choice = s.nextInt();
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		String reason;
		System.out.println("Please type your rationale:");
		reason = s.nextLine();
		int suggestionid = controller.submitSuggestion(campid, campinfo, reason);
		System.out.println("Your suggestion has been submitted.");
		
		System.out.println("This function is incomplete. Await CampInfo finalisation");
		return suggestionid;
	}

}
