package entities;

import java.time.LocalDate;
import java.util.HashSet;

import types.CampAspect;

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

    public Camp(CampInfo campInfo, HashSet<String> attendees, HashSet<String> campCommittee,
            boolean visibility, LocalDate creationDate) {
        this.campInfo = campInfo;
        this.campid = nextCampId++;
        this.attendees = attendees;
        this.campCommittee = campCommittee;
        this.visibility = visibility;
        this.creationDate = creationDate;
    }

    public Camp(CampInfo campInfo, HashSet<String> attendees, HashSet<String> campCommittee,
            boolean visibility, LocalDate creationDate, HashSet<Integer> enquiries, HashSet<Integer> suggestions) {
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
        return this.attendees.size() >= (int) this.campInfo.info().get(CampAspect.SLOTS);
    }

    public boolean addAttendee(String userId) {
        if (this.isCampAttendeeFull()) {
            return false;
        }
        this.attendees.add(userId);
        Integer currentAttendees = (Integer) this.campInfo.info().get(CampAspect.SLOTS);
        this.campInfo.info().put(CampAspect.SLOTS, ((Integer) (currentAttendees + 1)));

        return true;
    }

    public boolean removeAttendee(String userId) {
        if (!this.isCampAttendee(userId)) {
            return false;
        }
        this.attendees.remove(userId);
        Integer updatedAttendees = (Integer) this.campInfo.info().get(CampAspect.SLOTS) - 1;
        this.campInfo.info().put(CampAspect.SLOTS, updatedAttendees);
        return true;
    }

    public HashSet<String> getCampCommittee() {
        return this.campCommittee;
    }

    public boolean isCampCommittee(String userId) {
        return this.campCommittee.contains(userId);
    }

    public boolean isCampCommitteeFull() {
        return this.campCommittee.size() >= (int) this.campInfo.info().get(CampAspect.COMMITTEESLOTS);
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
        this.campInfo.info().compute(CampAspect.SLOTS,
                (key, value) -> (value == null) ? (Integer) 1 : (Integer) value + 1);

        return true;
    }

    public boolean removeCommittee(String userId) {
        if (!this.isCampCommittee(userId)) {
            return false;
        }
        this.campCommittee.remove(userId);
        Integer currentAttendees = (Integer) this.campInfo.info().get(CampAspect.SLOTS);
        this.campInfo.info().put(CampAspect.SLOTS, currentAttendees - 1);
        return true;
    }

    public HashSet<Integer> getEnquiries() {
        return this.enquiries;
    }

    public boolean addEnquiry(int enquiryId) {
        this.enquiries.add(enquiryId);
        return true;
    }

    public boolean removeEnquiry(int enquiryId) {
        this.enquiries.remove(enquiryId);
        return true;
    }

    public HashSet<Integer> getSuggestions() {
        return this.suggestions;
    }

    public boolean addSuggestion(int suggestionId) {
        this.suggestions.add(suggestionId);
        return true;
    }

    public boolean removeSuggestion(int suggestionId) {
        this.suggestions.remove(suggestionId);
        return true;
    }
}
