package entities;

import java.time.LocalDate;

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
    /**
     * campId is the unique id of the camp
     */
    private final int campid;

    /**
     * nextCampId is the next campId to be assigned to a new camp
     * static attribute to ensure that each campId is unique
     */
    private static int nextCampId = 0;

    /**
     * visibility is true if the camp is visible to students, false otherwise
     */
    private boolean visibility;
    /**
     * attendeeCount is the current number of attendees in the camp
     */
    private int attendeeCount = 0;

    /**
     * committeeCount is the current number of committee members in the camp
     */
    private int committeeCount = 0;

    /**
     * creationDate is the date that the camp was created
     */
    private final LocalDate creationDate;

    /**
     * Constructor for Camp. Intended use is for creating a new camp when entering
     * information from the CLI
     * 
     * @param campInfo
     * @param visibility
     * @param creationDate
     */
    public Camp(CampInfo campInfo, boolean visibility, LocalDate creationDate) {
        this.campInfo = campInfo;
        this.campid = nextCampId++;
        this.visibility = visibility;
        this.creationDate = creationDate;
    }

    /**
     * Constructor for Camp. Intended use is for creating a new camp when reading
     * from
     * the camp_list.csv where enquiries and suggestions ids are already included
     * 
     * @param campInfo
     * @param visibility
     * @param creationDate
     * @param attendeeCount
     * @param commiteeCount
     * @param committeeCount
     */
    public Camp(int campId, CampInfo campInfo, boolean visibility, LocalDate creationDate, int attendeeCount,
            int committeeCount) {
        this.campInfo = campInfo;
        this.campid = campId;
        this.visibility = visibility;
        this.attendeeCount = attendeeCount;
        this.committeeCount = committeeCount;
        this.creationDate = creationDate;
    }

    /**
     * When reading from the camp.csv, the campId is already included
     * get the next campId from the csv file
     * this ensures that the next campId is always greater than the previous
     * and previous enquiryId will not be overwritten
     * 
     * @param nextCampId
     */
    public static void setNextCampId(int nextCampId) {
        Camp.nextCampId = nextCampId;
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
     * Getter for attendeeCount
     * 
     * @return the attendeeCount
     */
    public int getAttendeeCount() {
        return this.attendeeCount;
    }

    /**
     * Setter for attendeeCount
     * 
     * @param attendeeCount
     * @return true if attendeeCount is successfully set, false otherwise
     */
    public boolean setAttendeeCount(int attendeeCount) {
        this.attendeeCount = attendeeCount;
        return true;
    }

    /**
     * Getter for committeeCount
     * 
     * @return the committeeCount
     */
    public int getCommitteeCount() {
    return this.committeeCount;
    }

    /**
     * Setter for committeeCount
     * 
     * @param committeeCount
     * @return true if committeeCount is successfully set, false otherwise
     */
    public boolean setCommitteeCount(int committeeCount) {
        this.committeeCount = committeeCount;
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
}
