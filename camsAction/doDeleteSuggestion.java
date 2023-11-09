package camsAction;

import java.util.HashMap;

import controllers.SuggestionController;
import controllers.UserController;
import interactions.Interaction;

public final class doDeleteSuggestion extends Interaction {

	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!SuggestionController.class.isInstance(data.get("Controller"))||
			!UserController.class.isInstance(data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		Object control = data.get("Controller");
		
		int suggestionid = GetData.SuggestionID(data);
		String ownerid = ((SuggestionController) control).getOwner(suggestionid);
		
		((UserController) control).incrementPoints(ownerid, -1);
		((SuggestionController) control).deleteSuggestion(suggestionid);
		System.out.println("Suggestion deleted. Points deducted accordingly");
		return true;
	}

}
