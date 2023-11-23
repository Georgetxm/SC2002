package controllers;

import java.util.Map.Entry;
import types.CampAspect;

public interface SuggestionControlInterface{
	/**
	 * Overriden methods from Suggestion Controller
	 * Creates a new suggestion, each suggestion contains only one aspect of
	 * CampInfo @see CampInfo
	 * i.e. Multiple suggestions are separate entries in the suggestions HashMap
	 * adds it to the list of suggestions in MainController's attributes
	 * also tags the suggestion to the camp and user
	 * 
	 * @param suggestion the suggestion aspect text to be added
	 * @param ownerid    the owner of the suggestion, which should be a student
	 */
	int add(Entry<CampAspect, ? extends Object> suggestion, String rationale, String ownerid, int campid)
			throws ControllerItemMissingException;
	int edit(int id, Entry<CampAspect, ? extends Object> edited, String rationale) throws ControllerItemMissingException;

	Entry<Entry<CampAspect, ? extends Object>, String> get(int suggestionid) throws ControllerItemMissingException;

	Boolean delete(int suggestionid) throws ControllerItemMissingException; // delete suggestion

	Boolean finalise(int suggestionid) throws ControllerItemMissingException; // mark as uneditable

	Boolean isEditable(int suggestionid) throws ControllerItemMissingException; // check if can edit

}
