package camsAction;

import java.util.NoSuchElementException;

import controllers.CampController;
import entities.Data;
import interactions.Interaction;
/**
 * Interaction that represents the action of changing the visibility of a camp to a value other than its original value.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doToggleVisibility extends Interaction {

	@Override
	public final Boolean run() throws MissingRequestedDataException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(Data.get("Controller"))
		)	throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		int campid = GetData.CampID();
		
		System.out.println("Camp visibility changed. Camp is now:");
		System.out.println(((CampController) control).toggleCampVisiblity(campid)?"Visible":"Not Visible");
		return true;
	}

}
