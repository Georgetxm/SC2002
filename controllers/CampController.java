package controllers;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map.Entry;

import cams.ReadWriteCampCSV;
import entities.Camp;
import entities.CampInfo;
import types.CampAspect;
import types.Role;

public class CampController implements CampControlInterface {

	private HashMap<Integer, Camp> camps;

	/**
	 * Constructor for CampController
	 * 
	 * @param userList
	 * @param campList
	 */
	public CampController() {
		HashMap<Integer, Camp> campList = new HashMap<Integer, Camp>();
		ReadWriteCampCSV.readCampCSV(campList, "lists/camp_list.csv");
		this.camps = campList;
	}

	/**
	 * Returns a Camp object given its ID
	 * 
	 * @param campId the Camp's ID
	 * 
	 * @return the Camp object with the given ID, null if not found
	 */

	public Camp findCampById(int campId) {
		if (camps.containsKey(campId)) {
			return camps.get(campId);
		}
		return null;
	}

	/**
	 * Create a new Camp Object based on the given CampInfo object
	 * Adds the Camp object to the camps ArrayList
	 * 
	 * @param info the CampInfo object containing the Camp's information, @See
	 *             CampInfo record class for more details
	 * 
	 * @return the Camp's ID
	 */
	@Override
	public int add(CampInfo info) {
		Camp camp = new Camp(info, false, LocalDate.now());

		camps.put((Integer) camp.getCampid(), camp);
		ReadWriteCampCSV.writeCampCSV(camps, "lists/camp_list.csv");

		return camp.getCampid();
	}

	/**
	 * Overriden method from CampController
	 * Deletes a Camp object from the camps ArrayList
	 * Resets a Student's campCommittee int attribute to -1 if student is in the
	 * committee of th deleted Camp
	 * Removes a Camp from a User object's camps HashSet
	 * Remove enquiries and suggestions of the Camp from the enquiries and
	 * suggestions ArrayList
	 * 
	 * @param campid the Camp's ID
	 * 
	 * @return true if the Camp is successfully deleted, false otherwise
	 */
	@Override
	public boolean delete(int campid) {
		Camp camp = findCampById(campid);
		if (!(camp == null)) {
			camps.remove(camp.getCampid(), camp);
			ReadWriteCampCSV.writeCampCSV(camps, "lists/camp_list.csv");
			return true;
		}
		return false;
	}

	/**
	 * Returns a CampInfo record object which contains information of the given its
	 * ID
	 * Check CampAspects.java for the list of attributes in CampInfo
	 * 
	 * @param campId the Camp's ID
	 * 
	 * @return the CampInfo object with the given ID, null if not found
	 */
	@Override
	public CampInfo details(int campId) {
		Camp camp = findCampById(campId);
		if (!(camp == null)) {
			return camp.getCampInfo();
		}
		return null;
	}

	@Override
	public String generateAttendeeList(int campid, EnumSet<Role> roles) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Overriden method from CampController
	 * Edit a single attribute of a Camp object
	 * 
	 * @param campid the Camp's ID
	 * @param detail the attribute to be edited
	 * @return true if the attribute is successfully edited, false otherwise
	 */

	@Override
	public boolean editDetails(int campid, Entry<CampAspect, ? extends Object> detail) {
		Camp camp = findCampById(campid);
		if (!(camp == null)) {
			camp.getCampInfo().info().replace(detail.getKey(), detail.getValue());
			ReadWriteCampCSV.writeCampCSV(camps, "lists/camp_list.csv");
			return true;
		}
		return false;
	}

	/**
	 * Overriden method from CampController
	 * Checks if the Camp's attendee is full
	 * 
	 * @param campid the Camp's ID
	 * @return true if the Camp's attendee is full, false otherwise
	 */
	@Override
	public boolean isAttendeeFull(int campid) {
		Camp camp = findCampById(campid);
		if (!(camp == null)) {
			return (camp.getAttendeeCount() >= camp.getCampInfo().info().get(CampAspect.SLOTS).hashCode());
		}
		return false;
	}

	/**
	 * Overriden method from CampController
	 * Checks if the Camp's committee is full
	 * 
	 * @param campid the Camp's ID
	 * @return true if the Camp's committee is full, false otherwise
	 */
	@Override
	public boolean isCommitteeFull(int campid) {
		Camp camp = findCampById(campid);
		if (!(camp == null)) {
			return (camp.getCommitteeCount() >= camp.getCampInfo().info().get(CampAspect.COMMITTEESLOTS).hashCode());
		}
		return false;
	}

}
