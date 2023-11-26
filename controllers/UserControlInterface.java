package controllers;

import java.util.EnumSet;

import types.Faculty;
import types.Perms;

/**
 * Represents the UserControlInterface to be implemented by
 * UserController
 * 
 * @author Teo Xuan Ming
 * @version 1.1
 * @since 2021-11-24
 */
public interface UserControlInterface {
	/**
	 * Returns the User's Faculty
	 * 
	 * @param userid the User's ID
	 * @return the User's Faculty
	 */
	public Faculty getUserFaculty(String userid);

	/**
	 * Tags a User to a Camp object's committee HashMap attribute
	 * Tags a Camp to a Student object's campCommittee int attribute
	 * 
	 * @param campId the Camp this User belongs to
	 * 
	 * @param userId the User's ID
	 * 
	 * @return true if the User is successfully added to the Camp, false otherwise
	 */
	public boolean setCampCommittee(String userid, int campid);

	/**
	 * Gets the Camp committee a student is in
	 * 
	 * @param userId the User's ID
	 * @return the Camp committee a student is in
	 */
	public Integer getCampCommitteeOfStudent(String userId);

	/**
	 * Increaments a Student's points
	 * 
	 * @param userid the Student's ID
	 * @param points the points to be added
	 * @return the Student's new points
	 */
	public int incrementPoints(String userid, int points);

	/**
	 * Grants a Student a set of permissions
	 * 
	 * @param userid   the Student's ID
	 * @param newperms the set of permissions to be granted
	 * @return the Student's new set of permissions
	 */
	public EnumSet<Perms> grantPerms(String userid, EnumSet<Perms> newperms);

	/**
	 * Denies a Student a set of permissions
	 * 
	 * @param userid       the Student's ID
	 * @param removedPerms the set of permissions to be denied
	 * @return the Student's new set of permissions
	 */
	public EnumSet<Perms> denyPerms(String userid, EnumSet<Perms> removedperms);

	/**
	 * Replaces a Student's set of permissions
	 * 
	 * @param userid           the Student's ID
	 * @param replacementPerms the set of permissions to be replaced
	 * @return the Student's new set of permissions
	 */
	public EnumSet<Perms> replacePerms(String userid, EnumSet<Perms> replacementperms);

	/**
	 * Gets the class of a User (i.e. either Student or Staff)
	 * 
	 * @param userid the User's ID
	 * @return the User's class (i.e. either Student or Staff
	 */
	public Class<?> getClass(String userid);

	/**
	 * Changes a User's password
	 * 
	 * @param password the new password
	 * @param id       the User's ID
	 * @return true if the password is successfully changed, false otherwise
	 */
	public Boolean changePassword(String password, String id);
}
