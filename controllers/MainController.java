package controllers;

import java.security.InvalidParameterException;
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
import entities.Staff;
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
    private HashSet<Integer> visibleCamps;
    private String userFilter;
    private Integer campFilter;
    private boolean visibleFilter;
    private HashMap<CampAspects, Object> aspectFilter;

    public MainController(ArrayList<User> users, ArrayList<Camp> camps) {
        this.users = users;
        this.camps = camps;
        this.suggestions = new ArrayList<Suggestion>();
        this.enquiries = new ArrayList<Enquiry>();
        this.visibleCamps = new HashSet<Integer>();
        this.userFilter = null;
        this.campFilter = null;
        this.visibleFilter = false;
        this.aspectFilter = new HashMap<CampAspects, Object>();
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
                if (user instanceof Student) {
                    return (Student) user;
                }
                if (user instanceof Staff) {
                    return (Staff) user;
                }
                ;
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
        User user = (User) findUserById(userId);

        if (camp != null) {
            if (role == Role.ATTENDEE &&
                    camp.addAttendee(userId) &&
                    user.registerForCamp(campId)) {
                return true;
            }
            if (role == Role.COMMITTEE
                    && Student.class.isInstance(user)
                    && camp.addCommittee(userId)
                    && ((Student) user).setCampComittee(campId)) {
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
    public boolean deleteCamp(int campid) {
        Camp camp = findCampById(campid);
        if (camp != null) {
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
                Enquiry e = findEnquiryById(campid, enquiry);
                if (e != null) {
                    enquiries.remove(e);
                }
            }
            for (Integer suggestion : camp.getSuggestions()) {
                Suggestion s = findSuggestionById(campid, suggestion);
                if (s != null) {
                    suggestions.remove(s);
                }
            }
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
        User user = findUserById(userId);
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
     * Adds the Camp's ID to the visibleCamps HashSet attribute if the Camp is
     * Removes the Camp's ID from the visibleCamps HashSet attribute if the Camp is
     * not
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

            if (camp.getVisibility()) {
                visibleCamps.add(campid);
            } else {
                visibleCamps.remove(campid);
            }
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
        if (visibleFilter) {
            for (Integer camp : visibleCamps) {
                Camp c = findCampById(camp);
                if (c != null) {
                    campList.put(c.getCampid(), c.getCampInfo().info().get(CampAspects.NAME).toString());
                }
            }
        }

        if (userFilter != null) {
            User user = (User) findUserById(userFilter);
            for (Integer camp : user.getCamps()) {
                Camp c = findCampById(camp);
                if (c != null) {
                    campList.put(c.getCampid(), c.getCampInfo().info().get(CampAspects.NAME).toString());
                }
            }
        }

        if (aspectFilter != null) {
            for (Camp camp : camps) {
                boolean match = true;
                for (Entry<CampAspects, ? extends Object> aspect : aspectFilter.entrySet()) {
                    if (camp.getCampInfo().info().get(aspect.getKey()) != aspect.getValue()) {
                        match = false;
                    }
                }
                if (match) {
                    campList.put(camp.getCampid(), camp.getCampInfo().info().get(CampAspects.NAME).toString());
                }
            }
        }

        userFilter = null;
        campFilter = null;
        aspectFilter = null;
        visibleFilter = false;

        if (campList.size() > 0) {
            return campList;
        } else {
            return null;
        }

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
    public boolean setCampCommittee(String userId, int campId) {
        if (!Student.class.isInstance(findUserById(userId))) {
            return false;
        }
        Camp camp = findCampById(campId);
        Student user = (Student) findUserById(userId);
        if (camp != null && camp.addCommittee(userId) && user.setCampComittee(campId)) {
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
     * Returns a HashMap of Camp Id and Camp Name a Student is in
     * including the Camp committee the Student is in
     * 
     * @param userId the User's ID
     * @return HashMap of Camp Id and Camp Name a Student is in
     */
    @Override
    public HashMap<Integer, String> getCamp(String userId) {
        User user = (User) findUserById(userId);
        if (user == null) {
            return null;
        }

        HashMap<Integer, String> campList = new HashMap<Integer, String>();

        if (campFilter == null) {
            for (Integer camp : user.getCamps()) {
                campList.put(camp, findCampById(camp).getCampInfo().info().get(CampAspects.NAME).toString());
            }
            // if (user.getCampCommittee() == campFilter) {
            // campList.put(user.getCampCommittee(),
            // findCampById(user.getCampCommittee()).getCampInfo().info().get(CampAspects.NAME).toString());
            // }
        } else {
            if (user.getCamps().contains(campFilter)) {
                campList.put(campFilter,
                        findCampById(campFilter).getCampInfo().info().get(CampAspects.NAME).toString());
            }
            // if (user.getCampCommittee() == campFilter) {
            // campList.put(campFilter,
            // findCampById(campFilter).getCampInfo().info().get(CampAspects.NAME).toString());
            // }
        }

        campFilter = null;
        return campList;
    }

    /**
     * Overriden method from UserController
     * Returns a HashSet of enquiries a Student has made
     * 
     * @param userId the User's ID
     * @return HashSet of enquiries a Student has made
     */
    @Override
    public HashSet<Entry<Integer, Integer>> getUserEnquiries(String userid) {
        Student user = (Student) findUserById(userid);
        if (user == null) {
            return null;
        }

        HashSet<Entry<Integer, Integer>> userEnquiries = new HashSet<Entry<Integer, Integer>>();
        if (campFilter != null) {
            for (Entry<Integer, Integer> enquiry : user.getEnquiries().entrySet()) {
                if (enquiry.getKey() == campFilter) {
                    userEnquiries.add(enquiry);
                }
            }
            campFilter = null;
        } else {

            for (Entry<Integer, Integer> enquiry : user.getEnquiries().entrySet()) {
                userEnquiries.add(enquiry);
            }
        }

        return userEnquiries;

    }

    /**
     * Overriden method from UserController
     * Returns a HashSet of suggestions a Student has made
     * 
     * @param userId the User's ID
     * 
     * @return HashSet of suggestions a Student has made
     */
    @Override
    public HashSet<Entry<Integer, Integer>> getUserSuggestions(String userid) {
        Student user = (Student) findUserById(userid);
        if (user == null) {
            return null;
        }

        HashSet<Entry<Integer, Integer>> userSuggestions = new HashSet<Entry<Integer, Integer>>();

        if (campFilter != null) {
            for (Entry<Integer, Integer> suggestion : user.getSuggestions().entrySet()) {
                if (suggestion.getKey() == campFilter) {
                    userSuggestions.add(suggestion);
                }
            }
            campFilter = null;
        } else {
            for (Entry<Integer, Integer> suggestion : user.getSuggestions().entrySet()) {
                userSuggestions.add(suggestion);
            }
        }

        return userSuggestions;
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
    public boolean addSuggestion(String creatorId, int campid, int suggestionid) {
        Student user = (Student) findUserById(creatorId);
        if (user == null) {
            return false;
        } else {
            if (!user.addSuggestion(campid, suggestionid)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Overriden method from UserController
     * Tags an enquiry to a Student object's enquiries HashMap
     * 
     * @param userId    the Student this enquiry belongs to
     * 
     * @param campId    the Camp this enquiry belongs to
     * 
     * @param enquiryId the enquiry's ID
     * 
     * @return true if the enquiry is successfully added to the Student, false
     */
    @Override
    public boolean addEnquiry(String userId, int campid, int enquiryId) {
        Student user = (Student) findUserById(userId);
        if (user == null) {
            return false;
        } else {
            if (!user.addEnquiry(campid, enquiryId)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Overriden method from CampController
     * Removes an enquiry from a User object's enquiries HashSet
     * 
     * @param creatorId    the User's ID
     * 
     * @param campid       the Camp this enquiry belongs to
     * 
     * @param suggestionId the suggestion's ID
     * 
     * @return true if the suggestion is successfully deleted, false otherwise
     */
    @Override
    public boolean deleteSuggestion(String creatorId, int campid, int suggestionId) {
        Student user = (Student) findUserById(creatorId);
        if (user != null && user.removeSuggestion(campid, suggestionId)) {
            return true;
        }
        return false;
    }

    /**
     * Overriden method from UserController
     * Removes an enquiry from a User object's enquiries HashSet
     * 
     * @param creatorId the User's ID
     * 
     * @param campId    the Camp this enquiry belongs to
     * 
     * @param enquiryId the enquiry's ID
     * 
     * @return true if the enquiry is successfully deleted, false otherwise
     */
    @Override
    public boolean deleteEnquiry(String creatorId, int campid, int enquiryId) {
        Student user = (Student) findUserById(creatorId);
        if (user != null && user.removeEnquiry(campid, enquiryId)) {
            return true;
        }
        return false;
    }

    /**
     * Overriden method from UserController
     * Increaments a Student's points
     * 
     * @param userid the Student's ID
     * @param points the points to be added
     * @return the Student's new points
     */
    @Override
    public int incrementPoints(String userid, int points) {
        Student user = (Student) findUserById(userid);
        if (user != null) {
            user.incrementPoints(points);
            return user.getPoints();
        }
        return -1;
    }

    /**
     * Overriden method from UserController
     * Grants a Student a set of permissions
     * 
     * @param userid   the Student's ID
     * @param newperms the set of permissions to be granted
     * @return the Student's new set of permissions
     */
    @Override
    public EnumSet<Perms> grantPerms(String userid, EnumSet<Perms> newperms) {
        User user = (User) findUserById(userid);
        if (user != null) {
            user.addPerms(newperms);
            return user.getPerms();
        }
        return null;
    }

    /**
     * Overriden method from UserController
     * Denies a Student a set of permissions
     * 
     * @param userid       the Student's ID
     * @param removedPerms the set of permissions to be denied
     * @return the Student's new set of permissions
     */
    @Override
    public EnumSet<Perms> denyPerms(String userid, EnumSet<Perms> removedPerms) {
        User user = (User) findUserById(userid);
        if (user != null) {
            user.removePerms(removedPerms);
            return user.getPerms();
        }
        return null;
    }

    /**
     * Overriden method from UserController
     * Replaces a Student's set of permissions
     * 
     * @param userid           the Student's ID
     * @param replacementPerms the set of permissions to be replaced
     * @return the Student's new set of permissions
     */
    @Override
    public EnumSet<Perms> replacePerms(String userid, EnumSet<Perms> replacementPerms) {
        User user = (User) findUserById(userid);
        if (user != null) {
            user.replacePerms(replacementPerms);
            return user.getPerms();
        }
        return null;
    }

    @Override
    public Controller FilterUser(String userid) {
        this.userFilter = userid;
        return this;
    }

    @Override
    public Controller FilterCamp(int campid) {
        Integer typecastedCampid = campid;
        this.campFilter = typecastedCampid;
        return this;
    }

    @Override
    public Controller FilterAspect(Entry<CampAspects, ? extends Object> filter) {
        aspectFilter.put(filter.getKey(), filter.getValue());
        return this;
    }

    @Override
    public int addEnquiry(String enquiry, String ownerid, int campid) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int editEnquiry(int enquiryid, String enquiry) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getEnquiry(int enquiryid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HashMap<Integer, String> getEnquiries() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean deleteEnquiry(int enquiryid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean finaliseEnquiry(int enquiryid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean isEnquiryEditable(int enquiryid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int saveReply(int enquiryid, String reply) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String[] getReply(int enquiryid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int addSuggestion(Entry<CampAspects, ? extends Object> suggestion, String rationale, String ownerid,
            int campid) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int editSuggestion(int id, Entry<CampAspects, ? extends Object> edited, String rationale) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Entry<Entry<CampAspects, ? extends Object>, String> getSuggestion(int suggestionid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HashMap<Integer, Entry<CampAspects, ? extends Object>> getSuggestions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean deleteSuggestion(int suggestionid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean finaliseSuggestion(int suggestionid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean isSuggestionEditable(int suggestionid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getOwner(int suggestionid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getHostCamp(int suggestionid) {
        // TODO Auto-generated method stub
        return 0;
    }

}
