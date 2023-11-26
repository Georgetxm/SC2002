package entities;

import java.util.EnumSet;
import types.Faculty;
import types.Perms;

/**
 * Represents a Staff object
 * Inherits from User class
 * 
 * @see User
 *      A Staff can have multiple camps as a STAFFIC @see CampAspect, CampInfo
 *      A Staff can create, edit, delete camps
 *      A Staff can approve suggestions to the camp they are a STAFFIC of
 * @author Teo Xuan Ming
 * @version 1.1
 * @since 2021-11-06
 */
public class Staff extends User {
    /**
     * Default constructor for Staff
     */
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

    /**
     * Creates new Staff object with the given parameters, meant for use when
     * reading from staff_list.csv
     * 
     * @param userId
     * @param name
     * @param password
     * @param faculty
     */
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
