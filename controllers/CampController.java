package controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import core.CampInfo;
import types.CampAspects;
import types.Role;

public interface CampController {
<<<<<<< HEAD
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
=======
	public int addCamp(CampInfo info);

	public boolean addUser(int campId, String userId, Role role);

	public boolean addEnquiry(int campId, String creatorId, int enquiryId);

	public boolean addSuggestion(int campId, String creatorId, int suggestionId);

	public boolean deleteCamp(int campId);

	public boolean removeAttendeeFromCamp(int campId, String userId);

	public boolean deleteEnquiry(int campId, String creatorId, int enquiryid);

	public boolean deleteSuggestion(int campId, String creatorId, int suggestionid);

	public HashMap<String, Integer> getCamps(Entry<CampAspects, ? extends Object> filter);

	public CampInfo getCampDetails(int campId);

	public HashSet<String> getCampAttendees(int campId);

	public Entry<Integer, Role>[] getCampParticipantID(int campId);

	public int getSuggestionOwner(int campid, int suggestionid);

>>>>>>> branch 'main' of https://github.com/Georgetxm/SC2002.git
	public boolean editCampDetails(int campid, Entry<CampAspects, ? extends Object> detail);

	public boolean isAttendeeFull(int campid);

	public boolean isCommiteeFull(int campid);
}
