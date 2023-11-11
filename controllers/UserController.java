package controllers;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import types.Faculty;
import types.Perms;

public interface UserController {
	//Feel free to change the return and parameter types, correcting on my end is not hard
	public Faculty getUserFaculty(String userid);
	public boolean setCampComittee(String userid, String name, int campid);
	public boolean joinCamp(String userid, String name, int campid);
	public boolean leaveCamp(String userid, int campid); 
	public boolean setCampCommittee(int campId, String userId);
	public Integer getCampCommittee(String userId);
	public HashMap<Integer,String> getCamp(int userid); //Returns id, name. 
	public HashSet<Entry<Integer,Integer>> getUserEnquiries(int userid); //Returns campid, enquiryid
	public HashSet<Entry<Integer,Integer>> getUserSuggestions(int userid); //Returns campid, suggestionid
	public boolean addSuggestion(String userid, int suggestionid);
	public boolean addEnquiry(String userid, int enquiryid);
	public boolean deleteEnquiry(String userid, int enquiryid);
	public boolean deleteSuggestion(String userid, int suggestionid);
	public int incrementPoints(String userid, int points); //returns new points
	public EnumSet<Perms> grantPerms(String userid, EnumSet<Perms> newperms); //Returns new perms
	public EnumSet<Perms> denyPerms(String userid, EnumSet<Perms> removedperms); //Returns new perms
	public EnumSet<Perms> replacePerms(String userid, EnumSet<Perms> replacementperms); //Returns new perms
}
