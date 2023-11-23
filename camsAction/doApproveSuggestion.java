package camsAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import controllers.Controller;
import controllers.ControllerItemMissingException;
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
		try {
			//Get owner of suggestion
			HashSet<Serializable> owners = control.Directory().with(entities.Suggestion.class,suggestionid).get(entities.User.class);
			String ownerid = (String) (new ArrayList<Serializable>(owners)).get(0);
			//Get host camp of suggestion
			HashSet<Serializable> hosts = control.Directory().with(entities.Suggestion.class,suggestionid).get(entities.Camp.class);
			int hostcamp = (Integer) (new ArrayList<Serializable>(hosts)).get(0);
			//Pull the suggestion out from control.Suggestion, and request edit details using campid and suggestion body
			control.Camp().editDetails(hostcamp, control.Suggestion().get(suggestionid).getKey());
			//Increment the points of the user
			control.User().incrementPoints(ownerid, 1);
			//Remove the past suggestion from both the storage and directory
			control.Suggestion().delete(suggestionid);
			control.Directory().remove(entities.Suggestion.class, suggestionid);
		} catch (ControllerItemMissingException e) {
			throw new MissingRequestedDataException("Suggestion cannot be found");
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
