package controllers;

import java.util.Map.Entry;
import types.CampAspect;

/**
 * Represents the SuggestionControlInterface to be implemented by
 * SuggestionController
 * 
 * @author Teo Xuan Ming
 * @version 1.1
 * @since 2021-11-24
 */
public interface SuggestionControlInterface {
	/**
	 * Creates a new suggestion, each suggestion contains only one aspect of
	 * CampInfo @see CampInfo
	 * i.e. Multiple suggestions are separate entries in the suggestions HashMap
	 * adds it to the list of suggestions in MainController's attributes
	 * also tags the suggestion to the camp and user
	 * 
	 * @param suggestion the suggestion aspect text to be added
	 * @param rationale  the rationale for the suggestion
	 * @return the suggestion id if successful
	 * @throws ControllerItemMissingException
	 */
	int add(Entry<CampAspect, ? extends Object> suggestion, String rationale)
			throws ControllerItemMissingException;

	/**
	 * Edit a suggestion based on the suggestion id
	 * Updates the suggestion aspect and rationale
	 * Updates the last updated date
	 * 
	 * @param id        the suggestion id to be edited
	 * @param edited    the new suggestion aspect object
	 * @param rationale the new rationale
	 * @return the suggestion id if successful throws controller item missing
	 *         exception if not
	 * @throws ControllerItemMissingException
	 */
	int edit(int id, Entry<CampAspect, ? extends Object> edited, String rationale)
			throws ControllerItemMissingException;

	/**
	 * Gets a suggestion by its id
	 * 
	 * @param suggestionid the suggestion id to be retrieved
	 * @return the suggestion object if successful, controller item missing
	 *         exception if not
	 * @throws ControllerItemMissingException
	 * 
	 */
	Entry<Entry<CampAspect, ? extends Object>, String> get(int suggestionid) throws ControllerItemMissingException;

	/**
	 * Delete a suggestion based on the suggestion id
	 * 
	 * @param suggestionid the suggestion id to be deleted
	 * @return true if successful, controller item missing exception if not
	 */
	Boolean delete(int suggestionid) throws ControllerItemMissingException; // delete suggestion

	/**
	 * Sets a suggestion to be accepted so that the suggestion is finalised
	 * 
	 * @param suggestionid the suggestion id to be finalised
	 * @return true if successful, controller item missing exception if not
	 */
	Boolean finalise(int suggestionid) throws ControllerItemMissingException; // mark as uneditable

	/**
	 * Checks if a suggestion is editable based on the suggestion's accepted status
	 * If the suggestion is accepted, it is not editable
	 * 
	 * @param suggestionid the suggestion id to be checked
	 * @return true if editable, controller item missing exception if not
	 */
	Boolean isEditable(int suggestionid) throws ControllerItemMissingException; // check if can edit

}
