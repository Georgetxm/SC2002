package camsAction;

import java.util.HashMap;

import controllers.CampController;
import interactions.Interaction;

public final class doPrintStudentList extends Interaction {

	@Override
	final public Boolean run(HashMap<String, Object> data) throws Exception {
		CampController controller;
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		try {controller = (CampController) data.get("Controller");}
		catch(ClassCastException e) {
			throw new Exception("Controller lacking required methods. Request Failed.");
		}
		int campid = GetData.CampID(data);
		System.out.println(controller.getCampStudentList(campid));
		return true;
	}

}
