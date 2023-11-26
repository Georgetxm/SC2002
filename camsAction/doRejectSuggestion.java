package camsAction;

import java.util.Scanner;

import controllers.Controller;
import controllers.ControllerItemMissingException;
import entities.UserInfoMissingException;
import interactions.Interaction;

public class doRejectSuggestion extends Interaction {

	@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException, MissingRequestedDataException {
		if(suggestionid==null) throw new MissingRequestedDataException("Missing suggestionid");
		try {
			//Remove the past suggestion from both the storage and directory
			control.Suggestion().delete(suggestionid);
			control.Directory().sync().remove(entities.Suggestion.class, suggestionid);
		} catch (ControllerItemMissingException e) {
			throw new MissingRequestedDataException("Suggestion cannot be found");
		}
		System.out.println("Suggestion Rejected");
		next = new querySuggestionsMenu();
		if(userid!=null) next = next.withuser(userid);
		if(campid!=null) next = next.withcamp(campid);
		if(filters!=null) next = next.withfilter(filters);
		return next;
	}

}
