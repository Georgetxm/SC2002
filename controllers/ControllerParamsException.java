package controllers;

/**
 * Represents a custom exception for when a parameter is of the wrong type
 * 
 * @author Teo Xuan Ming
 * @version 1.1
 * @since 2021-11-24
 */
public class ControllerParamsException extends Exception {
	/**
	 * Constructor for ControllerParamsException
	 * 
	 * @param info
	 */
	public ControllerParamsException(String info) {
		super(info);
	}

	/**
	 * Automatically generated serial number for serialisation
	 */
	private static final long serialVersionUID = 3116206905553311529L;

}
