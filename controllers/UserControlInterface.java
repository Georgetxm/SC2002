package controllers;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import types.Faculty;
import types.Perms;

public interface UserControlInterface {
	public Faculty getUserFaculty(String userid);

	public boolean setCampCommittee(String userid, int campid);

	public Integer getCampCommitteeOfStudent(String userId);

	public int incrementPoints(String userid, int points); // returns new points

	public EnumSet<Perms> grantPerms(String userid, EnumSet<Perms> newperms); // Returns new perms

	public EnumSet<Perms> denyPerms(String userid, EnumSet<Perms> removedperms); // Returns new perms

	public EnumSet<Perms> replacePerms(String userid, EnumSet<Perms> replacementperms); // Returns new perms

	public Controller FilterCamp(int campid);
	
	public Class<?> getClass(String userid);
}
