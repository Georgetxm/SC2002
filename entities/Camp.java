package entities;

import java.time.LocalDate;
import java.util.HashSet;

import types.CampAspect;

/**
 * Represents a Camp object
 * 
 * 
 * A Camp contains the CampInfo attribute which contains the information of the
 * camp @see CampInfo
 * with each information being an Aspect of the camp @see CampAspect
 * A camp object also contains userIds of attendees and camp committee, as well
 * as the ids of enquiries and suggestions tied to this camp object
 * Each camp object has a unique campId which is an auto-incremented static
 * attribute
 * 
 * @author Teo Xuan Ming
 * @version 1.0
 * @since 2021-11-12
 */

public class Camp {
    private CampInfo campInfo;
    private final int campid;
    private static int nextCampId = 0;
    private HashSet<String> attendees = new HashSet<String>();
    private HashSet<String> campCommittee = new HashSet<String>();
    private boolean visibility;
    private final LocalDate creationDate;
    private HashSet<Integer> enquiries = new HashSet<Integer>();
    private HashSet<Integer> suggestions = new HashSet<Integer>();

    /**
     * Constructor for Camp. Intended use is for creating a new camp when entering
     * information from the CLI
     * 
     * @param campInfo
     * @param attendees
     * @param campCommittee
     * @param visibility
     * @param creationDate
     */
    public Camp(CampInfo campInfo, HashSet<String> attendees, HashSet<String> campCommittee,
            boolean visibility, LocalDate creationDate) {
        this.campInfo = campInfo;
        this.campid = nextCampId++;
        this.attendees = attendees;
        this.campCommittee = campCommittee;
        this.visibility = visibility;
        this.creationDate = creationDate;
    }

    /**
     * Constructor for Camp. Intended use is for creating a new camp when reading
     * from
     * the camp_list.csv where enquiries and suggestions ids are already included
     * 
     * @param campInfo
     * @param attendees
     * @param campCommittee
     * @param visibility
     * @param creationDate
     * @param enquiries
     * @param suggestions
     */
    public Camp(CampInfo campInfo, HashSet<String> attendees, HashSet<String> campCommittee,
            boolean visibility, LocalDate creationDate, HashSet<Integer> enquiries, HashSet<Integer> suggestions) {
        this.campInfo = campInfo;
        this.campid = nextCampId++;
        this.attendees = attendees;
        this.campCommittee = campCommittee;
        this.visibility = visibility;
        this.creationDate = creationDate;
    }

    /**
     * Getter for campId
     * 
     * @return the campId
     */
    public int getCampid() {
        return this.campid;
    }

    /**
     * Getter for campInfo object
     * 
     * @return the campInfo object
     */
    public CampInfo getCampInfo() {
        return this.campInfo;
    }

    /**
     * Getter for visibility
     * 
     * @return the visibility
     */
    public boolean getVisibility() {
        return this.visibility;
    }

    /**
     * Setter for visibility
     * 
     * @param visibility
     * @return true if visibility is successfully set, false otherwise
     */
    public boolean setVisibility(boolean visibility) {
        this.visibility = visibility;
        return true;
    }

    /**
     * Getter for creationDate
     * 
     * @return the creationDate
     */
    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    /**
     * Getter for attendees
     * 
     * @return the attendees
     */
    public HashSet<String> getAttendees() {
        return this.attendees;
    }

    /**
     * Returns true if the userId is an attendee of this camp
     * 
     * @param userId
     * @return true if the userId is an attendee of this camp
     */
    public boolean isCampAttendee(String userId) {
        return this.attendees.contains(userId);
    }

    /**
     * Returns true if the camp is full
     * 
     * @return true if the camp is full
     */
    public boolean isCampAttendeeFull() {
        return this.attendees.size() >= (int) this.campInfo.info().get(CampAspect.SLOTS);
    }

    /**
     * Adds a userId to the attendees HashSet
     * 
     * @param userId
     * @return true if the userId is successfully added, false otherwise
     */
    public boolean addAttendee(String userId) {
        if (this.isCampAttendeeFull()) {
            return false;
        }
        this.attendees.add(userId);
        Integer currentAttendees = (Integer) this.campInfo.info().get(CampAspect.SLOTS);
        this.campInfo.info().put(CampAspect.SLOTS, ((Integer) (currentAttendees + 1)));

        return true;
    }

