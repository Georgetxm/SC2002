package controllers;

import java.util.HashMap;
import java.util.Map.Entry;

import core.CampAspectValue;
import core.CampInfo;
import types.CampAspects;
import types.Role;

public interface CampController {
	public int submitCamp(CampInfo info);
	public String submitUser(int campid, int userid, Role role);
	public int submitEnquiry(int campid, String enquiry);
	public int submitSuggestion(int campid, Entry<CampAspects,? extends Object> edited, String reason, int userid);
	public int submitReply(int campid, int enquiryid, String reply);
	public boolean deleteCamp(int campid);
	public boolean deleteUser(int campid, int userid);
	public boolean deleteEnquiry(int campid, int enquiryid);
	public boolean deleteSuggestion(int campid, int suggestionid);
	public HashMap<String,Integer> getCamps(CampAspectValue filter);
	public CampInfo getCampDetails(int campid);
	public String getCampStudentList(int campid);
	public Entry<Integer,Role>[] getCampParticipantID(int campid);
	public HashMap<String,Integer> getCampEnquiries(int campid, CampAspectValue filter);
	public HashMap<String,Integer> getCampSuggestions(int campid, CampAspectValue filter);
	public CampAspects getSuggestionAspect(int campid, int suggestionid);
	public String editSuggestion(int campid, int suggestionid, Entry<CampAspects, ? extends Object> edited, String reason);
	public String editEnquiry(int campid, int enquiryid, String edited);
	public int getSuggestionOwner(int campid, int suggestionid);
	public boolean approveSuggestion(int campid, int suggestionid);
	public boolean isEnquiryEditable(int campid, int enquiryid);
	public boolean isSuggestionEditable(int campid, int suggestionid);
	public boolean isAttendeeFull(int campid);
	public boolean isCommiteeFull(int campid);
}
