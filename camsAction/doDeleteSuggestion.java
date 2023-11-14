package camsAction;

import java.util.NoSuchElementException;

import controllers.SuggestionController;
import controllers.UserController;
import entities.Data;
import interactions.Interaction;
/**
 * Interaction that represents the action of deleting a suggestion.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doDeleteSuggestion extends Interaction {
	/**
	 * Requests the controller to delete a given suggestion.
	 * Ask the controller if a suggestion may be edited before requesting the deletion.
	 *@return true if controller accepts the request(s) and false if otherwise, or the suggestion cannot be deleted
	 *@throws MissingRequestedDataException if suggestion to be deleted cannot be found.
	 */
	@Override
	public final Boolean run() throws MissingRequestedDataException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(
			!SuggestionController.class.isInstance(Data.get("Controller"))||
			!UserController.class.isInstance(Data.get("Controller"))
		)	throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		int suggestionid = GetData.SuggestionID();
		if(((SuggestionController) control).isSuggestionEditable(suggestionid)) {
			System.out.println("This suggestion is finalised and can no longer be edited or deleted");
			return false;
		}
		String ownerid = ((SuggestionController) control).getOwner(suggestionid);
		
		((UserController) control).incrementPoints(ownerid, -1);
		((SuggestionController) control).deleteSuggestion(suggestionid);
		System.out.println("Suggestion deleted. Points deducted accordingly");
		return true;
	}

}
