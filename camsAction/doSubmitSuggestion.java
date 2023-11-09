package camsAction;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import controllers.CampController;
import controllers.SuggestionController;
import controllers.UserController;
import interactions.Interaction;
import types.CampAspects;

public final class doSubmitSuggestion extends Interaction{
	//Currently using distinct control interfaces for dual inheritance
	@Override
	public final Integer run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(data.get("Controller"))||
			!UserController.class.isInstance(data.get("Controller"))||
			!SuggestionController.class.isInstance(data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		Object control = data.get("Controller");
		
		int campid = GetData.CampID(data);
		String userid = GetData.CurrentUser(data);
		//Asks campcontrol for the camp info and pulls out the info
		TreeMap<CampAspects,? extends Object> info = ((CampController) control).getCampDetails(campid).info();

		Scanner s = getScanner(data);
		int choice=0;
		while(true) {
			System.out.println("What would you like to amend:");
			int counter = 1;
			for(CampAspects aspect:info.keySet())// For each aspect, print aspect
				System.out.printf("%d: %s\n",counter,GetData.FromObject(info.get(aspect)));
			choice = s.nextInt(); //user chooses an aspect, see if choice is valid
			if(choice<1||choice>info.keySet().size()) {
				System.out.println("Invalid option");
				continue;
			}
			break;
		}

		Entry<CampAspects, ? extends Object> edited;
		CampAspects chosenaspect = (CampAspects) info.keySet().toArray()[choice-1];
		switch(chosenaspect) { //Depending on the aspect chosen, request data from user
		case DATE: 						edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampDate(s); 		break;
		case REGISTRATION_DEADLINE: 	edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampRegisterDate(s);break;
		case LOCATION: 					edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampLocation(s); 	break;
		case SLOTS: 					edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampSlots(s); 		break;
		case DESCRIPTION: 				edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampDescription(s); break;
		default: System.out.println("This field cannot be changed."); return -1;
		}
		
		String reason;
		System.out.println("Please type your rationale:");
		reason = s.nextLine();
		
		int suggestionid = ((SuggestionController) control).addSuggestion(edited, reason, userid, campid);
		System.out.println("Your suggestion has been submitted.");
		
		((UserController) control).incrementPoints(userid, 1);
		System.out.println("Your points have been incremented");
		return suggestionid;
	}

}
