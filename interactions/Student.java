package interactions;

import java.util.EnumSet;
import java.util.HashSet;

import entities.User;
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

public class Student extends User {
    /*
     * camps is a HashSet of campIds that the Student is registered as an attendee
     */
    private HashSet<Integer> camps;

    /*
     * campCommittee is the campId of the camp that the Student is a committee of
     * student can only be in one camp committee at a time
     * default value is -1, i.e. not in any camp committee
     */
    private int campCommittee;

    /*
     * points is the number of points the Student has accumulated
     * from giving suggestions and having it approved
     */
    private int points;

    /*
     * Enquiries that the Student has created
     * enquiries is an HashMap of form campId, enquiryId
     */
    private HashSet<Integer> enquiries;

    /*
     * Suggestions that the Student has created
     * suggestions is an HashMap of form campId, suggestionId
     */
    private HashSet<Integer> suggestions;

    public Student() {
        super();
        this.camps = new HashSet<Integer>();
        this.campCommittee = -1;
        this.points = 0;
        this.enquiries = new HashSet<Integer>();
        this.suggestions = new HashSet<Integer>();
    }

    /*
     * Creates new Student object with the given parameters
     * 
     * @param userId the Student's userId
     * 
     * @param password the Student's password
     * 
     * @param faculty the Student's faculty, @see Faculty
     * 
     * @param permissions the Student's permissions, @see Perms
     * 
     * @return the new Student object
     *
     */
    public Student(String userId, String password, Faculty faculty, EnumSet<Perms> permissions) {
        super(userId, password, faculty, permissions);
        this.camps = new HashSet<Integer>();
        this.campCommittee = -1;
        this.points = 0;
        this.enquiries = new HashSet<Integer>();
        this.suggestions = new HashSet<Integer>();
    }

    /*
     * Returns the list of Student's camps
     * 
     * @return the list of Student's camps
     */
    public HashSet<Integer> getCamps() {
        return this.camps;
    }

    /*
     * Returns the Student's points
     * 
     * @return the Student's points
     */
    public int getPoints() {
        return this.points;
    }

    /*
     * Returns the campId that the Student is a campCommittee of
     * 
     * @return the campId that the Student is a campCommittee of
     */
    public int getCampCommittee() {
        return this.campCommittee;
    }

    /*
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

    /*
     * Adds a campId to the Student's camps HashSet as an attendee
     * 
     * @param campId the campId to be added
     * 
     * @return true if the campId is successfully added, false otherwise
     */
    public boolean registerAsAttendee(Integer campId) {
        this.camps.add(campId);
        return true;
    }

    /*
     * Removes a campId from the Student's camps HashSet as an attendee
     * 
     * @param campid the campId to be removed
     * 
     * @return true if the campId is successfully removed, false otherwise
     */
    public boolean withdrawFromCamp(int campid) {
        this.camps.remove(campid);
        return true;
    }

    /*
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

    /*
     * Returns the list of enquiries this student has created
     * 
     * @return the list of enquiries this student has created
     */
    public HashSet<Integer> getEnquiries() {
        return this.enquiries;
    }

    /*
     * Adds an enquiryId to the Student's list of enquiries
     * 
     * @param enquiryId the enquiryId to be added
     * 
     * @return true if the enquiryId is successfully added, false otherwise
     */
    public boolean addEnquiry(int enquiryId) {
        this.enquiries.add(enquiryId);
        return true;
    }

    /*
     * Removes an enquiryId from the Student's list of enquiries
     * 
     * @param enquiryId the enquiryId to be removed
     * 
     * @return true if the enquiryId is successfully removed, false otherwise
     */
    public boolean removeEnquiry(int enquiryId) {
        this.enquiries.remove(enquiryId);
        return true;
    }

    /*
     * Returns the list of suggestions this student has created
     * 
     * @return the list of suggestions this student has created
     */
    public HashSet<Integer> getSuggestions() {
        return this.suggestions;
    }

    /*
     * Adds a suggestionId to the Student's list of suggestions
     * 
     * @param suggestionId the suggestionId to be added
     * 
     * @return true if the suggestionId is successfully added, false otherwise
     */
    public boolean addSuggestion(int suggestionId) {
        this.suggestions.add(suggestionId);
        return true;
    }

    /*
     * Removes a suggestionId from the Student's list of suggestions
     * 
     * @param suggestionId the suggestionId to be removed
     * 
     * @return true if the suggestionId is successfully removed, false otherwise
     */
    public boolean removeSuggestion(int suggestionId) {
        this.suggestions.remove(suggestionId);
        return true;
    }
}
