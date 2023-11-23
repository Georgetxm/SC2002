package entities;

import java.util.EnumSet;
import java.util.HashSet;

import types.Faculty;
import types.Perms;

/**
 * Represents a User object
 * Inherited by Student and Staff classes
 * 
 * 
 * A User has the base attributres of a userId, password, faculty and
 * permissions
 * 
 * @author Teo Xuan Ming
 * @version 1.0
 * @since 2021-11-12
 */

public class User {
    /**
     * userId is the unique identifier of a User, the string before the @ in the NTU
     * email e.g. XTEO011
     */
    private String userId;

    /**
     * password is the password of the User
     */
    private String password;

    /**
     * faculty is the faculty of the User, see types/Faculty.java for the list of
     */
    private Faculty faculty;

    /**
     * permission is the set of permissions that the User has, see types/Perms.java
     * for the list of permissions
     */
    private EnumSet<Perms> permission;

    /**
     * camps is a HashSet of campIds that the Student is registered as an attendee
     */
    private HashSet<Integer> camps;

    /**
     * Default constructor for User
     */
    public User() {
        this.userId = "Name";
        this.password = "";
        this.faculty = Faculty.WHOLE_NTU;
        this.camps = new HashSet<Integer>();
        this.permission = EnumSet.noneOf(Perms.class);
    }

    /**
     * Constructor for User
     * 
     * @param userId
     * @param password
     * @param faculty
     * @param permission
     */
    public User(String userId, String password, Faculty faculty, EnumSet<Perms> permission) {
        this.userId = userId;
        this.password = password;
        this.faculty = faculty;
        this.permission = permission;
        this.camps = new HashSet<Integer>();
    }

    /**
     * Returns the userId of the User
     * 
     * @return the userId of the User
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Returns the password of the User
     * 
     * @return the password of the User
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns the faculty of the User
     * 
     * @return the faculty of the User
     */
    public Faculty getFaculty() {
        return this.faculty;
    }

    /**
     * Returns the permissions of the User
     * 
     * @return the permissions of the User
     */
    public EnumSet<Perms> getPerms() {
        return this.permission;
    }

    /**
     * Returns true if the User has been given the new permission(s)
     * 
     * @param newPerms
     * @return true if the User has been given the new permission(s)
     */
    public boolean addPerms(EnumSet<Perms> newPerms) {
        this.permission.addAll(newPerms);
        return true;
    }

    /**
     * Returns true if the User permission(s) attribute has been replaced with the
     * 
     * @param newPerms
     * @return true if the User permission(s) attribute has been replaced with the
     */
    public boolean replacePerms(EnumSet<Perms> newPerms) {
        this.permission = newPerms;
        return true;
    }

    /**
     * Returns true if the specified permission(s) has been removed from the User
     * 
     * @param newPerms
     * @return true if the specified permission(s) has been removed from the User
     */
    public boolean removePerms(EnumSet<Perms> newPerms) {
        this.permission.removeAll(newPerms);
        return true;
    }

    /**
     * Returns true if the User has the specified permission(s)
     * 
     * @param newPassword
     * @return true if the User has the specified permission(s)
     */
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * Returns the list of Student's camps
     * 
     * @return the list of Student's camps
     */
    public HashSet<Integer> getCamps() {
        return this.camps;
    }

    /**
     * Adds a campId to the Student's camps HashSet as an attendee
     * 
     * @param campId the campId to be added
     * 
     * @return true if the campId is successfully added, false otherwise
     */
    public boolean registerForCamp(Integer campId) {
        this.camps.add(campId);
        return true;
    }

    /**
     * Removes a campId from the Student's camps HashSet as an attendee
     * 
     * @param campid the campId to be removed
     * 
     * @return true if the campId is successfully removed, false otherwise
     */
    public boolean withdrawFromCamp(int campid) {
        this.camps.remove(campid);
        return true;
    }
}
