package entities;

import java.time.LocalDate;
import java.util.ArrayList;

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
    private ArrayList<Student> attendees;
    private int remainingCommitteeSlots;
    private ArrayList<Student> campCommittee;
    private Staff staffInCharge;
    private boolean visibility;
    private Faculty userGroup;

    public boolean isCampPastRegistrationDeadline() {
        return LocalDate.now().isAfter(this.registrationDeadline);
    }

    public boolean isCampAttendeeFull() {
        return this.attendees.size() >= this.remaningSlots;
    }

    public boolean isCampAttendee(Student attendee) {
        return this.attendees.contains(attendee);
    }

    public boolean addAttendee(Student attendee) {
        if (this.isCampAttendeeFull()) {
            return false;
        }
        this.attendees.add(attendee);
        this.remaningSlots -= 1;
        return true;
    }

    public boolean removeAttendee(Student attendee) {
        if (!this.attendees.contains(attendee)) {
            return false;
        }
        this.attendees.remove(attendee);
        this.remaningSlots += 1;
        return true;
    }

    public boolean isCampCommitteeFull() {
        return this.campCommittee.size() >= this.remainingCommitteeSlots;
    }

    public boolean addCommittee(Student committee) {
        if (this.isCampCommitteeFull()) {
            return false;
        }
        this.campCommittee.add(committee);
        this.remainingCommitteeSlots -= 1;
        return true;
    }

    public boolean removeCommittee(Student committee) {
        if (!this.campCommittee.contains(committee)) {
            return false;
        }
        this.campCommittee.remove(committee);
        this.remainingCommitteeSlots += 1;
        return true;
    }

    public boolean isCampCommittee(Student committee) {
        return this.campCommittee.contains(committee);
    }

}
