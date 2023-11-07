package camsAction;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import controllers.CampController;
import controllers.UserController;
import core.CampInfo;
import interactions.Interaction;
import types.CampAspects;

public final class doEditSuggestion extends Interaction {
	//Currently using distinct control interfaces for dual inheritance
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
		int suggestionid = GetData.SuggestionID(data);
		Scanner s = getScanner(data);
		
		if(!campcontrol.isSuggestionEditable(campid, suggestionid)) {
			System.out.println("Suggestion has been viewed and may not be edited");
			return false;
		}
		CampAspects chosenaspect=campcontrol.getSuggestionAspect(campid, suggestionid);
		Entry<CampAspects, ? extends Object> edited;
		switch(chosenaspect) { //Depending on the aspect chosen, request data from user
		case DATE: 				edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampDate(s); 		break;
		case LASTREGISTERDATE: 	edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampRegisterDate(s);break;
		case LOCATION: 			edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampLocation(s); 	break;
		case SLOTS: 			edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampSlots(s); 		break;
		case DESCRIPTION: 		edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampDescription(s); break;
		default: System.out.println("Change to this field is no longer permitted. As such your suggestion cannot be amended."); return false;
		}
		
		String reason;
		System.out.println("Please type your rationale:");
		reason = s.nextLine();

		usercontrol.incrementPoints(0, 0);
		campcontrol.editSuggestion(campid, suggestionid, edited, "Rationale");
		
		// TODO Auto-generated method stub
		System.out.println("This method is incomplete");
		return null;
	}

}
