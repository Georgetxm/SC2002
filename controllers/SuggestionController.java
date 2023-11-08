package controllers;

import java.util.Map.Entry;

import types.CampAspects;

public interface SuggestionController {
	int saveSuggestion(Entry<CampAspects, ? extends Object> suggestion, String rationale, int owner);
	int editSuggestion(int id, Entry<CampAspects, ? extends Object> edited, String rationale);
	Entry<CampAspects, ? extends Object> getSuggestion(int suggestionid);
	Boolean deleteSuggestion(int suggestionid); //delete suggestion
	Boolean finaliseSuggestion(int suggestionid); //mark as uneditable
	Boolean isSuggestionEditable(int suggestionid); //check if can edit
	
	int getOwner(int suggestionid); //get owner of suggestion

}
