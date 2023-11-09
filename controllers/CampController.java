package controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import core.CampInfo;
import types.CampAspects;
import types.Role;

public interface CampController {
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

	public boolean editCampDetails(int campid, Entry<CampAspects, ? extends Object> detail);

	public boolean isAttendeeFull(int campid);

	public boolean isCommiteeFull(int campid);
}
