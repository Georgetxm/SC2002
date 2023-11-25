package controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import cams.ReadWriteEnquiryCSV;
import entities.Enquiry;

public class EnquiryController implements EnquiryControlInterface {
	private HashMap<Integer, Enquiry> enquiries;

	/**
	 * Constructor for Enquiry Controller
	 * 
	 * @param enquiries
	 * @return
	 */
	public EnquiryController(HashMap<Integer, Enquiry> enquiryList) {
		this.enquiries = enquiryList;
	}

	/**
	 * Returns an Enquiry object given its ID
	 * 
	 * @param enquiryId
	 * @return the Enquiry object with the given ID, null if not found
	 */
	public Enquiry findEnquiryById(int enquiryId) {
		if (enquiries.containsKey(enquiryId)) {
			return enquiries.get(enquiryId);
		}
		return null;
	}

	/**
	 * Overriden methods from Enquiry Controller Interface
	 * addEnquiry adds an enquiry to the list of enquiries in MainController's
	 * attributes
	 * also tags the enquiry to the camp and user
	 * 
	 * @param enquiry the enquiry text to be added
	 * @param ownerid the owner of the enquiry, which should be a student
	 * @param campid  the camp the enquiry is about
	 * @return the enquiry id if successful, -1 if not
	 */
	@Override
	public int add(String enquiry) {
		Enquiry newEnquiry = new Enquiry(enquiry, false, LocalDate.now());
		this.enquiries.put(newEnquiry.getEnquiryId(), newEnquiry);
		ReadWriteEnquiryCSV.writeEnquiryCSV(enquiries, "lists/enquiry_list.csv");
		return newEnquiry.getEnquiryId();
	}

	/**
	 * Overriden methods from Enquiry Controller Interface
	 * Edit an enquiry text
	 * Updates the last update date of the enquiry
	 * 
	 * @param enquiryid the enquiry id to be edited
	 * @param enquiry   the new enquiry text
	 * @return the enquiry id if successful, -1 if not
	 * @throws ControllerItemMissingException
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
	 * Overriden methods from Enquiry Controller Interface
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
	 * Overriden methods from Enquiry Controller Interface
	 * Finalise an enquiry by setting the seen attribute to true
	 * so that enquiry can be replied to and points allocated to the responding
	 * committee member
	 * 
	 * @param enquiryid the enquiry id to be finalised
	 * @return true if successful, controller item missing exception if not
	 * @throws ControllerItemMissingException
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
	 * Overriden methods from Enquiry Controller Interface
	 * Check if an enquiry is editable
	 * If an enquiry has been finalised, (i.e.seen attribute is true), it is not
	 * editable
	 * 
	 * @param enquiryid the enquiry id to be checked
	 * @return true if editable, throws controller item missing exception if not
	 * @throws ControllerItemMissingException
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
	 * Overriden methods from Enquiry Controller Interface
	 * Save the reply to an enquiry under the replies attribute
	 * 
	 * @param enquiryid the enquiry id to be replied to
	 * @param reply     the reply text
	 * @return the enquiry id if successful, -1 if not
	 * @throws ControllerItemMissingException
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
	 * Overriden methods from Enquiry Controller Interface
	 * Get the replies to an enquiry
	 * 
	 * @param enquiryid the enquiry id to be retrieved
	 * @return the replies to an enquiry if successful, controller item missing
	 * @throws ControllerItemMissingException
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
	 * Overriden methods from Enquiry Controller Interface
	 * Delete an enquiry
	 * Removes the enquiry id from the camp and user attribute
	 * 
	 * @param enquiryid the enquiry id to be deleted
	 * @return true if successful, controller item missing exception if not
	 * @throws ControllerItemMissingException
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
