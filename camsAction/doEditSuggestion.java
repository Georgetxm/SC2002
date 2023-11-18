package camsAction;

import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;

import controllers.ControllerItemMissingException;
import controllers.SuggestionController;
import entities.Data;
import interactions.Interaction;
import types.CampAspect;
/**
 * Interaction that represents the action of changing the contents of a submitted suggestion
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doEditSuggestion extends Interaction {
	/**
	 * Requests the controller to change the content of a given suggestion.
	 * Ask the controller if a suggestion may be edited before requesting the deletion.
	 *@return true if controller accepts the request(s) and false if otherwise, or the suggestion cannot be deleted
	 *@throws MissingRequestedDataException if suggestion to be deleted cannot be found.
	 */
	@Override
	public final Boolean run() throws MissingRequestedDataException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(!SuggestionController.class.isInstance(Data.get("Controller")))	throw new NoSuchElementException("Controller not able enough. Request Failed.");
		SuggestionController suggestioncontrol = (SuggestionController) Data.get("Controller");
		
		int suggestionid = GetData.SuggestionID();
		Scanner s = getScanner();
		CampAspect chosenaspect = null;
		try {
			if(!suggestioncontrol.isSuggestionEditable(suggestionid)) {
				System.out.println("Suggestion has been viewed and may not be edited");
				return false;
			}
			 chosenaspect = suggestioncontrol.getSuggestion(suggestionid).getKey().getKey();
		} catch (ControllerItemMissingException e) {
			throw new MissingRequestedDataException("Suggestion id is invalid");
		}
		

		Entry<CampAspect, ? extends Object> edited;
		
		switch(chosenaspect) { //Depending on the aspect chosen, request data from user
		case DATE: 					edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampDate(s); 		break;
		case REGISTRATION_DEADLINE: edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampRegisterDate(s);	break;
		case LOCATION: 				edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampLocation(s); 	break;
		case SLOTS: 				edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampSlots(s); 		break;
		case DESCRIPTION: 			edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampDescription(s); 	break;
		default: System.out.println("Change to this field is no longer permitted. As such your suggestion cannot be amended."); return false;
		}
		
		System.out.println("Please type your rationale:");
		try {
			suggestioncontrol.editSuggestion(suggestionid, edited, s.nextLine());
		} catch (ControllerItemMissingException e) {
			throw new MissingRequestedDataException("Suggestion id is invalid");
		}
		
		System.out.println("Edits have been saved");
		return null;
	}

}
