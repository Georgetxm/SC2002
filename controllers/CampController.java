package controllers;

import java.util.HashMap;

import core.CampAspectValue;
import core.CampInfo;

public interface CampController {
	public String submitUser(int campid, int userid, String role);
	public int submitEnquiry(int campid, String enquiry);
	public int submitSuggestion(int campid, CampInfo edited, String reason);
	public int submitReply(int campid, int enquiryid, String reply);
	public boolean deleteUser(int campid, int userid);
	public boolean deleteEnquiry(int campid, int enquiryid);
	public boolean deleteSuggestion(int campid, int suggestionid);
	public HashMap<String,Integer> getCamps(CampAspectValue filter);
	public CampInfo getCampDetails(int campid);
	public String getCampStudentList(int campid);
	public HashMap<String,Integer> getCampEnquiries(int campid, CampAspectValue filter);
	public HashMap<String,Integer> getCampSuggestion(int campid, CampAspectValue filter);  
	public String editSuggestion(int campid, int suggestionid, CampInfo edited, String reason);
	public String editEnquiry(int campid, int enquiryid, String edited);
	public boolean approveSuggestion(int campid, int suggestionid);
}
