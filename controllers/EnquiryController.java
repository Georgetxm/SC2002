package controllers;

import java.util.ArrayList;

public class EnquiryController implements EnquiryControlInterface {

	@Override
	public int add(String enquiry, String ownerid, int campid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(int enquiryid, String enquiry) throws ControllerItemMissingException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String get(int enquiryid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean finalise(int enquiryid) throws ControllerItemMissingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isEditable(int enquiryid) throws ControllerItemMissingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int saveReply(int enquiryid, String reply) throws ControllerItemMissingException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<String> getReplies(int enquiryid) throws ControllerItemMissingException {
		// TODO Auto-generated method stub
		return null;
	}

}
