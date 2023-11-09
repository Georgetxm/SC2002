package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import core.CampInfo;
import types.CampAspects;
import types.Faculty;
import types.Location;
import types.Role;

public class Camp {
    private CampInfo campInfo;
    private final int campid;
    private static int nextCampId = 0;
    private HashSet<String> attendees;
    private HashSet<String> campCommittee;
    private boolean visibility;
    private final LocalDate creationDate;
    private HashMap<String, Integer> enquiries;
    private HashMap<String, Integer> suggestions;

    // private String name;
    // private String description;
    // private ArrayList<LocalDate> campDates;
    // private LocalDate registrationDeadline;
    // private Location location;
    // private int remaningSlots;
    // private int remainingCommitteeSlots;
    // private final Staff staffInCharge;
    // private final Faculty userGroup;

    public Camp(CampInfo campInfo, HashSet<String> attendees, HashSet<String> campCommittee,
            boolean visibility, LocalDate creationDate) {
        this.campInfo = campInfo;
        this.campid = nextCampId++;
        this.attendees = attendees;
        this.campCommittee = campCommittee;
        this.visibility = visibility;
        this.creationDate = creationDate;
    }

    public int getCampid() {
        return this.campid;
    }

    public CampInfo getCampInfo() {
        return this.campInfo;
    }

    public boolean getVisibility() {
        return this.visibility;
    }

    public boolean setVisibility(boolean visibility) {
        this.visibility = visibility;
        return true;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public HashSet<String> getAttendees() {
        return this.attendees;
    }

    public boolean isCampAttendee(String userId) {
        return this.attendees.contains(userId);
    }

    public boolean isCampAttendeeFull() {
        return this.attendees.size() >= (int) this.campInfo.info().get(CampAspects.SLOTS);
    }

    public boolean addAttendee(String userId) {
        if (this.isCampAttendeeFull()) {
            return false;
        }
        this.attendees.add(userId);
        Integer currentAttendees = (Integer) this.campInfo.info().get(CampAspects.SLOTS);
        this.campInfo.info().put(CampAspects.SLOTS, currentAttendees + 1);

        return true;
    }

    public boolean removeAttendee(String userId) {
        if (!this.isCampAttendee(userId)) {
            return false;
        }
        this.attendees.remove(userId);
        Integer updatedAttendees = (Integer) this.campInfo.info().get(CampAspects.SLOTS) - 1;
        this.campInfo.info().put(CampAspects.SLOTS, updatedAttendees);
        return true;
    }

    public boolean isCampCommittee(String userId) {
        return this.campCommittee.contains(userId);
    }

    public boolean isCampCommitteeFull() {
        return this.campCommittee.size() >= (int) this.campInfo.info().get(CampAspects.COMMITTEESLOTS);
    }

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
        this.campInfo.info().compute(CampAspects.SLOTS,
                (key, value) -> (value == null) ? (Integer) 1 : (Integer) value + 1);

        return true;
    }

    public boolean removeCommittee(String userId) {
        if (!this.isCampCommittee(userId)) {
            return false;
        }
        this.campCommittee.remove(userId);
        Integer currentAttendees = (Integer) this.campInfo.info().get(CampAspects.SLOTS);
        this.campInfo.info().put(CampAspects.SLOTS, currentAttendees - 1);
        return true;
    }

    public HashMap<String, Integer> getEnquiries() {
        return this.enquiries;
    }

    public boolean addEnquiry(String creatorId, int enquiryId) {
        this.enquiries.put(creatorId, enquiryId);
        return true;
    }

    public boolean removeEnquiry(String creatorId, int enquiryId) {
        this.enquiries.remove(creatorId, enquiryId);
        return true;
    }

    public HashMap<String, Integer> getSuggestions() {
        return this.suggestions;
    }

    public boolean addSuggestion(String creatorId, int suggestionId) {
        this.suggestions.put(creatorId, suggestionId);
        return true;
    }

    public boolean removeSuggestion(String creatorId, int suggestionId) {
        this.suggestions.remove(creatorId, suggestionId);
        return true;
    }
}
