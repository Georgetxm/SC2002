package controllers;

import java.time.LocalDate;
import java.util.AbstractMap;
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
    private HashMap<String, User> users;
    private HashMap<Integer, Camp> camps;
    private HashMap<Integer, Suggestion> suggestions;
    private HashMap<Integer, Enquiry> enquiries;
    private HashMap<Integer, Camp> visibleCamps;
    private String userFilter;
    private Integer campFilter;
    private boolean visibleFilter;
    private HashMap<CampAspects, Object> aspectFilter;

    public MainController(HashMap<String, User> users, HashMap<Integer, Camp> camps) {
        this.users = users;
        this.camps = camps;
        this.suggestions = new HashMap<Integer, Suggestion>();
        this.enquiries = new HashMap<Integer, Enquiry>();
        this.visibleCamps = new HashMap<Integer, Camp>();
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
        if (camps.containsKey(campId)) {
            return camps.get(campId);
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
        if (users.containsKey(userId) && Student.class.isInstance(users.get(userId))) {
            return (Student) users.get(userId);
        }
        if (users.containsKey(userId) && Staff.class.isInstance(users.get(userId))) {
            return (Staff) users.get(userId);
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
    public int addCamp(CampInfo info, String staffid) {
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
                Enquiry e = findEnquiryById(enquiry);
                if (e != null) {
                    enquiries.remove(e.getEnquiryId(), e);
                }
            }
            for (Integer suggestion : camp.getSuggestions()) {
                Suggestion s = findSuggestionById(suggestion);
                if (s != null) {
                    suggestions.remove(s.getSuggestionId(), s);
                }
            }
            camps.remove(camp.getCampid(), camp);
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
        this.visibleFilter = true;
        return this;
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
                visibleCamps.put(campid, camp);
            } else {
                visibleCamps.remove(campid);
            }
            return true;
        }
        return false;
    }

    /**
     * Overriden method from CampController
     * First puts all the Camps' campId and camp name in the the controller's camps
     * HashMap into the local variable HashMap "filteredCampList"
     * then checks for the filters and removes the camps that do not match the
     * filters
     * 
     * @return HashMap of Camp Id and Camp Name that matches all specified filters
     */
    @Override
    public HashMap<Integer, String> getCamps() throws ControllerItemMissingException {
        HashMap<Integer, String> filteredCampList = new HashMap<Integer, String>();

        // Iterate through camps and add the campId and campName to the HashMap
        this.camps.forEach((k, v) -> {
            filteredCampList.put(k, v.getCampInfo().info().get(CampAspects.NAME).toString());
        });
        System.out.println("Pre user filter");
        System.out.println(filteredCampList);
        System.out.println(userFilter);
        // If visibleFilter is true, intersect the HashMap with the visibleCamps
        // HashSet which will omit the camps that are not visible
        if (visibleFilter) {
            filteredCampList.keySet().retainAll(visibleCamps.keySet());
        }
        System.out.println("After visible filter");
        System.out.println(filteredCampList);
        System.out.println(userFilter);
        // If userFilter get list of camp ids user has,
        // intersect with filteredCampList to omit camps that specified userFilterId is
        // not in
        if (userFilter != null) {
        	System.out.println(userFilter);
            User user = (User) findUserById(userFilter);
            if (user == null) {
                throw new ControllerItemMissingException("User not found");
            }
            HashSet<Integer> userCamps = user.getCamps();
            filteredCampList.keySet().retainAll(userCamps);
        }
        System.out.println("Post user filter");
        System.out.println(filteredCampList);
        System.out.println(userFilter);
        // If aspectFilter is not null, iterate through the HashMap and remove camps
        // that do not match the aspectFilter
        if (aspectFilter != null) {
            filteredCampList.forEach((k, v) -> {
                boolean match = true;
                for (Entry<CampAspects, ? extends Object> aspect : aspectFilter.entrySet()) {
                    if (findCampById(k).getCampInfo().info().get(aspect.getKey()) != aspect.getValue()) {
                        match = false;
                    }
                }
                if (!match) {
                    filteredCampList.remove(k);
                }
            });
        }

        userFilter = null;
        campFilter = null;
        aspectFilter.clear();
        visibleFilter = false;

        if (filteredCampList.size() > 0) {
            return filteredCampList;
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
        if (camp != null
                && user.registerForCamp(campId)
                && camp.addCommittee(userId)
                && user.setCampComittee(campId)) {
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

    /**
     * Overriden methods from Enquiry Controller
     * addEnquiry adds an enquiry to the list of enquiries in MainController's
     * attributes
     * also tags the enquiry to the camp and user
     * 
     * @param enquiry the enquiry text to be added
     * @param ownerid the owner of the enquiry, which should be a student
     * @param campid  the camp the enquiry is about
     * @return the enquiry id if successful, -1 if not
     */
    @Override
    public int addEnquiry(String enquiry, String ownerid, int campid) {
        if (!Student.class.isInstance(findUserById(ownerid))) {
            return -1;
        }
        Student user = (Student) findUserById(ownerid);

        Camp camp = findCampById(campid);
        if (camp == null) {
            return -1;
        }
        Enquiry newEnquiry = new Enquiry(ownerid, campid, enquiry, false, LocalDate.now());
        camp.addEnquiry(newEnquiry.getEnquiryId());
        user.addEnquiry(campid, newEnquiry.getEnquiryId());
        return newEnquiry.getEnquiryId();
    }

    /**
     * Overriden methods from Enquiry Controller
     * Edit an enquiry text
     * Updates the last update date of the enquiry
     * 
     * @param enquiryid the enquiry id to be edited
     * @param enquiry   the new enquiry text
     * @return the enquiry id if successful, -1 if not
     * @throws ControllerItemMissingException
     */
    @Override
    public int editEnquiry(int enquiryid, String enquiry) throws ControllerItemMissingException {
        Enquiry enquiryToEdit = findEnquiryById(enquiryid);
        if (enquiryToEdit == null) {
            throw new ControllerItemMissingException("Enquiry does not exist");
        }
        if (enquiryToEdit.setEnquiryBody(enquiry)) {
            enquiryToEdit.updateLastUpdateDate(LocalDate.now());
            return enquiryToEdit.getEnquiryId();
        } else {
            return -1;
        }
    }

    /**
     * Overriden methods from Enquiry Controller
     * Get an enquiry text
     * 
     * @param enquiryid the enquiry id to be retrieved
     * @return the enquiry text if successful, null if not
     */
    @Override
    public String getEnquiry(int enquiryid) {
        if (findEnquiryById(enquiryid) == null) {
            return null;
        }

        return findEnquiryById(enquiryid).getEnquiryBody();
    }

    /**
     * Get all enquiries that fulfill the filter specified from the filter methods
     * 
     * @see FilterCamp(int campid)
     * @see FilterUser(String userid)
     *      Can be filtered by campid and userid
     *      Clears the filter after getting the enquiries
     * 
     * @return a HashMap of enquiry id and enquiry text
     * @throws ControllerParamsException
     * @throws ControllerItemMissingException
     */
    @Override
    public HashMap<Integer, String> getEnquiries() throws ControllerParamsException, ControllerItemMissingException {
        HashMap<Integer, Enquiry> filteredEnquiryList = this.enquiries;

        if (!Student.class.isInstance(findUserById(userFilter))) {
            throw new ControllerParamsException("Specified userFilter is not a student");
        }

        // If userFilter specified, get list of enquiry ids specified user has,
        // intersect with filteredEnquiryList to omit enquiries that specified
        // userFilterId is not in
        if (userFilter != null) {
            Student filteredUser = (Student) findUserById(userFilter);
            if (filteredUser == null) {
                throw new ControllerItemMissingException("User not found");
            }
            filteredEnquiryList.keySet().retainAll((filteredUser.getEnquiries().values()));
        }

        // If campFilter specified, get list of enquiry ids specified camp has,
        // intersect with filteredEnquiryList to omit enquiries that specified
        // campFilterId is not in
        if (campFilter != null) {
            Camp filteredCamp = findCampById(campFilter);
            if (filteredCamp == null) {
                throw new ControllerItemMissingException("Camp not found");
            }
            filteredEnquiryList.keySet().retainAll(filteredCamp.getEnquiries());
        }

        userFilter = null;
        campFilter = null;

        if (filteredEnquiryList.size() > 0) {
            // Convert the HashMap of enquiry id and enquiry object to enquiry id and
            // enquiry text
            HashMap<Integer, String> filteredEnquiryListString = new HashMap<Integer, String>();
            filteredEnquiryList.forEach((enquiryId, enquiryObj) -> {
                filteredEnquiryListString.put(enquiryId, enquiryObj.getEnquiryBody());
            });
            return filteredEnquiryListString;
        } else {
            return null;
        }
    }

    /**
     * Overriden methods from Enquiry Controller
     * Delete an enquiry
     * Removes the enquiry id from the camp and user attribute
     * 
     * @param enquiryid the enquiry id to be deleted
     * @return true if successful, controller item missing exception if not
     * @throws ControllerItemMissingException
     */
    @Override
    public Boolean deleteEnquiry(int enquiryid) throws ControllerItemMissingException {
        Enquiry enquiry = findEnquiryById(enquiryid);
        if (enquiry == null) {
            throw new ControllerItemMissingException("Enquiry does not exist");
        }

        Camp camp = findCampById(enquiry.getCampId());
        if (camp != null) {
            camp.removeEnquiry(enquiryid);
        }

        if (Student.class.isInstance(findUserById(enquiry.getCreatorUserId()))) {
            Student user = (Student) findUserById(enquiry.getCreatorUserId());
            user.removeEnquiry(camp.getCampid(), enquiryid);
        }

        enquiries.remove(enquiryid);
        return true;
    }

    /**
     * Overriden methods from Enquiry Controller
     * Finalise an enquiry by setting the seen attribute to true
     * so that enquiry can be replied to and points allocated to the responding
     * committee member
     * 
     * @param enquiryid the enquiry id to be finalised
     * @return true if successful, controller item missing exception if not
     * @throws ControllerItemMissingException
     */
    @Override
    public Boolean finaliseEnquiry(int enquiryid) throws ControllerItemMissingException {
        if (!enquiries.containsKey(enquiryid)) {
            throw new ControllerItemMissingException("Enquiry does not exist");
        }
        Enquiry enquiry = findEnquiryById(enquiryid);
        enquiry.setSeen(true);
        return true;
    }

    /**
     * Overriden methods from Enquiry Controller
     * Check if an enquiry is editable
     * If an enquiry has been finalised, (i.e.seen attribute is true), it is not
     * editable
     * 
     * @param enquiryid the enquiry id to be checked
     * @return true if editable, throws controller item missing exception if not
     * @throws ControllerItemMissingException
     */
    @Override
    public Boolean isEnquiryEditable(int enquiryid) throws ControllerItemMissingException {
        if (!enquiries.containsKey(enquiryid)) {
            throw new ControllerItemMissingException("Enquiry does not exist");
        }
        Enquiry enquiry = findEnquiryById(enquiryid);
        return !enquiry.isSeen();
    }

    /**
     * Overriden methods from Enquiry Controller
     * Save the reply to an enquiry under the replies attribute
     * 
     * @param enquiryid the enquiry id to be replied to
     * @param reply     the reply text
     * @return the enquiry id if successful, -1 if not
     * @throws ControllerItemMissingException
     */
    @Override
    public int saveReply(int enquiryid, String reply) throws ControllerItemMissingException {
        if (!enquiries.containsKey(enquiryid)) {
            throw new ControllerItemMissingException("Enquiry does not exist");
        }
        Enquiry enquiry = findEnquiryById(enquiryid);
        if (enquiry.addReply(reply)) {
            return enquiryid;
        } else {
            return -1;
        }
    }

    /**
     * Overriden methods from Enquiry Controller
     * Get the replies to an enquiry
     * 
     * @param enquiryid the enquiry id to be retrieved
     * @return the replies to an enquiry if successful, controller item missing
     * @throws ControllerItemMissingException
     */
    @Override
    public ArrayList<String> getReplies(int enquiryid) throws ControllerItemMissingException {
        if (!enquiries.containsKey(enquiryid)) {
            throw new ControllerItemMissingException("Enquiry does not exist");
        }
        Enquiry enquiry = findEnquiryById(enquiryid);
        return enquiry.getReplies();
    }

    /**
     * Overriden methods from Enquiry Controller
     * Get the owner of an enquiry
     * 
     * @param enquiryid the enquiry id to be retrieved
     * @return the owner of an enquiry if successful, controller item missing
     * @throws ControllerItemMissingException
     */
    @Override
    public String getEnquiryOwner(int enquiryid) throws ControllerItemMissingException {
        if (!enquiries.containsKey(enquiryid)) {
            throw new ControllerItemMissingException("Enquiry does not exist");
        }
        Enquiry enquiry = findEnquiryById(enquiryid);
        if (enquiry != null) {
            return enquiry.getCreatorUserId();
        }
        return null;
    }

    /**
     * Overriden methods from Enquiry Controller
     * Get the camp of an enquiry
     * 
     * @param enquiryid the enquiry id to be retrieved
     * @return the camp of an enquiry if successful, controller item missing
     * @throws ControllerItemMissingException
     */
    @Override
    public int getEnquiryHostCamp(int enquiryid) throws ControllerItemMissingException {
        if (!enquiries.containsKey(enquiryid)) {
            throw new ControllerItemMissingException("Enquiry does not exist");
        }
        Enquiry enquiry = findEnquiryById(enquiryid);
        if (enquiry != null) {
            return enquiry.getCampId();
        }
        return -1;
    }

    /**
     * Overriden methods from Suggestion Controller
     * Creates a new suggestion, each suggestion contains only one aspect of
     * CampInfo @see CampInfo
     * i.e. Multiple suggestions are separate entries in the suggestions HashMap
     * adds it to the list of suggestions in MainController's attributes
     * also tags the suggestion to the camp and user
     * 
     * @param suggestion the suggestion aspect text to be added
     * @param ownerid    the owner of the suggestion, which should be a student
     * @throws ControllerItemMissingException
     */
    @Override
    public int addSuggestion(Entry<CampAspects, ? extends Object> suggestion, String rationale, String ownerid,
            int campid) throws ControllerItemMissingException {
        Camp camp = findCampById(campid);
        if (camp == null) {
            throw new ControllerItemMissingException("Camp does not exist");
        }
        Suggestion newSuggestion = new Suggestion(ownerid, campid, rationale, suggestion, LocalDate.now());
        this.suggestions.put(newSuggestion.getSuggestionId(), newSuggestion);
        camp.addSuggestion(newSuggestion.getSuggestionId());

        if (Student.class.isInstance(findUserById(ownerid))) {
            Student user = (Student) findUserById(ownerid);
            user.addSuggestion(campid, newSuggestion.getSuggestionId());
        }

        return newSuggestion.getSuggestionId();
    }

    /**
     * Overriden methods from Suggestion Controller
     * Edit a suggestion based on the suggestion id
     * Updates the suggestion aspect and rationale
     * Updates the last updated date
     * 
     * @param id        the suggestion id to be edited
     * @param edited    the new suggestion aspect object
     * @param rationale the new rationale
     * @return the suggestion id if successful throws controller item missing
     *         exception if not
     * @throws ControllerItemMissingException
     */
    @Override
    public int editSuggestion(int id, Entry<CampAspects, ? extends Object> edited, String rationale)
            throws ControllerItemMissingException {
        Suggestion suggestionToBeEdited = findSuggestionById(id);
        if (suggestionToBeEdited == null) {
            throw new ControllerItemMissingException("Suggestion does not exist");
        }

        if (suggestionToBeEdited.setSuggestionAspect(edited) && suggestionToBeEdited.setRationale(rationale)) {
            suggestionToBeEdited.setLastUpdatedDate(LocalDate.now());
            return suggestionToBeEdited.getSuggestionId();
        } else {
            throw new ControllerItemMissingException("Suggestion not edited");
        }
    }

    /**
     * Gets a suggestion by its id
     * 
     * @param suggestionid the suggestion id to be retrieved
     * @return the suggestion object if successful, controller item missing
     *         exception if not
     * @throws ControllerItemMissingException
     * 
     */
    @Override
    public Entry<Entry<CampAspects, ? extends Object>, String> getSuggestion(int suggestionid)
            throws ControllerItemMissingException {
        Suggestion suggestion = findSuggestionById(suggestionid);
        if (suggestion == null) {
            throw new ControllerItemMissingException("Suggestion does not exist");
        } else {
            Entry<Entry<CampAspects, ? extends Object>, String> suggestionEntry = new AbstractMap.SimpleEntry<Entry<CampAspects, ? extends Object>, String>(
                    suggestion.getSuggestionAspect(), suggestion.getRationale());
            return suggestionEntry;
        }
    }

    /**
     * Overriden methods from Suggestion Controller
     * 
     * Get all suggestions that fulfill the filter specified from the filter methods
     * 
     * @see FilterCamp(int campid)
     * @see FilterUser(String userid)
     *      Can be filtered by campid and userid
     *      Clears the filter after getting the suggetions
     * 
     * @return a HashMap of suggestion id and suggestion aspect
     * @throws ControllerParamsException
     * @throws ControllerItemMissingException
     */
    @Override
    public HashMap<Integer, Entry<CampAspects, ? extends Object>> getSuggestions()
            throws ControllerParamsException, ControllerItemMissingException {
        HashMap<Integer, Suggestion> filteredSuggestionList = this.suggestions;

        if (!Student.class.isInstance(findUserById(userFilter))) {
            throw new ControllerParamsException("Specified userFilter is not a student");
        }

        // If userFilter specified, get list of suggestions ids specified user has,
        // intersect with filteredEnquiryList to omit suggestions that specified
        // userFilterId is not in
        if (userFilter != null) {
            Student filteredUser = (Student) findUserById(userFilter);
            if (filteredUser == null) {
                throw new ControllerItemMissingException("User not found");
            }
            filteredSuggestionList.keySet().retainAll((filteredUser.getSuggestions().values()));
        }

        // If campFilter specified, get list of enquiry ids specified camp has,
        // intersect with filteredEnquiryList to omit enquiries that specified
        // campFilterId is not in
        if (campFilter != null) {
            Camp filteredCamp = findCampById(campFilter);
            if (filteredCamp == null) {
                throw new ControllerItemMissingException("Camp not found");
            }
            filteredSuggestionList.keySet().retainAll(filteredCamp.getSuggestions());
        }

        userFilter = null;
        campFilter = null;

        if (filteredSuggestionList.size() > 0) {
            // Convert the HashMap of suggestion ids and suggestion objects
            // to suggestion id and suggestion aspect
            HashMap<Integer, Entry<CampAspects, ? extends Object>> filteredSuggestionHashMap = new HashMap<Integer, Entry<CampAspects, ? extends Object>>();
            filteredSuggestionList.forEach((suggestionId, suggestionObj) -> {
                filteredSuggestionHashMap.put(suggestionId, suggestionObj.getSuggestionAspect());
            });
            return filteredSuggestionHashMap;
        } else {
            return null;
        }
    }

    @Override
    public Boolean deleteSuggestion(int suggestionid) throws ControllerItemMissingException {
        Suggestion suggestionToDelete = findSuggestionById(suggestionid);
        if (suggestionToDelete == null) {
            throw new ControllerItemMissingException("Suggestion does not exist");
        }

        Camp camp = findCampById(suggestionToDelete.getCampId());
        if (camp != null) {
            camp.removeSuggestion(suggestionid);
        }

        if (Student.class.isInstance(findUserById(suggestionToDelete.getCreatorUserId()))) {
            Student user = (Student) findUserById(suggestionToDelete.getCreatorUserId());
            user.removeSuggestion(camp.getCampid(), suggestionid);
        }

        suggestions.remove(suggestionid);
        return true;
    }

    /**
     * Overriden methods from Suggestion Controller
     * Sets a suggestion to be accepted so that the suggestion is finalised
     */
    @Override
    public Boolean finaliseSuggestion(int suggestionid) throws ControllerItemMissingException {
        Suggestion suggestion = findSuggestionById(suggestionid);
        if (suggestion == null) {
            throw new ControllerItemMissingException("Suggestion does not exist");
        }
        suggestion.setAccepted(true);
        if (Student.class.isInstance(findUserById(suggestion.getCreatorUserId()))) {
            Student user = (Student) findUserById(suggestion.getCreatorUserId());
            user.incrementPoints(1);
        }
        return null;
    }

    @Override
    public Boolean isSuggestionEditable(int suggestionid) throws ControllerItemMissingException {
        Suggestion suggestion = findSuggestionById(suggestionid);
        if (suggestion == null) {
            throw new ControllerItemMissingException("Suggestion does not exist");
        }
        return !suggestion.isAccepted();
    }

    /**
     * Overriden methods from Suggestion Controller
     * Get the owner of a suggestion
     * 
     * @param suggestionid the suggestion id to be retrieved
     * @return the owner of a suggestion if successful, controller item missing
     * 
     * @throws ControllerItemMissingException
     */
    @Override
    public String getSuggestionOwner(int suggestionid) throws ControllerItemMissingException {
        Suggestion suggestion = findSuggestionById(suggestionid);
        if (suggestion == null) {
            throw new ControllerItemMissingException("Suggestion does not exist");
        }
        return suggestion.getCreatorUserId();
    }

    /**
     * Overriden methods from Suggestion Controller
     * Get the camp id of a suggestion
     * 
     * @param suggestionid the suggestion id to be retrieved
     * @return the camp id of a suggestion if successful, controller item missing
     */
    @Override
    public int getHostCamp(int suggestionid) throws ControllerItemMissingException {
        Suggestion suggestion = findSuggestionById(suggestionid);
        if (suggestion == null) {
            throw new ControllerItemMissingException("Suggestion does not exist");
        }
        return suggestion.getCampId();
    }

}
