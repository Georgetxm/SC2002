package entities;

import java.util.EnumSet;
import java.util.HashMap;
import types.Faculty;
import types.Perms;

/**
 * Represents a Student object
 * Inherits from User class
 * 
 * @see User
 *      A Student can have multiple camps as an attendee, but only one camp as a
 *      committee
 *      A Student can create enquiries and suggestions
 *      A Student can have points for giving suggestions and having it approved
 * @author Teo Xuan Ming
 * @version 1.0
 * @since 2021-11-06
 */

/**
 * 
 */
/**
 * 
 */
/**
 * 
 */
public class Student extends User {
    /**
     * campCommittee is the campId of the camp that the Student is a committee of
     * student can only be in one camp committee at a time
     * default value is -1, i.e. not in any camp committee
     */
    private int campCommittee;

    /**
     * points is the number of points the Student has accumulated
     * from giving suggestions and having it approved
     */
    private int points;

    /**
     * Enquiries that the Student has created
     * enquiries is an HashMap of form campId, enquiryId
     */
    private HashMap<Integer, Integer> enquiries;

    /**
     * Suggestions that the Student has created
     * suggestions is an HashMap of form campId, suggestionId
     */
    private HashMap<Integer, Integer> suggestions;

    public Student() {
        super("Armstrong", "StrongPassword", Faculty.ADM, EnumSet.of(
                Perms.DEFAULT,
                Perms.SUBMIT_CAMP_ENQUIRY,
                Perms.VIEW_AVAILABLE_CAMP,
                Perms.REGISTER_AS_ATTENDEE,
                Perms.REGISTER_AS_COMITTEE,
                Perms.WITHDRAW_AS_ATTENDEE));
        this.campCommittee = -1;
        this.points = 0;
        this.enquiries = new HashMap<Integer, Integer>();
        this.suggestions = new HashMap<Integer, Integer>();
    }

    /**
     * Creates new Student object with the given parameters
     * 
     * @param userId      the Student's userId
     * 
     * @param password    the Student's password
     * 
     * @param faculty     the Student's faculty, @see Faculty
     * 
     * @param permissions the Student's permissions, @see Perms
     *
     */
    public Student(String userId, String password, Faculty faculty, EnumSet<Perms> permissions) {
        super(userId, password, faculty, permissions);
        this.campCommittee = -1;
        this.points = 0;
        this.enquiries = new HashMap<Integer, Integer>();
        this.suggestions = new HashMap<Integer, Integer>();
    }

    /**
     * Returns the Student's points
     * 
     * @return the Student's points
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Returns the campId that the Student is a campCommittee of
     * 
     * @return the campId that the Student is a campCommittee of
     */
    public int getCampCommittee() {
        return this.campCommittee;
    }

    /**
     * Overrides the Student's campCommittee if the Student was previously in
     * campCommittee (i.e. CampCommittee != -1)
     * 
     * @param campId the campId to be set as the Student's campCommittee
     * 
     * @return true if the campId is successfully set, false otherwise
     */
    public boolean setCampComittee(int campId) {
        this.campCommittee = campId;
        return true;
    }

    /**
     * Increments the Student's points by the given points
     * 
     * @param points the number of points to be added
     * 
     * @return true if the points are successfully added, false otherwise
     */
    public boolean incrementPoints(int points) {
        this.points += points;
        return true;
    }

    /**
     * Returns the list of enquiries this student has created in the form of
     * HashMap of form campId, enquiryId
     * 
     * @return the list of enquiries this student has created
     */
    public HashMap<Integer, Integer> getEnquiries() {
        return this.enquiries;
    }

    /**
     * Adds an enquiryId to the Student's list of enquiries
     * 
     * @param enquiryId the enquiryId to be added
     * 
     * @return true if the enquiryId is successfully added, false otherwise
     */
    public boolean addEnquiry(int campid, int enquiryid) {
        this.enquiries.put(campid, enquiryid);
        return true;
    }

    /**
     * Removes an enquiryId from the Student's list of enquiries
     * 
     * @param enquiryId the enquiryId to be removed
     * 
     * @return true if the enquiryId is successfully removed, false otherwise
     */
    public boolean removeEnquiry(int campid, int enquiryId) {
        this.enquiries.remove(campid, enquiryId);
        return true;
    }

    /**
     * Returns the list of suggestions this student has created in the form of
     * HashMap of form campId, suggestionId
     * 
     * @return the list of suggestions this student has created
     */
    public HashMap<Integer, Integer> getSuggestions() {
        return this.suggestions;
    }

    /**
     * Adds a suggestionId to the Student's list of suggestions
     * 
     * @param suggestionId the suggestionId to be added
     * 
     * @return true if the suggestionId is successfully added, false otherwise
     */
    public boolean addSuggestion(int campid, int suggestionid) {
        this.suggestions.put(campid, suggestionid);
        return true;
    }

    /**
     * Removes a suggestionId from the Student's list of suggestions
     * 
     * @param suggestionId the suggestionId to be removed
     * 
     * @return true if the suggestionId is successfully removed, false otherwise
     */
    public boolean removeSuggestion(int campid, int suggestionId) {
        this.suggestions.remove(campid, suggestionId);
        return true;
    }
}
