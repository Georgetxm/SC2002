package entities;

import java.util.EnumSet;
import java.util.HashSet;

import types.Faculty;
import types.Perms;

public class Staff extends User {

    public Staff() {
        super();
    }

    public Staff(String userId, String password, Faculty faculty, EnumSet<Perms> permissions, HashSet<Integer> camps) {
        super(userId, password, faculty, permissions);
    }

    // public HashMap<Integer, String> getCampInCharge() {
    // return this.campInCharge;
    // }

    // public boolean addCampInCharge(Integer campid, String name) {
    // try {
    // this.campInCharge.put(campid, name);
    // } catch (Exception e) {
    // System.out.println("Error adding camp in charge: " + e.getMessage());
    // return false;
    // }
    // return true;
    // }
}
