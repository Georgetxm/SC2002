package controllers;

import java.util.ArrayList;

/**
 * EnquiryController is an interface implementing Controller methods for
 * managing Enquiries
 * 
 * 
 * 
 * @author Teo Xuan Ming
 * @version 1.0
 * @since 2021-11-12
 */
public interface EnquiryControlInterface {

	/**
	 * addEnquiry adds an enquiry to the list of enquiries in MainController's
	 * attributes
	 * also tags the enquiry to the camp and user
	 * 
	 * @param enquiry the enquiry text to be added
	 * @param ownerid the owner of the enquiry, which should be a student
	 * @param campid  the camp the enquiry is about
	 * @return the enquiry id if successful, -1 if not
	 */
	int add(String enquiry);

	/**
	 * Edit an enquiry
	 * 
	 * @param enquiryid the enquiry id to be edited
	 * @param enquiry   the new enquiry text
	 * @return the enquiry id if successful, -1 if not
	 * @throws ControllerItemMissingException
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
	 * Overriden methods from Enquiry Controller
	 * Finalise an enquiry by setting the seen attribute to true
	 * so that enquiry can be replied to and points allocated to the responding
	 * committee member
	 * 
	 * @param enquiryid the enquiry id to be finalised
	 * @return true if successful, controller item missing exception if not
	 */
	Boolean finalise(int enquiryid) throws ControllerItemMissingException; // mark as uneditable

	/**
	 * Overriden methods from Enquiry Controller
	 * Check if an enquiry is editable
	 * If an enquiry has been finalised, (i.e.seen attribute is true), it is not
	 * editable
	 * 
	 * @param enquiryid the enquiry id to be checked
	 * @return true if editable, throws controller item missing exception if not
	 */
	Boolean isEditable(int enquiryid) throws ControllerItemMissingException; // check if can edit

	int saveReply(int enquiryid, String reply) throws ControllerItemMissingException;

	/**
	 * Overriden methods from Enquiry Controller
	 * Get the replies to an enquiry
	 * 
	 * @param enquiryid the enquiry id to be retrieved
	 * @return the replies to an enquiry if successful, controller item missing
	 */

	ArrayList<String> getReplies(int enquiryid) throws ControllerItemMissingException;

	Boolean delete(int enquiryid) throws ControllerItemMissingException;
}
