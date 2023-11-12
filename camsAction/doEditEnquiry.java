package camsAction;

import java.util.NoSuchElementException;
import java.util.Scanner;

import controllers.EnquiryController;
import entities.Data;
import interactions.Interaction;
/**
 * Interaction that represents the action of changing the contents of a submitted enquiry
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doEditEnquiry extends Interaction {

	@Override
	public final Boolean run() throws MissingRequestedDataException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(!EnquiryController.class.isInstance(Data.get("Controller")))	
			throw new NoSuchElementException("Controller not able enough. Request Failed.");
		EnquiryController enquirycontrol = (EnquiryController) Data.get("Controller");
		
		int enquiryid = GetData.EnquiryID();
		
		if(!enquirycontrol.isEnquiryEditable(enquiryid)) {
			System.out.println("Enquiry has been seen and may not be edited");
			return false;
		}
	
		Scanner s = getScanner();
		System.out.println("Please type your edited enquiry");
		enquirycontrol.editEnquiry(enquiryid, s.nextLine());
		System.out.println("Enquiry saved.");
		return true;
	}

}
