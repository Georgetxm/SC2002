package controllers;

import java.util.EnumSet;
import java.util.Map.Entry;

import entities.CampInfo;
import types.CampAspect;
import types.Role;

public interface CampControlInterface {
	public int add(CampInfo info);

	public boolean delete(int campid);

	public CampInfo details(int campid);

	public String generateAttendeeList(int campid, EnumSet<Role> roles);

	public boolean editDetails(int campid, Entry<CampAspect, ? extends Object> detail);

	public boolean isAttendeeFull(int campid);

	public boolean isCommitteeFull(int campid);
}
