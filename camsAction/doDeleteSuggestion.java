package camsAction;

import java.util.HashMap;

import controllers.CampController;
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
		SuggestionController suggestioncontrol = (SuggestionController) data.get("Controller");
		UserController usercontrol = (UserController) data.get("Controller");
		CampController campcontrol = (CampController) data.get("Controller");
		
		int suggestionid = GetData.SuggestionID(data);
		int ownerid = suggestioncontrol.getOwner(suggestionid);
		int campid = suggestioncontrol.getHostCamp(suggestionid);
		
		usercontrol.incrementPoints(ownerid, -1);
		suggestioncontrol.deleteSuggestion(suggestionid);
		usercontrol.deleteSuggestion(ownerid, suggestionid);
		campcontrol.deleteSuggestion(campid, suggestionid);
		System.out.println("Suggestion deleted. Points deducted accordingly");
		return true;
	}

}
