package camsAction;

import java.util.HashMap;

import controllers.CampController;
import controllers.UserController;
import interactions.Interaction;

public final class doApproveSuggestion extends Interaction {
	//Currently using new single umbrella class implementation for dual inheritance
	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception {
		interface maincontrol extends CampController, UserController{} //<<<<<<<<Is this shit even allowed lmao guess we will find out
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(!maincontrol.class.isInstance(data.get("Controller")))
			throw new Exception("Controller not able enough. Request Failed.");
		maincontrol control = (maincontrol) data.get("Controller");
		
		int campid = GetData.CampID(data);
		int suggestionid = GetData.SuggestionID(data);
		int ownerid = control.getSuggestionOwner(campid, suggestionid);
		
		control.approveSuggestion(campid, suggestionid);
		control.incrementPoints(ownerid, 1);
		
		return null;
	}

}
