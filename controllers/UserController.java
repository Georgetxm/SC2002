package controllers;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import types.Faculty;
import types.Perms;

public interface UserController {
	public Faculty getUserFaculty(String userid);

	public boolean joinCamp(String userid, int campid);

	public boolean leaveCamp(String userid, int campid);

	public boolean setCampCommittee(String userid, int campid);

	public Integer getCampCommitteeOfStudent(String userId);

	public HashMap<Integer, String> getCamp(String userid); // Returns id, name.

	public HashSet<Entry<Integer, Integer>> getUserEnquiries(String userid); // Returns campid, enquiryid

	public HashSet<Entry<Integer, Integer>> getUserSuggestions(String userid); // Returns campid, suggestionid

	public boolean addSuggestion(String userid, int campid, int suggestionid);

	public boolean addEnquiry(String userid, int campid, int enquiryid);

	public boolean deleteEnquiry(String userid, int campid, int enquiryid);

	public boolean deleteSuggestion(String userid, int campid, int suggestionid);

	public int incrementPoints(String userid, int points); // returns new points

	public EnumSet<Perms> grantPerms(String userid, EnumSet<Perms> newperms); // Returns new perms

	public EnumSet<Perms> denyPerms(String userid, EnumSet<Perms> removedperms); // Returns new perms

	public EnumSet<Perms> replacePerms(String userid, EnumSet<Perms> replacementperms); // Returns new perms
}
