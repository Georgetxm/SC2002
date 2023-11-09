package controllers;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import types.Perms;

public interface UserController {
<<<<<<< HEAD
	//Feel free to change the return and parameter types, correcting on my end is not hard
	public boolean setCampComittee(int userid, String name, int campid);
	public boolean joinCamp(int userid, String name, int campid);
	public boolean leaveCamp(int userid, int campid); 
=======
	public boolean setCampComittee(int campId, String userId);
	public boolean addCamp(int userid, String name, int campid);
	public boolean deleteCamp(int userid, int campid); 
>>>>>>> branch 'main' of https://github.com/Georgetxm/SC2002.git
	public HashMap<Integer,String> getCamp(int userid); //Returns id, name. 
	public HashSet<Entry<Integer,Integer>> getUserEnquiries(int userid); //Returns campid, enquiryid
	public HashSet<Entry<Integer,Integer>> getUserSuggestions(int userid); //Returns campid, suggestionid
	public boolean addSuggestion(int userid, int suggestionid);
	public boolean addEnquiry(int userid, int enquiryid);
	public boolean deleteEnquiry(int userid, int enquiryid);
	public boolean deleteSuggestion(int userid, int suggestionid);
	public int incrementPoints(int userid, int points); //returns new points
	public EnumSet<Perms> grantPerms(int userid, EnumSet<Perms> newperms); //Returns new perms
	public EnumSet<Perms> denyPerms(int userid, EnumSet<Perms> removedperms); //Returns new perms
	public EnumSet<Perms> replacePerms(int userid, EnumSet<Perms> replacementperms); //Returns new perms
}
