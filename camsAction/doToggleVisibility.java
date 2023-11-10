package camsAction;

import controllers.CampController;
import entities.Data;
import interactions.Interaction;

public final class doToggleVisibility extends Interaction {

	@Override
	public final Boolean run() throws Exception {
		if(!Data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(Data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		int campid = GetData.CampID();
		
		System.out.println("Camp visibility changed. Camp is now:");
		System.out.println(((CampController) control).toggleCampVisiblity(campid)?"Visible":"Not Visible");
		return true;
	}

}
