package camsAction;

import java.util.Scanner;

import controllers.CampController;
import controllers.Controller;
import controllers.ControllerItemMissingException;
import controllers.SuggestionController;
import controllers.UserController;
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
	 */
@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws MissingRequestedDataException {
		
		if(suggestionid==null) throw new MissingRequestedDataException("Missing suggestionid");
		
		String ownerid;
		try {
			ownerid = ((SuggestionController) control).getSuggestionOwner(suggestionid);
			((CampController) control).editCampDetails(((SuggestionController) control).getHostCamp(suggestionid),
			((SuggestionController) control).getSuggestion(suggestionid).getKey());
			((UserController) control).incrementPoints(ownerid, 1);
			((SuggestionController) control).deleteSuggestion(suggestionid);
		} catch (ControllerItemMissingException e) {
			throw new MissingRequestedDataException("Suggestion ccannot be found");
		}
		System.out.println("Suggestion Approved");
		next = new querySuggestionsMenu();
		if(userid!=null) next = next.withuser(userid);
		if(campid!=null) next = next.withcamp(campid);
		if(filters!=null) next = next.withfilter(filters);
		if(this.ownerid!=null) next = next.withowner(this.ownerid);
		return next.withsuggestion(suggestionid);
	}

}
