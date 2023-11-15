package camsAction;

import java.lang.ModuleLayer.Controller;
import java.util.NoSuchElementException;

import controllers.CampController;
import controllers.ControllerItemMissingException;
import controllers.EnquiryController;
import entities.Data;
import interactions.Interaction;
/**
 * Interaction that represents the action of deleting an enquiry
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doDeleteEnquiry extends Interaction {
	/**
	 * Requests the controller to delete an enquiry from the database.
	 * Asks controller if the enquiry is finalised before requesting deletion.
	 * Controller is expected to ensure all links to the enquiry are also removed, and the database remainds consistent with no loose ends.
	 *@return true if controller accepts the request(s) or false if enquiry is already finalised, or does not accept any request.
	 *@throws MissingRequestedDataException if the enquiryid cannot be found
	 */
	@Override
	public final Boolean run() throws MissingRequestedDataException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(!CampController.class.isInstance(Data.get("Controller")))
			throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = (Controller) Data.get("Controller");
		
		int campid = GetData.CampID();
		int enquiryid = GetData.EnquiryID();
		try {
			if(!((EnquiryController) control).isEnquiryEditable(enquiryid)) {
				System.out.println("This enquiry is finalised and cannot be deleted or edited!");
				return false;
			}
		} catch (ControllerItemMissingException e) {
			System.out.println("This enquiry cannot be found");
			return false;
		}
		((CampController) control).deleteEnquiry(campid, enquiryid);
		System.out.println("Enquiry deleted");
		return null;
	}

}
