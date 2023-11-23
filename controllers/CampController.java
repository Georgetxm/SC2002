package controllers;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map.Entry;

import entities.CampInfo;
import types.CampAspect;
import types.Role;

public class CampController implements CampControlInterface {

	@Override
	public int add(CampInfo info, String ownerid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(int campid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CampInfo details(int campid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateAttendeeList(int campid, EnumSet<Role> roles) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean editDetails(int campid, Entry<CampAspect, ? extends Object> detail) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAttendeeFull(int campid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCommiteeFull(int campid) {
		// TODO Auto-generated method stub
		return false;
	}

}
