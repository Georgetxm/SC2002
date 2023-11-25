package controllers;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map.Entry;

import cams.ReadWriteSuggestionCSV;
import entities.Suggestion;
import types.CampAspect;

public class SuggestionController implements SuggestionControlInterface {

	private HashMap<Integer, Suggestion> suggestions;

	/**
	 * Constructor for Suggestion Controller
	 * 
	 * @param suggestions
	 * @return
	 */
	public SuggestionController() {
		HashMap<Integer, Suggestion> suggestionList = new HashMap<Integer, Suggestion>();
		ReadWriteSuggestionCSV.readSuggestionCSV(suggestionList, "lists/suggestion_list.csv");
		this.suggestions = suggestionList;
	}

	/**
	 * Returns a Suggestion object given its ID
	 * 
	 * @param suggestionId
	 * @return the Suggestion object with the given ID, null if not found
	 */

	public Suggestion findSuggestionById(int suggestionId) {
		if (suggestions.containsKey(suggestionId)) {
			return suggestions.get(suggestionId);
		}
		return null;
	}

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
	 * @throws ControllerItemMissingException
	 */
	@Override
	public int add(Entry<CampAspect, ? extends Object> suggestion, String rationale)
			throws ControllerItemMissingException {
		Suggestion newSuggestion = new Suggestion(rationale, suggestion, LocalDate.now());
		this.suggestions.put(newSuggestion.getSuggestionId(), newSuggestion);
		ReadWriteSuggestionCSV.writeSuggestionCSV(suggestions, "lists/suggestion_list.csv");

		return newSuggestion.getSuggestionId();
	}

	/**
	 * Overriden methods from Suggestion Controller
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
	@Override
	public int edit(int id, Entry<CampAspect, ? extends Object> edited, String rationale)
			throws ControllerItemMissingException {
		Suggestion suggestionToBeEdited = findSuggestionById(id);
		if (suggestionToBeEdited == null) {
			throw new ControllerItemMissingException("Suggestion does not exist");
		}

		if (suggestionToBeEdited.setSuggestionAspect(edited) && suggestionToBeEdited.setRationale(rationale)) {
			suggestionToBeEdited.setLastUpdatedDate(LocalDate.now());
			ReadWriteSuggestionCSV.writeSuggestionCSV(suggestions, "lists/suggestion_list.csv");
			return suggestionToBeEdited.getSuggestionId();
		} else {
			throw new ControllerItemMissingException("Suggestion not edited");
		}
	}

	/**
	 * Gets a suggestion by its id
	 * 
	 * @param suggestionid the suggestion id to be retrieved
	 * @return the suggestion object if successful, controller item missing
	 *         exception if not
	 * @throws ControllerItemMissingException
	 * 
	 */
	@Override
	public Entry<Entry<CampAspect, ? extends Object>, String> get(int suggestionid)
			throws ControllerItemMissingException {
		Suggestion suggestion = findSuggestionById(suggestionid);
		if (suggestion == null) {
			throw new ControllerItemMissingException("Suggestion does not exist");
		} else {
			Entry<Entry<CampAspect, ? extends Object>, String> suggestionEntry = new AbstractMap.SimpleEntry<Entry<CampAspect, ? extends Object>, String>(
					suggestion.getSuggestionAspect(), suggestion.getRationale());
			return suggestionEntry;
		}
	}

	/**
	 * Overriden methods from Suggestion Controller Interface
	 * Delete a suggestion based on the suggestion id
	 * 
	 * @param suggestionid the suggestion id to be deleted
	 * @return true if successful, controller item missing exception if not
	 */
	@Override
	public Boolean delete(int suggestionid) throws ControllerItemMissingException {
		Suggestion suggestionToDelete = findSuggestionById(suggestionid);
		if (suggestionToDelete == null) {
			throw new ControllerItemMissingException("Suggestion does not exist");
		}

		suggestions.remove(suggestionid);
		ReadWriteSuggestionCSV.writeSuggestionCSV(suggestions, "lists/suggestion_list.csv");

		return true;
	}

	/**
	 * Overriden methods from Suggestion Controller
	 * Sets a suggestion to be accepted so that the suggestion is finalised
	 */
	@Override
	public Boolean finalise(int suggestionid) throws ControllerItemMissingException {
		Suggestion suggestion = findSuggestionById(suggestionid);
		if (suggestion == null) {
			throw new ControllerItemMissingException("Suggestion does not exist");
		}
		suggestion.setAccepted(true);
		ReadWriteSuggestionCSV.writeSuggestionCSV(suggestions, "lists/suggestion_list.csv");

		return null;
	}

	/**
	 * Overriden methods from Suggestion Controller Interface
	 * Checks if a suggestion is editable based on the suggestion's accepted status
	 * If the suggestion is accepted, it is not editable
	 * 
	 * @param suggestionid the suggestion id to be checked
	 * @return true if editable, controller item missing exception if not
	 */
	@Override
	public Boolean isEditable(int suggestionid) throws ControllerItemMissingException {
		Suggestion suggestion = findSuggestionById(suggestionid);
		if (suggestion == null) {
			throw new ControllerItemMissingException("Suggestion does not exist");
		}
		return !suggestion.isAccepted();
	}

}
