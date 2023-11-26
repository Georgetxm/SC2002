package types;

/**
 * Represents the Perms ENUM
 * Used to represent the permissions of a user to determine what they can do
 * in the system and what they can view
 * 
 * @author Teo Xuan Ming
 * @version 1.1
 * @since 2021-11-24
 */

public enum Perms {
	/**
	 * The default permission
	 */
	DEFAULT,
	/**
	 * The permission to create camps given to staffs
	 */
	CREATE_CAMP,
	/**
	 * The permission to delete camps given to staff who is in charge of a camp
	 */
	DELETE_CAMP,
	/**
	 * The permission to edit camps given to staff who is in charge of a camp
	 */
	EDIT_CAMP,
	/**
	 * The permission to view all camps guven to all users
	 */
	VIEW_EVERY_CAMP,
	/**
	 * The permission to approve camp suggestions
	 * given to staff who is in charge of a camp
	 */
	APPROVE_CAMP_SUGGESTION,
	/**
	 * The permission to view camp enquiries
	 * Given to staff and students who are in the camp
	 */
	VIEW_CAMP_ENQUIRY,
	/**
	 * The permission to submit camp enquiries
	 * given to students who are in the camp
	 */
	SUBMIT_CAMP_ENQUIRY,
	/**
	 * The permission to reply camp enquiries
	 * given to camp committee members in a camp
	 */
	REPLY_CAMP_ENQUIRY,
	/**
	 * The permission to submit camp suggestions
	 * given to students who are committee members in a camp
	 */
	SUBMIT_CAMP_SUGGESTION,
	/**
	 * The permission to view camp suggestions
	 * given to staff who is in charge of a camp
	 * given to students who are committee members in a camp
	 */
	VIEW_CAMP_SUGGESTION,
	/**
	 * The permission to edit camp suggestions
	 * given to student who are committee members in a camp and has submitted a
	 * suggestion
	 */
	EDIT_CAMP_SUGGESTION,
	/**
	 * The permission to delete camp suggestions
	 * given to student who are committee members in a camp and has submitted a
	 * suggestion
	 */
	DELETE_CAMP_SUGGESTION,
	/**
	 * The permission to view camp student information
	 * given to staff who is in charge of a camp and camp committee members
	 */
	VIEW_CAMP_STUDENT_INFORMATION,
	/**
	 * The permission to view all available camps
	 */
	VIEW_AVAILABLE_CAMP,
	/**
	 * The permission to register as an attendee
	 * given to students who are not in any camps or committee of that camp
	 */
	REGISTER_AS_ATTENDEE,
	/**
	 * The permission to register as an committee
	 * given to students who are not in any committees
	 */
	REGISTER_AS_COMMITTEE,
	/**
	 * The permission to withdraw as an attendee
	 * given to students who are in a camp as an attendee
	 */
	WITHDRAW_AS_ATTENDEE
}
