package entities;

import java.util.EnumSet;
import java.util.HashMap;

import types.Faculty;
import types.Perms;

public class Student extends User {
    private HashMap<Integer, String> camps;
    private int campCommittee;
    private int points;

    public Student() {
        super();
        this.camps = new HashMap<Integer, String>();
        this.campCommittee = -1;
        this.points = 0;
    }

    public Student(String userId, String password, Faculty faculty, EnumSet<Perms> permissions, int points) {
        super(userId, password, faculty, permissions);
        this.camps = new HashMap<Integer, String>();
        this.campCommittee = -1;
        this.points = points;
    }

    public HashMap<Integer, String> getCamps() {
        return this.camps;
    }

    public int getPoints() {
        return this.points;
    }

    public int getCampCommittee() {
        return this.campCommittee;
    }

    public HashMap<Integer, String> getCampsParticipatingIn() {
        return this.camps;
    }

    public boolean registerAsAttendee(Integer campid, String name) {
        try {
            this.camps.put(campid, name);
        } catch (Exception e) {
            System.out.println("Error adding camp in charge: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean registerAsCommittee(int campId) {
        if (this.campCommittee != -1) {
            return false;
        }
        this.campCommittee = campId;
        return true;
    }

    public boolean withdrawFromCamp(int campid) {
        try {
            this.camps.remove(campid);
        } catch (Exception e) {
            System.out.println("Error removing camp: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean incrementPoints(int points) {
        try {
            this.points += points;
        } catch (Exception e) {
            System.out.println("Error adding points: " + e.getMessage());
            return false;
        }
        return true;
    }

}
