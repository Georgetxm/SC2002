package controllers;

import java.util.HashMap;
import java.util.Map.Entry;
import types.CampAspect;

public interface SuggestionController extends Controller {
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
	int addSuggestion(Entry<CampAspect, ? extends Object> suggestion, String rationale, String ownerid, int campid)
			throws ControllerItemMissingException;

		
	int editSuggestion(int id, Entry<CampAspect, ? extends Object> edited, String rationale) throws ControllerItemMissingException;

	Entry<Entry<CampAspect, ? extends Object>, String> getSuggestion(int suggestionid) throws ControllerItemMissingException;

	HashMap<Integer, Entry<CampAspect, ? extends Object>> getSuggestions() throws ControllerParamsException, ControllerItemMissingException;

	Boolean deleteSuggestion(int suggestionid) throws ControllerItemMissingException; // delete suggestion

	Boolean finaliseSuggestion(int suggestionid) throws ControllerItemMissingException; // mark as uneditable

	Boolean isSuggestionEditable(int suggestionid) throws ControllerItemMissingException; // check if can edit

	String getSuggestionOwner(int suggestionid) throws ControllerItemMissingException; // get owner of suggestion

	int getHostCamp(int suggestionid) throws ControllerItemMissingException;

	public Controller FilterUser(String userid); //
	public Controller FilterCamp(int campid);

}
