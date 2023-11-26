package controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import cams.ReadWriteEnquiryCSV;
import entities.Enquiry;

/**
 * Represents the EnquiryController class
 * Holds a list of Enquiry objects
 * Holds all methods to interface with the Enquiry object
 * Created and held by the Controller ENUM
 * 
 * @author Teo Xuan Ming
 * @version 1.1
 * @since 2021-11-24
 */
public class EnquiryController implements EnquiryControlInterface {
	/**
	 * enquiries is the list of Enquiry objects
	 */
	private HashMap<Integer, Enquiry> enquiries;

	/**
	 * Constructor for Enquiry Controller
	 * 
	 * @param enquiryList the list of Enquiry objects
	 */
	public EnquiryController(HashMap<Integer, Enquiry> enquiryList) {
		this.enquiries = enquiryList;
	}

	/**
	 * Returns an Enquiry object given its ID
	 * 
	 * @param enquiryId the Enquiry's ID
	 * @return the Enquiry object with the given ID, null if not found
	 */
	public Enquiry findEnquiryById(int enquiryId) {
		if (enquiries.containsKey(enquiryId)) {
			return enquiries.get(enquiryId);
		}
		return null;
	}

	/**
	 * Overriden methods from EnquiryControllerInterface
	 * adds an enquiry to the list of enquiries in the EnquiryController
	 * 
	 * @param enquiry the enquiry text to be added
	 * @return the enquiry id if successful
	 */
	@Override
	public int add(String enquiry) {
		Enquiry newEnquiry = new Enquiry(enquiry, false, LocalDate.now());
		this.enquiries.put(newEnquiry.getEnquiryId(), newEnquiry);
		ReadWriteEnquiryCSV.writeEnquiryCSV(enquiries, "lists/enquiry_list.csv");
		return newEnquiry.getEnquiryId();
	}

	/**
	 * Overriden methods from EnquiryControllerInterface
	 * Edit an enquiry text
	 * Updates the last update date of the enquiry
	 * 
	 * @param enquiryid the enquiry id to be edited
	 * @param enquiry   the new enquiry text
	 * @return the enquiry id if successful, -1 if not
	 * @throws ControllerItemMissingException if enquiry cannot be found
	 */
	@Override
	public int edit(int enquiryid, String enquiry) throws ControllerItemMissingException {
		Enquiry enquiryToEdit = findEnquiryById(enquiryid);
		if (enquiryToEdit == null) {
			throw new ControllerItemMissingException("Enquiry does not exist");
		}
		if (enquiryToEdit.setEnquiryBody(enquiry)) {
			enquiryToEdit.updateLastUpdateDate(LocalDate.now());
			ReadWriteEnquiryCSV.writeEnquiryCSV(enquiries, "lists/enquiry_list.csv");
			return enquiryToEdit.getEnquiryId();
		} else {
			return -1;
		}
	}

	/**
	 * Overriden methods from EnquiryControllerInterface
	 * Get an enquiry text
	 * 
	 * @param enquiryid the enquiry id to be retrieved
	 * @return the enquiry text if successful, null if not
	 */
	@Override
	public String get(int enquiryid) {
		if (findEnquiryById(enquiryid) == null) {
			return null;
		}

		return findEnquiryById(enquiryid).getEnquiryBody();
	}

	/**
	 * Overriden methods from EnquiryControllerInterface
	 * Finalise an enquiry by setting the seen attribute to true
	 * so that enquiry can be replied to
	 * 
	 * @param enquiryid the enquiry id to be finalised
	 * @return true if successful, controller item missing exception if not
	 * @throws ControllerItemMissingException if enquiry cannot be found
	 */
	@Override
	public Boolean finalise(int enquiryid) throws ControllerItemMissingException {
		if (!enquiries.containsKey(enquiryid)) {
			throw new ControllerItemMissingException("Enquiry does not exist");
		}
		Enquiry enquiry = findEnquiryById(enquiryid);
		enquiry.setSeen(true);
		ReadWriteEnquiryCSV.writeEnquiryCSV(enquiries, "lists/enquiry_list.csv");
		return true;
	}

	/**
	 * Overriden methods from EnquiryControllerInterface
	 * Check if an enquiry is editable
	 * If an enquiry has been finalised, (i.e.seen attribute is true), it is not
	 * editable
	 * 
	 * @param enquiryid the enquiry id to be checked
	 * @return true if editable, throws controller item missing exception if not
	 * @throws ControllerItemMissingException if enquiry cannot be found
	 */
	@Override
	public Boolean isEditable(int enquiryid) throws ControllerItemMissingException {
		if (!enquiries.containsKey(enquiryid)) {
			throw new ControllerItemMissingException("Enquiry does not exist");
		}
		Enquiry enquiry = findEnquiryById(enquiryid);
		return !enquiry.isSeen();
	}

	/**
	 * Overriden methods from EnquiryControllerInterface
	 * Save the reply to an enquiry under the replies attribute
	 * 
	 * @param enquiryid the enquiry id to be replied to
	 * @param reply     the reply text
	 * @return the enquiry id if successful, -1 if not
	 * @throws ControllerItemMissingException if enquiry cannot be found
	 */
	@Override
	public int saveReply(int enquiryid, String reply) throws ControllerItemMissingException {
		if (!enquiries.containsKey(enquiryid)) {
			throw new ControllerItemMissingException("Enquiry does not exist");
		}
		Enquiry enquiry = findEnquiryById(enquiryid);
		if (enquiry.addReply(reply)) {
			ReadWriteEnquiryCSV.writeEnquiryCSV(enquiries, "lists/enquiry_list.csv");
			return enquiryid;
		} else {
			return -1;
		}
	}

	/**
	 * Overriden methods from EnquiryControllerInterface
	 * Get the replies to an enquiry
	 * 
	 * @param enquiryid the enquiry id to be retrieved
	 * @return the replies to an enquiry if successful, controller item missing
	 * @throws ControllerItemMissingException if enquiry cannot be found
	 */
	@Override
	public ArrayList<String> getReplies(int enquiryid) throws ControllerItemMissingException {
		if (!enquiries.containsKey(enquiryid)) {
			throw new ControllerItemMissingException("Enquiry does not exist");
		}
		Enquiry enquiry = findEnquiryById(enquiryid);
		return enquiry.getReplies();
	}

	/**
	 * Overriden methods from EnquiryControllerInterface
	 * Delete an enquiry
	 * Removes the enquiry id from the camp and user attribute
	 * 
	 * @param enquiryid the enquiry id to be deleted
	 * @return true if successful, controller item missing exception if not
	 * @throws ControllerItemMissingException if enquiry cannot be found
	 */
	@Override
	public Boolean delete(int enquiryid) throws ControllerItemMissingException {
		Enquiry enquiry = findEnquiryById(enquiryid);
		if (enquiry == null) {
			throw new ControllerItemMissingException("Enquiry does not exist");
		}

		enquiries.remove(enquiryid);
		ReadWriteEnquiryCSV.writeEnquiryCSV(enquiries, "lists/enquiry_list.csv");
		return true;
	}

}
