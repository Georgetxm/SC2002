package camsAction;

import java.util.NoSuchElementException;

import controllers.CampController;
import controllers.ControllerItemMissingException;
import controllers.SuggestionController;
import controllers.UserController;
import entities.Data;
import interactions.Interaction;

/**
 * Interaction that represents the action of approving a suggestion.
 * Effectively serves as a function pointer
 * 
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doApproveSuggestion extends Interaction {
	/**
	 * Requests the controller to edit a camp's details based off a suggestion.
	 * Requests the controller to increment the points of the suggestion owner.
	 * Requests the controller to delete the suggestion, as it has already been
	 * approved.
	 * 
	 * @return true if controller accepts the request(s)
	 * @throws MissingRequestedDataException  if camp to be edited cannot be found
	 * @throws MissingRequestedDataException  if suggestion to be implemented cannot
	 *                                        be found
	 * @throws MissingRequestedDataException  if user to have points incremented
	 *                                        cannot be found
	 * @throws ControllerItemMissingException
	 */
	@Override
	public final Boolean run() throws MissingRequestedDataException, ControllerItemMissingException {
		if (!Data.containsKey("Controller"))
			throw new NoSuchElementException("No controller found. Request Failed.");
		if (!CampController.class.isInstance(Data.get("Controller")) ||
				!UserController.class.isInstance(Data.get("Controller")) ||
				!SuggestionController.class.isInstance(Data.get("Controller")))
			throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");

		int campid = GetData.CampID();
		int suggestionid = GetData.SuggestionID();
		String ownerid = ((SuggestionController) control).getSuggestionOwner(suggestionid);

		((CampController) control).editCampDetails(campid,
				((SuggestionController) control).getSuggestion(suggestionid).getKey());
		((UserController) control).incrementPoints(ownerid, 1);
		((SuggestionController) control).deleteSuggestion(suggestionid);

		System.out.println("Suggestion Approved");

		return true;
	}

}
