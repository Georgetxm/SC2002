package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import types.Faculty;
import types.Location;

public class Camp {
    private int campid;
    private String name;
    private String description;
    private ArrayList<LocalDate> campDates;
    private LocalDate registrationDeadline;
    private Location location;
    private int remaningSlots;
    private HashMap<String, Faculty> attendees;
    private int remainingCommitteeSlots;
    private HashMap<String, Faculty> campCommittee;
    private final Staff staffInCharge;
    private boolean visibility;
    private final Faculty userGroup;
    private final LocalDate creationDate;

    public Camp(int campid, String name, String description, ArrayList<LocalDate> campDates,
            LocalDate registrationDeadline, Location location, int remaningSlots, HashMap<String, Faculty> attendees,
            int remainingCommitteeSlots, HashMap<String, Faculty> campCommittee, Staff staffInCharge,
            boolean visibility,
            Faculty userGroup, LocalDate creationDate) {
        this.campid = campid;
        this.name = name;
        this.description = description;
        this.campDates = campDates;
        this.registrationDeadline = registrationDeadline;
        this.location = location;
        this.remaningSlots = remaningSlots;
        this.attendees = attendees;
        this.remainingCommitteeSlots = remainingCommitteeSlots;
        this.campCommittee = campCommittee;
        this.staffInCharge = staffInCharge;
        this.visibility = visibility;
        this.userGroup = userGroup;
        this.creationDate = creationDate;
    }

    public int getCampid() {
        return this.campid;
    }

    public String getName() {
        return this.name;
    }

    public boolean setName(String newName) {
        if (newName == null || newName.isEmpty()) {
            return false;
        }
        this.name = newName;
        return true;
    }

    public boolean setDescription(String newDescription) {
        if (newDescription == null || newDescription.isEmpty()) {
            return false;
        }
        this.description = newDescription;
        return true;
    }

    public String getDescription() {
        return this.description;
    }

    public ArrayList<LocalDate> getCampDates() {
        return this.campDates;
    }

    public boolean setCampDates(ArrayList<LocalDate> newCampDates) {
        if (newCampDates == null || newCampDates.isEmpty()) {
            return false;
        }
        this.campDates = newCampDates;
        return true;
    }

    public Location getLocation() {
        return this.location;
    }

    public boolean setLocation(Location location) {
        if (this.location == null) {
            return false;
        }
        this.location = location;
        return true;
    }

    public Staff getStaffInCharge() {
        return this.staffInCharge;
    }

    public boolean getVisibility() {
        return this.visibility;
    }

    public boolean setVisibility(boolean visibility) {
        this.visibility = visibility;
        return true;
    }

    public Faculty getUserGroup() {
        return this.userGroup;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public boolean isCampPastRegistrationDeadline() {
        return LocalDate.now().isAfter(this.registrationDeadline);
    }

    public boolean isCampAttendeeFull() {
        return this.attendees.size() >= this.remaningSlots;
    }

    public boolean isCampAttendee(String userId) {
        return this.attendees.containsKey(userId);
    }

    public boolean addAttendee(String userId, Faculty faculty) {
        if (this.isCampAttendeeFull()) {
            return false;
        }
        this.attendees.put(userId, faculty);
        this.remaningSlots -= 1;
        return true;
    }

    public boolean removeAttendee(String userId) {
        if (!this.isCampAttendee(userId)) {
            return false;
        }
        this.attendees.remove(userId);
        this.remaningSlots += 1;
        return true;
    }

    public boolean isCampCommitteeFull() {
        return this.campCommittee.size() >= this.remainingCommitteeSlots;
    }

    public boolean isCampCommittee(String userId) {
        return this.campCommittee.containsKey(userId);
    }

    public boolean addCommittee(String userId, Faculty faculty) {
        if (this.isCampCommitteeFull()) {
            return false;
        }
        this.campCommittee.put(userId, faculty);
        this.remainingCommitteeSlots -= 1;
        return true;
    }

    public boolean removeCommittee(String userId) {
        if (!this.isCampCommittee(userId)) {
            return false;
        }
        this.campCommittee.remove(userId);
        this.remainingCommitteeSlots += 1;
        return true;
    }

}
