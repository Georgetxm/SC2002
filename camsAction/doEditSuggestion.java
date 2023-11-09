package camsAction;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import controllers.SuggestionController;
import interactions.Interaction;
import types.CampAspects;

public final class doEditSuggestion extends Interaction {
	//Currently using distinct control interfaces for dual inheritance
	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(!SuggestionController.class.isInstance(data.get("Controller")))	throw new Exception("Controller not able enough. Request Failed.");
		SuggestionController suggestioncontrol = (SuggestionController) data.get("Controller");
		
		int suggestionid = GetData.SuggestionID(data);
		Scanner s = getScanner(data);
		
		if(!suggestioncontrol.isSuggestionEditable(suggestionid)) {
			System.out.println("Suggestion has been viewed and may not be edited");
			return false;
		}
		
		CampAspects chosenaspect = suggestioncontrol.getSuggestion(suggestionid).getKey().getKey();
		Entry<CampAspects, ? extends Object> edited;
		
		switch(chosenaspect) { //Depending on the aspect chosen, request data from user
		case DATE: 				edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampDate(s); 		break;
		case LASTREGISTERDATE: 	edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampRegisterDate(s);	break;
		case LOCATION: 			edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampLocation(s); 	break;
		case SLOTS: 			edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampSlots(s); 		break;
		case DESCRIPTION: 		edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampDescription(s); 	break;
		default: System.out.println("Change to this field is no longer permitted. As such your suggestion cannot be amended."); return false;
		}
		
		System.out.println("Please type your rationale:");
		suggestioncontrol.editSuggestion(suggestionid, edited, s.nextLine());
		
		System.out.println("Edits have been saved");
		return null;
	}

}
