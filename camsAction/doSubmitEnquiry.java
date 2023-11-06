package camsAction;

import java.util.HashMap;
import java.util.Scanner;

import controllers.CampController;
import interactions.Interaction;

public final class doSubmitEnquiry extends Interaction {
	@Override
	public final Integer run(HashMap<String, Object> data) throws Exception {
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
		Scanner s = getScanner(data);
		
		String enquiry;
		System.out.println("Please type your enquiry:");
		enquiry = s.nextLine();
		int enquiryid = campcontrol.submitEnquiry(campid, enquiry);
		System.out.println("Your enquiry has been submitted.");
		
		return enquiryid;
	}

}
