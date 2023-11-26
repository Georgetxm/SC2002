package controllers;

import java.util.ArrayList;

/**
 * Represents the EnquiryControlInterface to be implemented by EnquiryController
 * 
 * @author Teo Xuan Ming
 * @version 1.1
 * @since 2021-11-24
 */
public interface EnquiryControlInterface {

	/**
	 * Add an enquiry
	 * 
	 * @param enquiry the enquiry text to be added
	 * @return the enquiry id if successful, -1 if not
	 */
	int add(String enquiry);

	/**
	 * Edit an enquiry
	 * 
	 * @param enquiryid the enquiry id to be edited
	 * @param enquiry   the new enquiry text
	 * @return the enquiry id if successful, -1 if not
	 * @throws ControllerItemMissingException if enquiry cannot be found
	 */
	int edit(int enquiryid, String enquiry) throws ControllerItemMissingException;

	/**
	 * Get an enquiry text based on enquiry id
	 * 
	 * @param enquiryid the enquiry id to be retrieved
	 * @return the enquiry text if successful, null if not
	 */
	String get(int enquiryid);

	/**
	 * Finalise an enquiry by setting the seen attribute to true
	 * so that enquiry can be replied to
	 * 
	 * @param enquiryid the enquiry id to be finalised
	 * @return true if successful, controller item missing exception if not
	 * @throws ControllerItemMissingException if enquiry cannot be found
	 */
	Boolean finalise(int enquiryid) throws ControllerItemMissingException; // mark as uneditable

	/**
	 * Check if an enquiry is editable
	 * If an enquiry has been finalised, (i.e.seen attribute is true), it is not
	 * editable
	 * 
	 * @param enquiryid the enquiry id to be checked
	 * @return true if editable, throws controller item missing exception if not
	 * @throws ControllerItemMissingException if enquiry cannot be found
	 */
	Boolean isEditable(int enquiryid) throws ControllerItemMissingException;

	/**
	 * Save a reply to an enquiry
	 * 
	 * @param enquiryid the enquiry id to be replied to
	 * @param reply     the reply text
	 * @return the enquiry id if successful, -1 if not
	 * @throws ControllerItemMissingException if enquiry cannot be found
	 */
	int saveReply(int enquiryid, String reply) throws ControllerItemMissingException;

	/**
	 * Get the replies to an enquiry
	 * 
	 * @param enquiryid the enquiry id to be retrieved
	 * @return the replies to an enquiry if successful, controller item missing
	 * @throws ControllerItemMissingException if enquiry cannot be found
	 */

	ArrayList<String> getReplies(int enquiryid) throws ControllerItemMissingException;

	/**
	 * Delete an enquiry
	 * 
	 * @param enquiryid the enquiry id to be deleted
	 * @return true if successful, controller item missing exception if not
	 * @throws ControllerItemMissingException if enquiry cannot be found
	 */
	Boolean delete(int enquiryid) throws ControllerItemMissingException;
}
