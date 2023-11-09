package controllers;

import java.util.Map.Entry;

import types.CampAspects;

public interface SuggestionController {
	int addSuggestion(Entry<CampAspects, ? extends Object> suggestion, String rationale, int ownerid, int campid);
	int editSuggestion(int id, Entry<CampAspects, ? extends Object> edited, String rationale);
	Entry<Entry<CampAspects, ? extends Object>,String> getSuggestion(int suggestionid);
	Entry<CampAspects, ? extends Object>[] getSuggestions();
	SuggestionController FilterCamp(int campid); //adds user filter
	SuggestionController FilterUser(int userid); //adds camp filter
	Boolean deleteSuggestion(int suggestionid); //delete suggestion
	Boolean finaliseSuggestion(int suggestionid); //mark as uneditable
	Boolean isSuggestionEditable(int suggestionid); //check if can edit
	
	int getOwner(int suggestionid); //get owner of suggestion
	int getHostCamp(int suggestionid);

}
