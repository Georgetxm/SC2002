package entities;

import java.util.EnumSet;
import types.Faculty;
import types.Perms;

public class Staff extends User {

    public Staff() {
        super("staffUserId", "name", "password", Faculty.WHOLE_NTU, EnumSet.of(
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

    public Staff(String userId, String name, String password, Faculty faculty) {
        super(userId, name, password, faculty, EnumSet.of(
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
}
