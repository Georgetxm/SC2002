package controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import entities.Camp;
import entities.CampInfo;
import entities.Suggestion;
import entities.Enquiry;
import entities.Student;
import entities.User;
import types.CampAspects;
import types.Faculty;
import types.Perms;
import types.Role;

public class MainController implements CampController, UserController, SuggestionController, EnquiryController {
    private ArrayList<User> users;
    private ArrayList<Camp> camps;
    private ArrayList<Suggestion> suggestions;
    private ArrayList<Enquiry> enquiries;

    public MainController(ArrayList<User> users, ArrayList<Camp> camps) {
        this.users = users;
        this.camps = camps;
        this.suggestions = new ArrayList<Suggestion>();
        this.enquiries = new ArrayList<Enquiry>();
    }

    /**
     * Returns a Camp object given its ID
     * 
     * @param campId the Camp's ID
     * 
     * @return the Camp object with the given ID, null if not found
     */

    public Camp findCampById(int campId) {
        for (Camp camp : camps) {
            if (camp.getCampid() == campId) {
                return camp;
            }
        }
        return null;
    }

    /**
     * Returns a User object given its ID
     * 
     * @param userId the User's ID
     * 
     * @return the User object with the given ID, null if not found
     */

    public User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    /**
     * Returns a Suggestion object given its ID
     * 
     * @param campId
     * @param suggestionId
     * @return the Suggestion object with the given ID, null if not found
     */

    public Suggestion findSuggestionById(int campId, int suggestionId) {
        Camp camp = findCampById(campId);
        for (Integer suggestion : camp.getSuggestions()) {
            if (suggestion == suggestionId) {
                for (Suggestion s : suggestions) {
                    if (s.getSuggestionId() == suggestionId) {
                        return s;
                    }
                }
            }

        }
        return null;
    }

