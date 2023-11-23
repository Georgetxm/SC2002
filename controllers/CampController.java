package controllers;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import entities.Camp;
import entities.CampInfo;
import entities.Enquiry;
import entities.Staff;
import entities.Student;
import entities.Suggestion;
import entities.User;
import types.CampAspect;
import types.Role;

public class CampController implements CampControlInterface {

	private HashMap<String, User> users;
	private HashMap<Integer, Camp> camps;
	private HashMap<Integer, Suggestion> suggestions;
	private HashMap<Integer, Enquiry> enquiries;

	/**
	 * Constructor for CampController
	 * 
	 * @param userList
	 * @param campList
	 */
	public CampController(HashMap<String, User> userList,
			HashMap<Integer, Camp> campList,
			HashMap<Integer, Suggestion> suggestions,
			HashMap<Integer, Enquiry> enquiries) {
		this.users = userList;
		this.camps = campList;
		this.suggestions = suggestions;
		this.enquiries = enquiries;
	}

	/**
	 * Returns a User object given its ID
	 * 
	 * @param userId the User's ID
	 * 
	 * @return the User object with the given ID, null if not found
	 */

	public User findUserById(String userId) {
		if (users.containsKey(userId) && Student.class.isInstance(users.get(userId))) {
			return (Student) users.get(userId);
		}
		if (users.containsKey(userId) && Staff.class.isInstance(users.get(userId))) {
			return (Staff) users.get(userId);
		}
		return null;
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
	 * Returns a Suggestion object given its ID
	 * 
	 * @param suggestionId
	 * @return the Suggestion object with the given ID, null if not found
	 */

	public Suggestion findSuggestionById(int suggestionId) {
		if (suggestions.containsKey(suggestionId)) {
			return suggestions.get(suggestionId);
		}
		return null;
	}

	/**
	 * Returns an Enquiry object given its ID
	 * 
	 * @param enquiryId
	 * @return the Enquiry object with the given ID, null if not found
	 */
	public Enquiry findEnquiryById(int enquiryId) {
		if (enquiries.containsKey(enquiryId)) {
			return enquiries.get(enquiryId);
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
	public int add(CampInfo info, String staffid) {
		Camp camp = new Camp(info, new HashSet<String>(), new HashSet<String>(), false, LocalDate.now());

		camps.put((Integer) camp.getCampid(), camp);
		// TODO: ADD EXCEPTION
		if (!Staff.class.isInstance(findUserById(staffid))) {
			return -1;
		}
		Staff user = (Staff) findUserById(staffid);
		user.registerForCamp(camp.getCampid());

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
			for (String student : camp.getAttendees()) {
				User user = findUserById(student);
				user.withdrawFromCamp(campid);
			}
			for (String committee : camp.getCampCommittee()) {
				if (findUserById(committee) instanceof Student) {
					Student user = (Student) findUserById(committee);
					user.setCampComittee(-1);
				}
			}
			for (Integer enquiry : camp.getEnquiries()) {
				Enquiry e = findEnquiryById(enquiry);
				if (!(e == null)) {
					enquiries.remove(e.getEnquiryId(), e);
				}
			}
			for (Integer suggestion : camp.getSuggestions()) {
				Suggestion s = findSuggestionById(suggestion);
				if (!(s == null)) {
					suggestions.remove(s.getSuggestionId(), s);
				}
			}
			camps.remove(camp.getCampid(), camp);
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
			return camp.isCampAttendeeFull();
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
	public boolean isCommiteeFull(int campid) {
		Camp camp = findCampById(campid);
		if (!(camp == null)) {
			return camp.isCampCommitteeFull();
		}
		return false;
	}

}
