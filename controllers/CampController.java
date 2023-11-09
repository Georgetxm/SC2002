package controllers;

import java.util.HashMap;
import java.util.Map.Entry;

import core.CampInfo;
import types.CampAspects;
import types.Role;

public interface CampController {
	public int addCamp(CampInfo info, int ownerid);
	public boolean joinCamp(int campid, int userid, Role role);
	public boolean addEnquiry(int campid, int enquiryid);
	public boolean addSuggestion(int campid, int suggestionid);
	public boolean deleteCamp(int campid);
	public boolean leaveCamp(int campid, int userid);
	public boolean deleteEnquiry(int campid, int enquiryid);
	public boolean deleteSuggestion(int campid, int suggestionid);
	CampController FilterUser(int userid);
	CampController FilterAspect(Entry<CampAspects,? extends Object> filter);
	public HashMap<Integer, String> getCamps();
	public CampInfo getCampDetails(int campid);
	public String getCampStudentList(int campid);
	public Entry<Integer,Role>[] getCampParticipantID(int campid);
	public boolean editCampDetails(int campid, Entry<CampAspects, ? extends Object> detail);
	public boolean isAttendeeFull(int campid);
	public boolean isCommiteeFull(int campid);
}
