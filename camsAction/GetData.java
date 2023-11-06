package camsAction;

import java.util.HashMap;

final class GetData {
	final int CurrentUser(HashMap<String,Object> data) throws Exception {
		int userid;
		if(!data.containsKey("CurrentUser")) throw new Exception("User not identified. Request Failed.");
		try {userid = (int) data.get("CurrentUser");}
		catch(ClassCastException e) {
			throw new Exception("Invalid User ID. Request Failed.");
		}
		return userid;
	}
	final int CampID(HashMap<String,Object> data) throws Exception {
		int campid;
		if(!data.containsKey("CurrentCamp")) throw new Exception("Did not select camp. Request Failed.");
		try {campid = (int) data.get("CurrentCamp");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Camp ID. Request Failed.");
		}
		return campid;
	}
	
	final int SuggestionID(HashMap<String,Object> data) throws Exception {
		int suggestionid;
		if(!data.containsKey("CurrentItem")) throw new Exception("Did not select suggestion. Request Failed.");
		try {suggestionid = (int) data.get("CurrentItem");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Suggestion ID. Request Failed.");
		}
		return suggestionid;
	}
	final int EnquiryID(HashMap<String,Object> data) throws Exception {
		int enquiryid;
		if(!data.containsKey("CurrentItem")) throw new Exception("Did not select enquiry. Request Failed.");
		try {enquiryid = (int) data.get("CurrentItem");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Enquiry ID. Request Failed.");
		}
		return enquiryid;
	}
}
