package controllers;

import java.util.EnumSet;

import types.Faculty;
import types.Perms;

public class UserController implements UserControlInterface {

	@Override
	public Faculty getUserFaculty(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setCampCommittee(String userid, int campid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer getCampCommitteeOfStudent(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int incrementPoints(String userid, int points) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EnumSet<Perms> grantPerms(String userid, EnumSet<Perms> newperms) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumSet<Perms> denyPerms(String userid, EnumSet<Perms> removedperms) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumSet<Perms> replacePerms(String userid, EnumSet<Perms> replacementperms) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Controller FilterCamp(int campid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getClass(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

}
