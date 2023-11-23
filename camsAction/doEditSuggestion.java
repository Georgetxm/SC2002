package camsAction;

import java.util.Map.Entry;
import java.util.Scanner;

import controllers.Controller;
import controllers.ControllerItemMissingException;
import controllers.SuggestionControlInterface;
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
	 */@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws MissingRequestedDataException {
		if(suggestionid==null) throw new MissingRequestedDataException("Suggstion id invalid");
		CampAspect chosenaspect = null;
		try {
			if(!((SuggestionControlInterface) control).isEditable(suggestionid))
				System.out.println("Suggestion has been viewed and may not be edited");
			else chosenaspect = ((SuggestionControlInterface) control).get(suggestionid).getKey().getKey();
		} catch (ControllerItemMissingException e) {
			throw new MissingRequestedDataException("Suggestion id is invalid");
		}
		Entry<CampAspect, ? extends Object> edited = null;
		
		if(chosenaspect!=null) switch(chosenaspect) { //Depending on the aspect chosen, request data from user
			case DATE: 					edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampDate(s); 		break;
			case REGISTRATION_DEADLINE: edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampRegisterDate(s);	break;
			case LOCATION: 				edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampLocation(s); 	break;
			case SLOTS: 				edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampSlots(s); 		break;
			case DESCRIPTION: 			edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampDescription(s); 	break;
			default: System.out.println("Change to this field is no longer permitted. As such your suggestion cannot be amended.");
		}
		if(edited!=null) {
			System.out.println("Please type your rationale:");
			try {
				((SuggestionControlInterface) control).editSuggestion(suggestionid, edited, s.nextLine());
			} catch (ControllerItemMissingException e) {
				throw new MissingRequestedDataException("Suggestion id is invalid");
			}
			System.out.println("Edits have been saved");
		}
		next = new querySuggestionsMenu();
		if(this.userid!=null) next = next.withuser(userid);
		if(this.campid!=null) next = next.withcamp(campid);
		if(this.filters!=null) next = next.withfilter(filters);
		if(this.ownerid!=null) next = next.withowner(this.ownerid);
		return next.withsuggestion(suggestionid);
	}

}
