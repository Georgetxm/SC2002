package camsAction;

import java.util.HashMap;

import controllers.CampController;
import interactions.Interaction;

public class doPrintStudentList extends Interaction {

	@Override
	final public Boolean run(HashMap<String, Object> data) throws Exception {
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
		System.out.println(controller.getCampStudentList(campid));
		// TODO Auto-generated method stub
		return true;
	}

}
