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
    private boolean visibility;
    private final LocalDate creationDate;

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
     * @param attendees
     * @param campCommittee
     * @param visibility
     * @param creationDate
     * @param enquiries
     * @param suggestions
     */
    public Camp(CampInfo campInfo, boolean visibility, LocalDate creationDate, HashSet<Integer> enquiries,
            HashSet<Integer> suggestions) {
        this.campInfo = campInfo;
        this.campid = nextCampId++;
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
}
