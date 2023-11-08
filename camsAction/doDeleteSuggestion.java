package camsAction;

import java.util.HashMap;

import controllers.CampController;
import controllers.UserController;
import interactions.Interaction;

public final class doDeleteSuggestion extends Interaction {

	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(data.get("Controller"))||
			!UserController.class.isInstance(data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		CampController campcontrol = (CampController) data.get("Controller");
		UserController usercontrol = (UserController) data.get("Controller");
		
		int campid = GetData.CampID(data);
		int suggestionid = GetData.SuggestionID(data);
		
		campcontrol.getSuggestionOwner(campid, suggestionid);
		usercontrol.incrementPoints(campid, -1);
		campcontrol.deleteSuggestion(campid, suggestionid);
		
		System.out.println("Suggestion deleted. Points deducted accordingly");
		return true;
	}

}
