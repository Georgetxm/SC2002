package camsAction;

import java.util.HashMap;

import controllers.CampController;
import controllers.UserController;
import core.CampInfo;
import interactions.Interaction;

public final class doEditSuggestion extends Interaction {
	//Currently using distinct control interfaces for dual inheritance
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
		
		if(!campcontrol.isSuggestionEditable(campid, suggestionid)) {
			System.out.println("Suggestion has been viewed and may not be edited");
			return false;
		}

		CampInfo campinfo = null;
		usercontrol.incrementPoints(0, 0);
		campcontrol.editSuggestion(campid, suggestionid, campinfo, "Rationale");
		
		// TODO Auto-generated method stub
		System.out.println("This method is incomplete");
		return null;
	}

}
