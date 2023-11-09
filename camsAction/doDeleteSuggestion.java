package camsAction;

import controllers.SuggestionController;
import controllers.UserController;
import entities.Data;
import interactions.Interaction;

public final class doDeleteSuggestion extends Interaction {

	@Override
	public final Boolean run() throws Exception {
		if(!Data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!SuggestionController.class.isInstance(Data.get("Controller"))||
			!UserController.class.isInstance(Data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		int suggestionid = GetData.SuggestionID();
		String ownerid = ((SuggestionController) control).getOwner(suggestionid);
		
		((UserController) control).incrementPoints(ownerid, -1);
		((SuggestionController) control).deleteSuggestion(suggestionid);
		System.out.println("Suggestion deleted. Points deducted accordingly");
		return true;
	}

}
