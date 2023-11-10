package controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import entities.CampInfo;
import types.CampAspects;
import types.Role;

public interface CampController extends Controller{
	public int addCamp(CampInfo info, String ownerid);
	public boolean joinCamp(int campid, String userid, Role role);
	public boolean addEnquiry(int campid, int enquiryid);
	public boolean addSuggestion(int campid, int suggestionid);
	public boolean deleteCamp(int campid);
	public boolean removeAttendeeFromCamp(int campid, String userid);
	public boolean deleteEnquiry(int campid, int enquiryid);
	public boolean deleteSuggestion(int campid, int suggestionid);
	public CampController filterVisible();
	public boolean toggleCampVisiblity(int campid);
	public HashMap<Integer, String> getCamps();
	public CampInfo getCampDetails(int campid);
	public String getCampStudentList(int campid);
	public HashSet<String> getCampAttendees(int campid);
	public HashSet<String> getCampComittees(int campid);
	public boolean editCampDetails(int campid, Entry<CampAspects, ? extends Object> detail);
	public boolean isAttendeeFull(int campid);
	public boolean isCommiteeFull(int campid);
}
