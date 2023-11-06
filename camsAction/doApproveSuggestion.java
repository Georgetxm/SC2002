package camsAction;

import java.util.HashMap;

import controllers.CampController;
import controllers.UserController;
import interactions.Interaction;

public class doApproveSuggestion extends Interaction {
	//Currently using new single umbrella class implementation for dual inheritance
	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception {
		interface maincontrol extends CampController, UserController{} //<<<<<<<<Is this shit even allowed lmao guess we will find out
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(!maincontrol.class.isInstance(data.get("Controller")))
			throw new Exception("Controller not able enough. Request Failed.");
		maincontrol control = (maincontrol) data.get("Controller");
		
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
		int userid = control.getSuggestionOwner(campid, suggestionid);
		control.approveSuggestion(campid, suggestionid);
		control.incrementPoints(userid, 1);
		
		return null;
	}

}