    /**
     * Returns an Enquiry object given its ID
     * 
     * @param campId
     * @param enquiryId
     * @return the Enquiry object with the given ID, null if not found
     */
    public Enquiry findEnquiryById(int campId, int enquiryId) {
        Camp camp = findCampById(campId);
        for (Integer enquiry : camp.getEnquiries()) {
            if (enquiry == enquiryId) {
                for (Enquiry e : enquiries) {
                    if (e.getEnquiryId() == enquiryId) {
                        return e;
                    }
                }
            }

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
    public int addCamp(CampInfo info, String ownerid) {
        Camp camp = new Camp(info, new HashSet<String>(), new HashSet<String>(), false, LocalDate.now());
        camps.add(camp);
        return camp.getCampid();
    }

    /**
     * Tags a User to a Camp object's attendees or committee HashMap attribute
     * Tags a Camp to a Student object's camps HashSet or campCommittee int
     * attribute
     * 
     * @param campId the Camp this User belongs to
     * 
     * @param userId the User's ID
     * 
     * @param role   the User's role
     * 
     * @return true if the User is successfully added to the Camp, false otherwise
     */
    @Override
    public boolean joinCamp(int campId, String userId, Role role) {
        Camp camp = findCampById(campId);
        Student user = (Student) findUserById(userId);
        if (camp != null) {
            if (role == Role.ATTENDEE &&
                    camp.addAttendee(userId) &&
                    user.registerAsAttendee(campId)) {
                return true;
            }
            if (role == Role.COMMITTEE
                    && camp.addCommittee(userId)
                    && user.setCampComittee(campId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Overriden method from CampController
     * Tags an enquiry to a Camp object's enquiries HashMap
     * 
     * @param campId    the Camp this enquiry belongs to
     * 
     * @param enquiryId the enquiry's ID
     * 
     * @return true if the enquiry is successfully added to the Camp, false
     */
    @Override
    public boolean addEnquiry(int campId, int enquiryId) {
        Camp camp = findCampById(campId);
        if (camp == null) {
            return false;
        } else {
            if (!camp.addEnquiry(enquiryId)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Overriden method from CampController
     * Tags a suggestion to a Camp object's suggestions HashMap
     * 
     * @param campId       the Camp this enquiry belongs to
     * 
     * @param suggestionId the suggestion's ID
     * 
     * @return true if the suggestion is successfully added, false otherwise
     */
    @Override
    public boolean addSuggestion(int campId, int suggestionId) {
        Camp camp = findCampById(campId);
        if (camp == null) {
            return false;
        } else {
            if (!camp.addSuggestion(suggestionId)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Overriden method from CampController
     * Deletes a Camp object from the camps ArrayList
     * 
     * @param campid the Camp's ID
     * 
     * @return true if the Camp is successfully deleted, false otherwise
     */
    @Override
    public boolean deleteCamp(int campid) {
        Camp camp = findCampById(campid);
        if (camp != null) {
            camps.remove(camp);
            return true;
        }
        return false;
    }

    /**
     * Overriden method from CampController
     * Removes a User from a Camp object's attendees HashMap
     * Removes a Camp from a User object's camps HashSet
     * 
     * @param campid the Camp's ID
     * 
     * @param userid the User's ID
     * 
     * @return true if the User is successfully deleted, false otherwise
     */
    @Override
    public boolean removeAttendeeFromCamp(int campId, String userId) {
        Camp camp = findCampById(campId);
        Student user = (Student) findUserById(userId);
        if (camp != null && user != null) {
            if (camp.removeAttendee(userId) && user.withdrawFromCamp(campId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Overriden method from CampController
     * Removes an enquiry from a Camp object's enquiries HashMap
     * Removes an enquiry from a User object's enquiries ArrayList
     * 
     * @param campId    the Camp's ID
     * 
     * @param creatorId the User's ID
     * 
     * @param enquiryId the enquiry's ID
     * 
     * @return true if the enquiry is successfully deleted, false otherwise
     */
    @Override
    public boolean deleteEnquiry(int campId, int enquiryId) {
        Camp camp = findCampById(campId);
        if (camp != null && camp.removeEnquiry(enquiryId)) {
            return true;
        }
        return false;
    }

    /**
     * Overriden method from CampController
     * Removes an enquiry from a Camp object's enquiries HashSet
     * 
     * @param campId     the Camp's ID
     * 
     * @param suggestion the suggestion's ID
     * 
     * @return true if the suggestion is successfully deleted, false otherwise
     */
    @Override
    public boolean deleteSuggestion(int campId, int suggestionId) {
        Camp camp = findCampById(campId);
        if (camp != null && camp.removeSuggestion(suggestionId)) {
            return true;
        }
        return false;
    }

    public CampController filterVisible() {
        ArrayList<Camp> visibleCamps = new ArrayList<Camp>();
        for (Camp camp : camps) {
            if (camp.getVisibility()) {
                visibleCamps.add(camp);
            }
        }
        return new MainController(users, visibleCamps);
    }

    /**
     * Overriden method from CampController
     * Toggles the visibility of a Camp object
     * 
     * @param campid the Camp's ID
     * @return true if the Camp's visibility is successfully toggled, false
     *         otherwise
     */
    @Override
    public boolean toggleCampVisiblity(int campid) {
        Camp camp = findCampById(campid);
        if (camp != null) {
            camp.setVisibility(!camp.getVisibility());
            return true;
        }
        return false;
    }

    /**
     * Overriden method from CampController
     * Returns a HashMap of Camo Id and Camp Name
     * 
     * @return HashMap of Camp Id and Camp Name
     */
    @Override
    public HashMap<Integer, String> getCamps() {
        HashMap<Integer, String> campList = new HashMap<Integer, String>();
        for (Camp camp : camps) {
            campList.put(camp.getCampid(), camp.getCampInfo().info().get(CampAspects.NAME).toString());
        }
        return null;
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
    public CampInfo getCampDetails(int campId) {
        Camp camp = findCampById(campId);
        if (camp != null) {
            return camp.getCampInfo();
        }
        return null;
    }

    /**
     * Overriden method from CampController
     * Returns a HashMap of Student userIds and their roles in the Camp
     * 
     * @param campid the Camp's ID
     * @return HashMap of Student userIds and their roles in the Camp
     */

    @Override
    public HashMap<String, Role> getCampStudentList(int campid) {
        Camp camp = findCampById(campid);
        if (camp != null) {
            HashMap<String, Role> studentList = new HashMap<String, Role>();
            for (String student : camp.getAttendees()) {
                studentList.put(student, Role.ATTENDEE);
            }
            for (String committee : camp.getCampCommittee()) {
                studentList.put(committee, Role.COMMITTEE);
            }
            return studentList;
        }
        return null;
    }

    /**
     * Overriden method from CampController
     * Returns a HashSet of Student userIds who are attending the Camp
     * 
     * @param campId the Camp's ID
     * @return the HashSet of Student userIds who are attending the Camp
     */
    @Override
    public HashSet<String> getCampAttendees(int campId) {
        Camp camp = findCampById(campId);
        if (camp != null) {
            return camp.getAttendees();
        }
        return null;
    }

    /**
     * Overriden method from CampController
     * Returns a HashSet of Student userIds who are in the Camp's committee
     * 
     * @param campId the Camp's ID
     * @return the HashSet of Student userIds who are in the Camp's committee
     */

    @Override
    public HashSet<String> getCampComittees(int campid) {
        Camp camp = findCampById(campid);
        if (camp != null) {
            return camp.getCampCommittee();
        }
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
    public boolean editCampDetails(int campid, Entry<CampAspects, ? extends Object> detail) {
        Camp camp = findCampById(campid);
        if (camp != null) {
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
        if (camp != null) {
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
        if (camp != null) {
            return camp.isCampCommitteeFull();
        }
        return false;
    }

    /**
     * Overriden method from UserController
     * Returns the User's Faculty
     * 
     * @param userid the User's ID
     * @return the User's Faculty
     */

    @Override
    public Faculty getUserFaculty(String userid) {
        User user = findUserById(userid);
        if (user != null) {
            return user.getFaculty();
        }
        return null;
    }

    /**
     * Overriden method from UserController
     * Tags a User to a Camp object's committee HashMap attribute
     * Tags a Camp to a Student object's campCommittee int attribute
     * 
     * @param campId the Camp this User belongs to
     * 
     * @param userId the User's ID
     * 
     * @return true if the User is successfully added to the Camp, false otherwise
     */

    @Override
    public boolean setCampCommittee(int campId, String userId) {
        Camp camp = findCampById(campId);
        Student user = (Student) findUserById(userId);
        if (camp != null && camp.addCommittee(userId) && user.setCampComittee(campId)) {
            return true;
        }
        return false;
    }

    /**
     * Overriden method from UserController
     * Adss a userId to a Camp object's attendees HashSet attribute
     * 
     * @param userId the User's ID
     * @param campId the Camp's ID
     * @return true if the User is successfully added to the Camp, false otherwise
     */

    @Override
    public boolean joinCamp(String userId, int campId) {
        Camp camp = findCampById(campId);
        if (camp.addAttendee(userId)) {
            return true;
        }
        return false;
    }

    /**
     * Overriden method from UserController
     * Removes a userId from a Camp object's attendees HashSet attribute
     * 
     * @param userId the User's ID
     * @param campId the Camp's ID
     * @return true if the User is successfully removed from the Camp, false
     *         otherwise
     */
    public boolean leaveCamp(String userId, int campId) {
        Camp camp = findCampById(campId);
        if (camp.removeAttendee(userId)) {
            return true;
        }
        return false;
    }

    /**
     * Overriden method from UserController
     * Gets the Camp committee a student is in
     * 
     * @param userId the User's ID
     * @return the Camp committee a student is in
     */
    @Override
    public Integer getCampCommitteeOfStudent(String userId) {
        Student user = (Student) findUserById(userId);
        if (user != null) {
            return user.getCampCommittee();
        }
        return null;
    }

    /**
     * Overriden method from UserController
     * Tags an enquiry to a Student object's enquiries HashMap
     * 
     * @param userId    the Student this enquiry belongs to
     * 
     * @param enquiryId the enquiry's ID
     * 
     * @return true if the enquiry is successfully added to the Student, false
     */
    @Override
    public boolean addEnquiry(String userId, int enquiryId) {
        Student user = (Student) findUserById(userId);
        if (user == null) {
            return false;
        } else {
            if (!user.addEnquiry(enquiryId)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Overriden method from UserController
     * Tags a suggestion to a Camp object's suggestions HashMap
     * Tags a suggestion to a User object's suggestions HashMap
     * 
     * @param campId       the Camp this enquiry belongs to
     * 
     * @param creatorId    the User who created this enquiry
     * 
     * @param suggestionId the suggestion's ID
     * 
     * @return true if the suggestion is successfully added, false otherwise
     */
    @Override
    public boolean addSuggestion(String creatorId, int suggestionId) {
        Student user = (Student) findUserById(creatorId);
        if (user == null) {
            return false;
        } else {
            if (!user.addSuggestion(suggestionId)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Overriden method from UserController
     * Removes an enquiry from a User object's enquiries HashSet
     * 
     * @param creatorId the User's ID
     * 
     * @param enquiryId the enquiry's ID
     * 
     * @return true if the enquiry is successfully deleted, false otherwise
     */
    @Override
    public boolean deleteEnquiry(String creatorId, int enquiryId) {
        Student user = (Student) findUserById(creatorId);
        if (user != null && user.removeEnquiry(enquiryId)) {
            return true;
        }
        return false;
    }

    /**
     * Overriden method from CampController
     * Removes an enquiry from a User object's enquiries HashSet
     * 
     * @param creatorId  the User's ID
     * 
     * @param suggestion the suggestion's ID
     * 
     * @return true if the suggestion is successfully deleted, false otherwise
     */
    @Override
    public boolean deleteSuggestion(String creatorId, int suggestionId) {
        Student user = (Student) findUserById(creatorId);
        if (user != null && user.removeSuggestion(suggestionId)) {
            return true;
        }
        return false;
    }

}
