package controllers;

import java.util.Map.Entry;

import types.CampAspects;

public interface SuggestionController {
	int save(Entry<CampAspects, ? extends Object> suggestion, String rationale, int owner);
	int edit(int id, Entry<CampAspects, ? extends Object> edited, String rationale);
	Entry<CampAspects, ? extends Object> get(int suggestionid);
	Boolean delete(int suggestionid); //delete suggestion
	Boolean finalise(int suggestionid); //mark as uneditable
	Boolean isEditable(int suggestionid); //check if can edit
	
	int getOwner(int suggestionid); //get owner of suggestion

}
