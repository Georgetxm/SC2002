package camsAction;

import java.util.HashMap;
import java.util.Scanner;

import controllers.CampController;
import core.Utility;
import interactions.Interaction;

public final class doSubmitEnquiry extends Interaction {
	@Override
	public final boolean run(HashMap<String, Object> data) throws Exception {
		CampController controller;
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		try {controller = (CampController) data.get("Controller");}
		catch(ClassCastException e) {
			throw new Exception("Controller lacking required methods. Request Failed.");
		}
		int campid;
		if(!data.containsKey("CurrentCamp")) throw new Exception("Did not select camp. Request Failed.");
		try {campid = (int) data.get("CurrentCamp");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Camp ID. Request Failed.");
		}
		Scanner s = Utility.getScanner(data);
		
		String enquiry;
		System.out.println("Please type your enquiry:");
		enquiry = s.nextLine();
		controller.submitEnquiry(campid, enquiry);
		System.out.println("Your enquiry has been submitted.");
		
		return true;
	}

}
