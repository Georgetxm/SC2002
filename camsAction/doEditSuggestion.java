package camsAction;

import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;

import controllers.SuggestionController;
import entities.Data;
import interactions.Interaction;
import types.CampAspects;
/**
 * Interaction that represents the action of changing the contents of a submitted suggestion
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doEditSuggestion extends Interaction {
	//Currently using distinct control interfaces for dual inheritance
	@Override
	public final Boolean run() throws MissingRequestedDataException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(!SuggestionController.class.isInstance(Data.get("Controller")))	throw new NoSuchElementException("Controller not able enough. Request Failed.");
		SuggestionController suggestioncontrol = (SuggestionController) Data.get("Controller");
		
		int suggestionid = GetData.SuggestionID();
		Scanner s = getScanner();
		
		if(!suggestioncontrol.isSuggestionEditable(suggestionid)) {
			System.out.println("Suggestion has been viewed and may not be edited");
			return false;
		}
		
		CampAspects chosenaspect = suggestioncontrol.getSuggestion(suggestionid).getKey().getKey();
		Entry<CampAspects, ? extends Object> edited;
		
		switch(chosenaspect) { //Depending on the aspect chosen, request data from user
		case DATE: 					edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampDate(s); 		break;
		case REGISTRATION_DEADLINE: edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampRegisterDate(s);	break;
		case LOCATION: 				edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampLocation(s); 	break;
		case SLOTS: 				edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampSlots(s); 		break;
		case DESCRIPTION: 			edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampDescription(s); 	break;
		default: System.out.println("Change to this field is no longer permitted. As such your suggestion cannot be amended."); return false;
		}
		
		System.out.println("Please type your rationale:");
		suggestioncontrol.editSuggestion(suggestionid, edited, s.nextLine());
		
		System.out.println("Edits have been saved");
		return null;
	}

}
