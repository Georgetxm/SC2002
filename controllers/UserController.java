package controllers;

import java.util.EnumSet;
import java.util.HashMap;

import cams.ReadWriteUserCSV;
import entities.Staff;
import entities.Student;
import entities.User;
import types.Faculty;
import types.Perms;
import types.Role;

public class UserController implements UserControlInterface {
	private HashMap<String, User> userList;

	/**
	 * Constructor for UserController
	 * 
	 * @param userList the list of Users
	 */
	public UserController(HashMap<String, User> userList) {
		this.userList = userList;
	}

	/**
	 * Returns a User object given its ID
	 * 
	 * @param userId the User's ID
	 * 
	 * @return the User object with the given ID, null if not found
	 */

	public User findUserById(String userId) {
		if (userList.containsKey(userId) && Student.class.isInstance(userList.get(userId))) {
			return (Student) userList.get(userId);
		}
		if (userList.containsKey(userId) && Staff.class.isInstance(userList.get(userId))) {
			return (Staff) userList.get(userId);
		}
		return null;
	}

	/**
	 * Overriden method from UserController
	 * Returns the User's Faculty
	 * 
	 * @param userid the User's ID
	 * @return the User's Faculty
	 */

	@Override
	public Faculty getUserFaculty(String userid) {
		User user = findUserById(userid);
		if (!(user == null)) {
			return user.getFaculty();
		}
		return null;
	}

	/**
	 * Overriden method from UserController
	 * Tags a User to a Camp object's committee HashMap attribute
	 * Tags a Camp to a Student object's campCommittee int attribute
	 * 
	 * @param campId the Camp this User belongs to
	 * 
	 * @param userId the User's ID
	 * 
	 * @return true if the User is successfully added to the Camp, false otherwise
	 */

	@Override
	public boolean setCampCommittee(String userId, int campId) {
		if (!Student.class.isInstance(findUserById(userId))) {
			return false;
		}
		Student user = (Student) findUserById(userId);
		if (user.setCampComittee(campId)) {
			ReadWriteUserCSV.writeUserCSV(userList, "lists/student_list.csv", Role.COMMITTEE);
			return true;
		}
		return false;
	}

	/**
	 * Overriden method from UserController
	 * Gets the Camp committee a student is in
	 * 
	 * @param userId the User's ID
	 * @return the Camp committee a student is in
	 */
	@Override
	public Integer getCampCommitteeOfStudent(String userId) {
		if (Student.class.isInstance(findUserById(userId))) {
			Student user = (Student) findUserById(userId);
			if (!(user == null))
				return user.getCampCommittee();
		}

		return null;
	}

	/**
	 * Overriden method from UserController
	 * Increaments a Student's points
	 * 
	 * @param userid the Student's ID
	 * @param points the points to be added
	 * @return the Student's new points
	 */
	@Override
	public int incrementPoints(String userid, int points) {
		if (Student.class.isInstance(findUserById(userid))) {
			Student user = (Student) findUserById(userid);
			if (!(user == null)) {
				user.incrementPoints(points);
				ReadWriteUserCSV.writeUserCSV(userList, "lists/student_list.csv", Role.COMMITTEE);
				return user.getPoints();
			}
		}
		return -1;
	}

	/**
	 * Overriden method from UserController
	 * Grants a Student a set of permissions
	 * 
	 * @param userid   the Student's ID
	 * @param newperms the set of permissions to be granted
	 * @return the Student's new set of permissions
	 */
	@Override
	public EnumSet<Perms> grantPerms(String userid, EnumSet<Perms> newperms) {
		User user = (User) findUserById(userid);
		if (!(user == null)) {
			user.addPerms(newperms);
			if (user.getClass().equals(Student.class)) {
				ReadWriteUserCSV.writeUserCSV(userList, "lists/student_list.csv", Role.ATTENDEE);
			} else if (user.getClass().equals(Staff.class)) {
				ReadWriteUserCSV.writeUserCSV(userList, "lists/staff_list.csv", Role.STAFF);
			}
			return user.getPerms();
		}
		return null;
	}

	/**
	 * Overriden method from UserController
	 * Denies a Student a set of permissions
	 * 
	 * @param userid       the Student's ID
	 * @param removedPerms the set of permissions to be denied
	 * @return the Student's new set of permissions
	 */
	@Override
	public EnumSet<Perms> denyPerms(String userid, EnumSet<Perms> removedPerms) {
		User user = (User) findUserById(userid);
		if (!(user == null)) {
			user.removePerms(removedPerms);
			if (user.getClass().equals(Student.class)) {
				ReadWriteUserCSV.writeUserCSV(userList, "lists/student_list.csv", Role.ATTENDEE);
			} else if (user.getClass().equals(Staff.class)) {
				ReadWriteUserCSV.writeUserCSV(userList, "lists/staff_list.csv", Role.STAFF);
			}
			return user.getPerms();
		}
		return null;
	}

	/**
	 * Overriden method from UserController
	 * Replaces a Student's set of permissions
	 * 
	 * @param userid           the Student's ID
	 * @param replacementPerms the set of permissions to be replaced
	 * @return the Student's new set of permissions
	 */
	@Override
	public EnumSet<Perms> replacePerms(String userid, EnumSet<Perms> replacementPerms) {
		User user = (User) findUserById(userid);
		if (!(user == null)) {
			user.replacePerms(replacementPerms);
			if (user.getClass().equals(Student.class)) {
				ReadWriteUserCSV.writeUserCSV(userList, "lists/student_list.csv", Role.ATTENDEE);
			} else if (user.getClass().equals(Staff.class)) {
				ReadWriteUserCSV.writeUserCSV(userList, "lists/staff_list.csv", Role.STAFF);
			}
			return user.getPerms();
		}
		return null;
	}

	/**
	 * Overriden method from UserControlInterface
	 * Gets the class of a User (i.e. either Student or Staff)
	 * 
	 * @param userid the User's ID
	 * @return the User's class (i.e. either Student or Staff
	 */
	@Override
	public Class<?> getClass(String userid) {
		User user = (User) findUserById(userid);
		if (!(user == null)) {
			return user.getClass();
		}
		return null;
	}

}
