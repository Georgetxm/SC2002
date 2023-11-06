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
		
		int campid;
		if(!data.containsKey("CurrentCamp")) throw new Exception("Did not select camp. Request Failed.");
		try {campid = (int) data.get("CurrentCamp");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Camp ID. Request Failed.");
		}
		
		int enquiryid;
		if(!data.containsKey("CurrentItem")) throw new Exception("Did not select enquiry. Request Failed.");
		try {enquiryid = (int) data.get("CurrentItem");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Enquiry ID. Request Failed.");
		}
		
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
