package camsAction;

import java.util.Scanner;

import controllers.EnquiryController;
import entities.Data;
import interactions.Interaction;

public final class doEditEnquiry extends Interaction {

	@Override
	public final Boolean run() throws Exception {
		if(!Data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(!EnquiryController.class.isInstance(Data.get("Controller")))	
			throw new Exception("Controller not able enough. Request Failed.");
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
