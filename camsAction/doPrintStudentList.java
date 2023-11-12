package camsAction;

import java.util.NoSuchElementException;

import controllers.CampController;
import entities.Data;
import interactions.Interaction;
/**
 * Interaction that represents the action of generating and printing a camp report.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doPrintStudentList extends Interaction {

	@Override
	final public Boolean run() throws MissingRequestedDataException {
		CampController controller;
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		try {controller = (CampController) Data.get("Controller");}
		catch(ClassCastException e) {
			throw new NoSuchElementException("Controller lacking required methods. Request Failed.");
		}
		int campid = GetData.CampID();
		System.out.println(controller.getCampStudentList(campid));
		return true;
	}

}