    /**
     * Removes a userId from the attendees HashSet
     * 
     * @param userId
     * @return true if the userId is successfully removed, false otherwise
     */
    public boolean removeAttendee(String userId) {
        if (!this.isCampAttendee(userId)) {
            return false;
        }
        this.attendees.remove(userId);
        Integer updatedAttendees = (Integer) this.campInfo.info().get(CampAspect.SLOTS) - 1;
        this.campInfo.info().put(CampAspect.SLOTS, updatedAttendees);
        return true;
    }

    /**
     * Getter for campCommittee
     * 
     * @return campCommittee HashSet
     */
    public HashSet<String> getCampCommittee() {
        return this.campCommittee;
    }

    /**
     * Returns true if the userId is a camp committee of this camp
     * 
     * @param userId
     * @return true if the userId is a camp committee of this camp
     */
    public boolean isCampCommittee(String userId) {
        return this.campCommittee.contains(userId);
    }

    /**
     * Returns true if the camp committee is full
     * 
     * @return true if the camp committee is full
     */
    public boolean isCampCommitteeFull() {
        return this.campCommittee.size() >= (int) this.campInfo.info().get(CampAspect.COMMITTEESLOTS);
    }

    /**
     * Adds a userId to the campCommittee HashSet
     * 
     * @param userId
     * @return true if the userId is successfully added, false otherwise
     */
    public boolean addCommittee(String userId) {
        if (this.isCampCommitteeFull()) {
            System.out.println("Camp committee is full");
            return false;
        }

        if (this.isCampAttendee(userId)) {
            System.out.println(userId + " is an attendee");
            return false;
        }

        this.campCommittee.add(userId);
        // Integer currentAttendees = (Integer)
        // this.campInfo.info().get(CampAspects.COMMITTEESLOTS);
        // this.campInfo.info().put(CampAspects.SLOTS, currentAttendees + 1);
        this.campInfo.info().compute(CampAspect.SLOTS,
                (key, value) -> (value == null) ? (Integer) 1 : (Integer) value + 1);

        return true;
    }

    /**
     * Removes a userId from the campCommittee HashSet
     * 
     * @param userId
     * @return true if the userId is successfully removed, false otherwise
     */
    public boolean removeCommittee(String userId) {
        if (!this.isCampCommittee(userId)) {
            return false;
        }
        this.campCommittee.remove(userId);
        Integer currentAttendees = (Integer) this.campInfo.info().get(CampAspect.SLOTS);
        this.campInfo.info().put(CampAspect.SLOTS, currentAttendees - 1);
        return true;
    }

    /**
     * Getter for enquiries
     * 
     * @return enquiries HashSet
     */
    public HashSet<Integer> getEnquiries() {
        return this.enquiries;
    }

    /**
     * Adds an enquiryId to the enquiries HashSet
     * 
     * @param enquiryId
     * @return
     */
    public boolean addEnquiry(int enquiryId) {
        this.enquiries.add(enquiryId);
        return true;
    }

    /**
     * Removes an enquiryId from the enquiries HashSet
     * 
     * @param enquiryId
     * @return true if the enquiryId is successfully removed, false otherwise
     */
    public boolean removeEnquiry(int enquiryId) {
        this.enquiries.remove(enquiryId);
        return true;
    }

    /**
     * Getter for suggestions
     * 
     * @return suggestions HashSet
     */
    public HashSet<Integer> getSuggestions() {
        return this.suggestions;
    }

    /**
     * Adds a suggestionId to the suggestions HashSet
     * 
     * @param suggestionId
     * @return true if the suggestionId is successfully added, false otherwise
     */
    public boolean addSuggestion(int suggestionId) {
        this.suggestions.add(suggestionId);
        return true;
    }

    /**
     * Removes a suggestionId from the suggestions HashSet
     * 
     * @param suggestionId
     * @return true if the suggestionId is successfully removed, false otherwise
     */
    public boolean removeSuggestion(int suggestionId) {
        this.suggestions.remove(suggestionId);
        return true;
    }
}
