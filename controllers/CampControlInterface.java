package controllers;

import java.util.Map.Entry;

import entities.CampInfo;
import types.CampAspect;

/**
 * Represents the CampControlInterface to be implemented by CampController
 * 
 * @author Teo Xuan Ming
 * @version 1.1
 * @since 2021-11-24
 */
public interface CampControlInterface {
	/**
	 * Overriden method from CampControlInterface
	 * Create a new Camp Object based on the given CampInfo object
	 * Adds the Camp object to the camps ArrayList
	 * Writes the camps ArrayList to the camp_list.csv file
	 * 
	 * @param info the CampInfo object containing the Camp's information, @See
	 *             CampInfo record class for more details
	 * 
	 * @return the Camp's ID
	 */
	public int add(CampInfo info);

	/**
	 * Overriden method from CampControlInterface
	 * Deletes a Camp object from the camps ArrayList
	 * Writes the the new camps ArrayList to the camp_list.csv file
	 * 
	 * @param campid the Camp's ID
	 * 
	 * @return true if the Camp is successfully deleted, false otherwise
	 */
	public boolean delete(int campid);

	/**
	 * Overriden method from CampControlInterface
	 * Returns a CampInfo record object which contains information of the given its
	 * ID
	 * Check CampAspects.java for the list of attributes in CampInfo
	 * 
	 * @param campid the Camp's ID
	 * 
	 * @return the CampInfo object with the given ID, null if not found
	 */
	public CampInfo details(int campid);

	/**
	 * Overriden method from CampControlInterface
	 * Edit a single attribute of a Camp object
	 * Writes the the new camps ArrayList to the camp_list.csv file
	 * 
	 * @param campid the Camp's ID
	 * @param detail the attribute to be edited
	 * @return true if the attribute is successfully edited, false otherwise
	 */
	public boolean editDetails(int campid, Entry<CampAspect, ? extends Object> detail);

	/**
	 * Overriden method from CampControlInterface
	 * Checks if the Camp's attendee is full by comparing the Camp's attendee count
	 * to the Camp's attendee limit specified in the CampInfo object
	 * 
	 * @param campid the Camp's ID
	 * @return true if the Camp's attendee is full, false otherwise
	 */
	public boolean isAttendeeFull(int campid);

	/**
	 * Overriden method from CampControlInterface
	 * Checks if the Camp's committee is full
	 * 
	 * @param campid the Camp's ID
	 * @return true if the Camp's committee is full, false otherwise
	 */
	public boolean isCommitteeFull(int campid);
}
