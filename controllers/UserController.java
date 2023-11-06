package controllers;
import java.util.EnumSet;
import java.util.List;
import java.util.Map.Entry;

import types.Perms;

public interface UserController {
	public boolean setCampComittee(int userid, String name, int campid);
	public boolean addCamp(int userid, String name, int campid);
	public List<Entry<String,Integer>> getCamp(int userid);
	public int incrementPoints(int userid, int points);
	public EnumSet<Perms> grantPerms(int userid, Perms newperms);
}
