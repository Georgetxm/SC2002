package camsAction;

import java.util.Scanner;

import controllers.Controller;
import controllers.ControllerItemMissingException;
import controllers.EnquiryControlInterface;
import interactions.Interaction;

/**
 * Interaction that represents the action of changing the contents of a
 * submitted enquiry
 * Effectively serves as a function pointer
 * 
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doEditEnquiry extends Interaction {
	/**
	 * Requests the controller to modify the contents of a given enquiry.
	 * Ask the controller if an enquiry may be edited before requesting the
	 * deletion.
	 * 
	 * @return true if controller accepts the request(s) and false if otherwise, or
	 *         the enquiry cannot be deleted
	 * @throws MissingRequestedDataException  if the enquiry to be deleted cannot be
	 *                                        found.
	 */@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws MissingRequestedDataException {
		if(enquiryid==null) throw new MissingRequestedDataException("Enquiry is invalid");
		try {
			if (!((EnquiryControlInterface) control).isEditable(enquiryid))
				System.out.println("Enquiry has been seen and may not be edited");
			else {
				System.out.println("Please type your edited enquiry");
				((EnquiryControlInterface) control).edit(enquiryid, s.nextLine());
				System.out.println("Enquiry saved.");
			}
		} catch (ControllerItemMissingException e) {
			System.out.println("This enquiry cannot be found");
		}
		next = new queryEnquriesMenu();
		if(this.userid!=null) next = next.withuser(userid);
		if(this.campid!=null) next = next.withcamp(campid);
		if(this.filters!=null) next = next.withfilter(filters);
		if(this.ownerid!=null) next = next.withowner(this.ownerid);
		return next.withenquiry(enquiryid);
	}

}
