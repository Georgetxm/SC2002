package camsAction;

import java.util.HashMap;
import controllers.CampController;
import controllers.SuggestionController;
import controllers.UserController;
import interactions.Interaction;

public final class doApproveSuggestion extends Interaction {
	//Currently using new single umbrella class implementation for dual inheritance
	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(data.get("Controller"))||
			!UserController.class.isInstance(data.get("Controller"))||
			!SuggestionController.class.isInstance(data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		Object control = data.get("Controller");
		
		
		int campid = GetData.CampID(data);
		int suggestionid = GetData.SuggestionID(data);
		int ownerid = ((SuggestionController) control).getOwner(suggestionid);
		
		((CampController) control).editCampDetails(campid, ((SuggestionController) control).getSuggestion(suggestionid).getKey());
		((UserController) control).incrementPoints(ownerid, 1);
		
		if(!GetData.isSilenced(data)) System.out.println("Suggestion Approved");
		
		return null;
	}

}
