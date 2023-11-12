package camsAction;

import java.util.NoSuchElementException;

import controllers.CampController;
import entities.Data;
import interactions.Interaction;

public final class doDeleteEnquiry extends Interaction {

	@Override
	public final Boolean run() throws MissingRequestedDataException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(!CampController.class.isInstance(Data.get("Controller")))
			throw new NoSuchElementException("Controller not able enough. Request Failed.");
		CampController campcontrol = (CampController) Data.get("Controller");
		
		int campid = GetData.CampID();
		int enquiryid = GetData.EnquiryID();
		
		campcontrol.deleteEnquiry(campid, enquiryid);
		System.out.println("Enquiry deleted");
		return null;
	}

}
