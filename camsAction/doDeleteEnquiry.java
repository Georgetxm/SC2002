package camsAction;


import java.util.Scanner;

import controllers.Controller;
import controllers.ControllerItemMissingException;
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
	 *@return query enquiry menu with user id, filters, campid, and ownerid preserved
	 *@throws MissingRequestedDataException if the enquiryid cannot be found
	 */@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws MissingRequestedDataException {
		if(enquiryid==null) throw new MissingRequestedDataException("Invalid enquiry");
		Boolean iseditable=false;
		try {
			iseditable = control.Enquiry().isEditable(enquiryid);
		} catch (ControllerItemMissingException e) {
			throw new MissingRequestedDataException("This enquiry cannot be found");
		}
		if(!iseditable) System.out.println("This enquiry is finalised and cannot be deleted or edited!");
		else {
			try {
				control.Enquiry().delete(enquiryid);
			} catch (ControllerItemMissingException e) {
				System.out.println("Enquiry is invalid");
			}
			control.Directory().remove(entities.Enquiry.class, enquiryid);
			System.out.println("Enquiry deleted");
		}
		next = new queryEnquiriesMenu();
		if(this.userid!=null) next = next.withuser(userid);
		if(this.campid!=null) next = next.withcamp(campid);
		if(this.filters!=null) next = next.withfilter(filters);
		if(this.ownerid!=null) next = next.withowner(this.ownerid);
		return next.withenquiry(enquiryid);
	}

}
