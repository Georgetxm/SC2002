package entities;

/**
 * Custom exception for when a user's information is missing
 * 
 * 
 * @author Teo Xuan Ming
 * @version 1.0
 * @since 2021-11-12
 */

public class UserInfoMissingException extends Exception {
	public UserInfoMissingException(String info) {
		super(info);
	}

	/**
	 * Automatically generated serial number for serialisation
	 */
	private static final long serialVersionUID = 3516206905553311529L;

}
