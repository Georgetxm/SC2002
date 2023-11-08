package controllers;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import types.Perms;

public interface UserController {
	public boolean setCampComittee(int campId, String userId);
	public boolean addCamp(int userid, String name, int campid);
	public boolean deleteCamp(int userid, int campid); 
	public HashMap<Integer,String> getCamp(int userid); //Returns id, name. 
	public HashSet<Entry<Integer,Integer>> getCampEnquiries(int userid); //Returns campid, enquiryid
	public HashSet<Entry<Integer,Integer>> getCampSuggestions(int userid); //Returns campid, suggestionid
	public int incrementPoints(int userid, int points); //returns new points
	public EnumSet<Perms> grantPerms(int userid, EnumSet<Perms> newperms); //Returns new perms
	public EnumSet<Perms> denyPerms(int userid, EnumSet<Perms> removedperms); //Returns new perms
	public EnumSet<Perms> replacePerms(int userid, EnumSet<Perms> replacementperms); //Returns new perms
}
