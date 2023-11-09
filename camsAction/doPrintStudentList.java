package camsAction;

import controllers.CampController;
import entities.Data;
import interactions.Interaction;

public final class doPrintStudentList extends Interaction {

	@Override
	final public Boolean run() throws Exception {
		CampController controller;
		if(!Data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		try {controller = (CampController) Data.get("Controller");}
		catch(ClassCastException e) {
			throw new Exception("Controller lacking required methods. Request Failed.");
		}
		int campid = GetData.CampID();
		System.out.println(controller.getCampStudentList(campid));
		return true;
	}

}
