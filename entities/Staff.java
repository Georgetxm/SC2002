package entities;

import java.util.EnumSet;
import java.util.HashMap;

import types.Faculty;
import types.Perms;

public class Staff extends User {
    private HashMap<Integer, String> campInCharge;

    public Staff() {
        super();
        this.campInCharge = new HashMap<Integer, String>();
    }

    public Staff(String userId, String password, Faculty faculty, EnumSet<Perms> permissions) {
        super(userId, password, faculty, permissions);
        this.campInCharge = new HashMap<Integer, String>();
    }

    public HashMap<Integer, String> getCampInCharge() {
        return this.campInCharge;
    }

    public boolean addCampInCharge(Integer campid, String name) {
        try {
            this.campInCharge.put(campid, name);
        } catch (Exception e) {
            System.out.println("Error adding camp in charge: " + e.getMessage());
            return false;
        }
        return true;
    }
}
