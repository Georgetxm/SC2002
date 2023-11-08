package controllers;

import java.util.HashMap;
import java.util.Map.Entry;

import core.CampInfo;
import types.CampAspects;
import types.Role;

public interface CampController {
	public int addCamp(CampInfo info);
	public String addUser(int campid, int userid, Role role);
	public int addEnquiry(int campid, String enquiry);
	public int addSuggestion(int campid, int suggestionid);
	public boolean deleteCamp(int campid);
	public boolean deleteUser(int campid, int userid);
	public boolean deleteEnquiry(int campid, int enquiryid);
	public boolean deleteSuggestion(int campid, int suggestionid);
	public HashMap<String,Integer> getCamps(Entry<CampAspects,? extends Object> filter);
	public CampInfo getCampDetails(int campid);
	public String getCampStudentList(int campid);
	public Entry<Integer,Role>[] getCampParticipantID(int campid);
	public int getSuggestionOwner(int campid, int suggestionid);
	public boolean editCampDetails(int campid, Entry<CampAspects, ? extends Object> detail);
	public boolean isAttendeeFull(int campid);
	public boolean isCommiteeFull(int campid);
}
