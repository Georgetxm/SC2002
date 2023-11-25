package controllers;

import java.util.EnumSet;

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

	public Class<?> getClass(String userid);

	public Boolean changePassword(String password, String id);
}
