package entities;

import java.util.EnumSet;
import types.Faculty;
import types.Perms;

public class Staff extends User {

    public Staff() {
        super("Staffname", "", Faculty.WHOLE_NTU, EnumSet.of(
                Perms.DEFAULT,
                Perms.CREATE_CAMP,
                Perms.DELETE_CAMP,
                Perms.EDIT_CAMP,
                Perms.APPROVE_CAMP_SUGGESTION,
                Perms.REPLY_CAMP_ENQUIRY,
                Perms.VIEW_CAMP_ENQUIRY,
                Perms.VIEW_CAMP_SUGGESTION,
                Perms.VIEW_EVERY_CAMP,
                Perms.VIEW_CAMP_STUDENT_INFORMATION));
    }

    public Staff(String userId, String password) {
        super(userId, password, Faculty.WHOLE_NTU, EnumSet.of(
                Perms.DEFAULT,
                Perms.CREATE_CAMP,
                Perms.DELETE_CAMP,
                Perms.EDIT_CAMP,
                Perms.APPROVE_CAMP_SUGGESTION,
                Perms.REPLY_CAMP_ENQUIRY,
                Perms.VIEW_CAMP_ENQUIRY,
                Perms.VIEW_CAMP_SUGGESTION,
                Perms.VIEW_EVERY_CAMP,
                Perms.VIEW_CAMP_STUDENT_INFORMATION));
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
