package camsAction;

import java.util.HashMap;

import controllers.CampController;
import core.CampInfo;
import interactions.Interaction;

public class doEditSuggestion extends Interaction {

	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception {
		CampController controller;
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		try {controller = (CampController) data.get("Controller");}
		catch(ClassCastException e) {
			throw new Exception("Controller lacking required methods. Request Failed.");
		}
		int campid;
		if(!data.containsKey("CurrentCamp")) throw new Exception("Did not select camp. Request Failed.");
		try {campid = (int) data.get("CurrentCamp");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Camp ID. Request Failed.");
		}
		int suggestionid;
		if(!data.containsKey("CurrentItem")) throw new Exception("Did not select suggestion. Request Failed.");
		try {campid = (int) data.get("CurrentItem");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Suggestion ID. Request Failed.");
		}
		CampInfo campinfo;
		// TODO Auto-generated method stub
		System.out.println("This method is incomplete");
		return null;
	}

}
