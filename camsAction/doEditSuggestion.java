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
		
		int campid;
		if(!data.containsKey("CurrentCamp")) throw new Exception("Did not select camp. Request Failed.");
		try {campid = (int) data.get("CurrentCamp");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Camp ID. Request Failed.");
		}
		
		int suggestionid;
		if(!data.containsKey("CurrentItem")) throw new Exception("Did not select suggestion. Request Failed.");
		try {suggestionid = (int) data.get("CurrentItem");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Suggestion ID. Request Failed.");
		}
		
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
