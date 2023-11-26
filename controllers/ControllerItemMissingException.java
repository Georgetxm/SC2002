package controllers;

/**
 * Represents a custom exception for when an item is missing from the controller
 * 
 * @author Teo Xuan Ming
 * @version 1.1
 * @since 2021-11-24
 */
public class ControllerItemMissingException extends Exception {

    /**
     * Constructor for ControllerItemMissingException
     * 
     * @param info the information to be displayed when the exception is thrown
     */
    public ControllerItemMissingException(String info) {
        super(info);
    }

    /**
     * Automatically generated serial number for serialisation
     */
    private static final long serialVersionUID = 3226206905553311529L;

}
