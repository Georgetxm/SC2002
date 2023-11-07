package camsAction;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
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
		//Asks campcontrol for the camp info and pulls out the info
		TreeMap<CampAspects,Object> info = campcontrol.getCampDetails(campid).info();

		Scanner s = getScanner(data);
		int choice=0;
		while(true) {
			System.out.println("What would you like to amend:");
			int counter = 1;
			for(CampAspects aspect:info.keySet())
				System.out.printf("%d: %s\n",counter,GetData.FromObject(info.get(aspect)));
			choice = s.nextInt();
			if(choice<1||choice>info.keySet().size()) {
				System.out.println("Invalid option");
				continue;
			}
			break;
		}

		Entry<CampAspects, ? extends Object> editedvalue;
		CampAspects chosenaspect = (CampAspects) info.keySet().toArray()[choice-1];
		switch(chosenaspect) {
		case DATE: 				editedvalue = (Entry<CampAspects, ? extends Object>) ParseInput.CampDate(s); 		break;
		case LASTREGISTERDATE: 	editedvalue = (Entry<CampAspects, ? extends Object>) ParseInput.CampRegisterDate(s);break;
		case LOCATION: 			editedvalue = (Entry<CampAspects, ? extends Object>) ParseInput.CampLocation(s); 	break;
		case SLOTS: 			editedvalue = (Entry<CampAspects, ? extends Object>) ParseInput.CampSlots(s); 		break;
		case DESCRIPTION: 		editedvalue = (Entry<CampAspects, ? extends Object>) ParseInput.CampDescription(s); break;
		default: System.out.println("This field cannot be changed."); return -1;
		}
		info.put(chosenaspect, editedvalue);
		CampInfo edited = new CampInfo(info);
		
		String reason;
		System.out.println("Please type your rationale:");
		reason = s.nextLine();
		
		int suggestionid = campcontrol.submitSuggestion(campid, edited, reason, userid);
		System.out.println("Your suggestion has been submitted.");
		
		usercontrol.incrementPoints(userid, 1);
		System.out.println("Your points have been incremented");
		return suggestionid;
	}

}
