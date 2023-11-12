package camsAction;

import java.util.NoSuchElementException;

import controllers.CampController;
import controllers.SuggestionController;
import controllers.UserController;
import entities.Data;
import interactions.Interaction;
import interactions.MenuChoice;
import interactions.StaticMenu;
/**
 * 
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
/**
 * 
 */
/**
 * 
 */
/**
 * Requests the controller to edit camp details, based off a specific suggestion.
 * Requests the controller to increment points of the user by 1.
 * Onus is on the controller to ensure camp details are edited appropriately and whatever information is kept consistent.
 * @see Interaction
 * @see MenuChoice
 * @see StaticMenu
 */
public final class doApproveSuggestion extends Interaction {
	//Currently using new single umbrella class implementation for dual inheritance
	@Override
	public final Boolean run() throws MissingRequestedDataException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(Data.get("Controller"))||
			!UserController.class.isInstance(Data.get("Controller"))||
			!SuggestionController.class.isInstance(Data.get("Controller"))
		)	throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		
		int campid = GetData.CampID();
		int suggestionid = GetData.SuggestionID();
		String ownerid = ((SuggestionController) control).getOwner(suggestionid);
		
		((CampController) control).editCampDetails(campid, ((SuggestionController) control).getSuggestion(suggestionid).getKey());
		((UserController) control).incrementPoints(ownerid, 1);
		
		System.out.println("Suggestion Approved");
		
		return true;
	}

}
