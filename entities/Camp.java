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
    /**
     * campInfo is the CampInfo object containing the camp's information @see
     * CampInfo
     */
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
     * @param campInfo    the CampInfo object containing the camp's information
     * @param creationDate the date that the camp was created
     */
    public Camp(CampInfo campInfo, LocalDate creationDate) {
        this.campInfo = campInfo;
        this.campid = nextCampId++;
        this.creationDate = creationDate;
    }

    /**
     * Constructor for Camp. Intended use is for creating a new camp when reading
     * from
     * the camp_list.csv where enquiries and suggestions ids are already included
     * 
     * @param campId         the campId to be assigned to the new camp
     * @param campInfo       the CampInfo object containing the camp's information
     * @param creationDate   the date that the camp was created
     * @param attendeeCount  the number of attendees in the camp
     * @param committeeCount the number of committee members in the camp
     */
    public Camp(int campId, CampInfo campInfo, LocalDate creationDate, int attendeeCount,
            int committeeCount) {
        this.campInfo = campInfo;
        this.campid = campId;
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
     * @param nextCampId the next campId to be assigned to a new camp
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
     * @param attendeeCount the attendeeCount to set
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
     * @param committeeCount the committeeCount to set
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
