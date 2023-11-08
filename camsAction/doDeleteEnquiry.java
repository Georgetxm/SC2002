package camsAction;

import java.util.HashMap;

import controllers.CampController;
import interactions.Interaction;

public final class doDeleteEnquiry extends Interaction {

	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(!CampController.class.isInstance(data.get("Controller")))
			throw new Exception("Controller not able enough. Request Failed.");
		CampController campcontrol = (CampController) data.get("Controller");
		
		int campid = GetData.CampID(data);
		int enquiryid = GetData.EnquiryID(data);
		
		campcontrol.deleteEnquiry(campid, enquiryid);
		System.out.println("Enquiry deleted");
		return null;
	}

}
