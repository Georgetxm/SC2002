package controllers;

import java.util.ArrayList;
import java.util.HashMap;

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
public interface EnquiryController extends Controller {

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
	int addEnquiry(String enquiry, String ownerid, int campid);

	/**
	 * Edit an enquiry
	 * 
	 * @param enquiryid the enquiry id to be edited
	 * @param enquiry   the new enquiry text
	 * @return the enquiry id if successful, -1 if not
	 * @throws ControllerItemMissingException
	 */
	int editEnquiry(int enquiryid, String enquiry) throws ControllerItemMissingException;

	/**
	 * Get an enquiry text based on enquiry id
	 * 
	 * @param enquiryid the enquiry id to be retrieved
	 * @return the enquiry text if successful, null if not
	 */
	String getEnquiry(int enquiryid);

	/**
	 * Get all enquiries that fulfill the filter specified from the filter methods
	 * 
	 * @see FilterCamp(int campid)
	 * @see FilterUser(String userid)
	 *      Can be filtered by campid and userid
	 *      Clears the filter after getting the enquiries
	 * 
	 * @return a HashMap of enquiry id and enquiry text
	 * @throws ControllerParamsException
	 * @throws ControllerItemMissingException
	 */
	HashMap<Integer, String> getEnquiries() throws ControllerParamsException, ControllerItemMissingException;

	/**
	 * Overriden methods from Enquiry Controller
	 * Finalise an enquiry by setting the seen attribute to true
	 * so that enquiry can be replied to and points allocated to the responding
	 * committee member
	 * 
	 * @param enquiryid the enquiry id to be finalised
	 * @return true if successful, controller item missing exception if not
	 */
	Boolean deleteEnquiry(int enquiryid) throws ControllerItemMissingException; // delete suggestion

	/**
	 * Overriden methods from Enquiry Controller
	 * Finalise an enquiry by setting the seen attribute to true
	 * so that enquiry can be replied to and points allocated to the responding
	 * committee member
	 * 
	 * @param enquiryid the enquiry id to be finalised
	 * @return true if successful, controller item missing exception if not
	 */
	Boolean finaliseEnquiry(int enquiryid) throws ControllerItemMissingException; // mark as uneditable

	/**
	 * Overriden methods from Enquiry Controller
	 * Check if an enquiry is editable
	 * If an enquiry has been finalised, (i.e.seen attribute is true), it is not
	 * editable
	 * 
	 * @param enquiryid the enquiry id to be checked
	 * @return true if editable, throws controller item missing exception if not
	 */
	Boolean isEnquiryEditable(int enquiryid) throws ControllerItemMissingException; // check if can edit

	/**
	 * Overriden methods from Enquiry Controller
	 * Get the owner of an enquiry
	 * 
	 * @param enquiryid the enquiry id to be retrieved
	 * @return the owner of an enquiry if successful, controller item missing
	 */
	String getEnquiryOwner(int enquiryid) throws ControllerItemMissingException;

	/**
	 * Overriden methods from Enquiry Controller
	 * Get the camp of an enquiry
	 * 
	 * @param enquiryid the enquiry id to be retrieved
	 * @return the camp of an enquiry if successful, controller item missing
	 */
	int getEnquiryHostCamp(int enquiryid) throws ControllerItemMissingException;

	/**
	 * Overriden methods from Enquiry Controller
	 * Save the reply to an enquiry under the replies attribute
	 * 
	 * @param enquiryid the enquiry id to be replied to
	 * @param reply     the reply text
	 * @return the enquiry id if successful, -1 if not
	 */
	int saveReply(int enquiryid, String reply) throws ControllerItemMissingException;

	/**
	 * Overriden methods from Enquiry Controller
	 * Get the replies to an enquiry
	 * 
	 * @param enquiryid the enquiry id to be retrieved
	 * @return the replies to an enquiry if successful, controller item missing
	 */

	ArrayList<String> getReplies(int enquiryid) throws ControllerItemMissingException;

	public Controller FilterUser(String userid); //
	public Controller FilterCamp(int campid); //adds user filter
}
