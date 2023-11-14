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
	/**
	 * Requests the controller to delete a given suggestion.
	 * Ask the controller if a suggestion may be edited before requesting the deletion.
	 *@return true if controller accepts the request(s) and false if otherwise, or the suggestion cannot be deleted
	 *@throws MissingRequestedDataException if suggestion to be deleted cannot be found.
	 */
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
