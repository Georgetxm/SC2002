package camsAction;

import java.util.HashMap;
import java.util.Scanner;

import controllers.CampController;
import interactions.Interaction;

public final class doEditEnquiry extends Interaction {

	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(!CampController.class.isInstance(data.get("Controller")))
			throw new Exception("Controller not able enough. Request Failed.");
		CampController campcontrol = (CampController) data.get("Controller");
		
		int campid = GetData.CampID(data);
		int enquiryid = GetData.EnquiryID(data);
		
		if(!campcontrol.isEnquiryEditable(campid, enquiryid)) {
			System.out.println("Enquiry has been seen and may not be edited");
			return false;
		}
	
		Scanner s = getScanner(data);
		System.out.println("Please type your edited enquiry");
		campcontrol.editEnquiry(campid, enquiryid, s.nextLine());
		System.out.println("Enquiry saved.");
		return true;
	}

}
