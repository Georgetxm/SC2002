package types;

/**
 * The roles a user can take in a camp
 */
public enum Role {
	/**
	 * Refers to an attendee of the camp.<p>
	 * A basic relationship between a student and the camp, stored in directory.
	 * A student may attend multiple camps.
	 * Attending camps does not confer special rights.
	 */
	ATTENDEE,
	/**
	 * Refers to a camp commitee member of the camp<p>
	 * A special relationship between a student and the camp, on top of the attendee relationship.
	 * Student can only be a camp committee of one camp.
	 * Confers special privileges but only for that one camp.
	 * As the committee is an add-on on the attendee relationship, it cannot be stored in directory and is thus stored in user.
	 */
	COMMITTEE,
	/**
	 * Refers to the staff IC of a camp.<p>
	 * A basic relationship between a staff and a camp.
	 * A staff may be staff IC of multiple camps.
	 */
	STAFF
}
