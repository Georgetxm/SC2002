package camsAction;

import controllers.CampController;
import controllers.SuggestionController;
import controllers.UserController;
import entities.Data;
import interactions.Interaction;

public final class doApproveSuggestion extends Interaction {
	//Currently using new single umbrella class implementation for dual inheritance
	@Override
	public final Boolean run() throws Exception {
		if(!Data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(Data.get("Controller"))||
			!UserController.class.isInstance(Data.get("Controller"))||
			!SuggestionController.class.isInstance(Data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		
		int campid = GetData.CampID();
		int suggestionid = GetData.SuggestionID();
		String ownerid = ((SuggestionController) control).getOwner(suggestionid);
		
		((CampController) control).editCampDetails(campid, ((SuggestionController) control).getSuggestion(suggestionid).getKey());
		((UserController) control).incrementPoints(ownerid, 1);
		
		if(!GetData.isSilenced()) System.out.println("Suggestion Approved");
		
		return null;
	}

}
