package camsAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import controllers.Controller;
import controllers.ControllerItemMissingException;
import interactions.Interaction;

/**
 * Interaction that represents the action of deleting a suggestion.
 * Effectively serves as a function pointer
 * 
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doDeleteSuggestion extends Interaction {
	/**
	 * Requests the controller to delete a given suggestion.
	 * Ask the controller if a suggestion may be edited before requesting the
	 * deletion.
	 * 
	 * @return true if controller accepts the request(s) and false if otherwise, or
	 *         the suggestion cannot be deleted
	 * @throws MissingRequestedDataException  if suggestion to be deleted cannot be
	 *                                        found.
	 */@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws MissingRequestedDataException {
		if(suggestionid==null) throw new MissingRequestedDataException("Invalid suggestion id");
		try {
			if (!control.Suggestion().isEditable(suggestionid)) 
				System.out.println("This suggestion is finalised and can no longer be edited or deleted");
			else{
				HashSet<Serializable> owners = control.Directory().sync().with(entities.Suggestion.class, suggestionid).get(entities.User.class);
				String owner = (String) new ArrayList<Serializable>(owners).get(0);
				control.User().incrementPoints(owner, -1);
				control.Suggestion().delete(suggestionid);
				control.Directory().sync().remove(entities.Suggestion.class, suggestionid);
				System.out.println("Suggestion deleted. Points deducted accordingly");
			}
		} catch (ControllerItemMissingException e) {
			throw new MissingRequestedDataException("Suggestion Id is invalid");
		}
		next = new querySuggestionsMenu();
		if(this.userid!=null) next = next.withuser(userid);
		if(this.campid!=null) next = next.withcamp(campid);
		if(this.filters!=null) next = next.withfilter(filters);
		if(this.ownerid!=null) next = next.withowner(this.ownerid);
		return next.withsuggestion(suggestionid);
	}

}
