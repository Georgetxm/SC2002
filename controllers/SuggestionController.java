package controllers;

import java.util.Map.Entry;

import types.CampAspect;

public class SuggestionController implements SuggestionControlInterface {

	@Override
	public int add(Entry<CampAspect, ? extends Object> suggestion, String rationale)
			throws ControllerItemMissingException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(int id, Entry<CampAspect, ? extends Object> edited, String rationale)
			throws ControllerItemMissingException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Entry<Entry<CampAspect, ? extends Object>, String> get(int suggestionid)
			throws ControllerItemMissingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(int suggestionid) throws ControllerItemMissingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean finalise(int suggestionid) throws ControllerItemMissingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isEditable(int suggestionid) throws ControllerItemMissingException {
		// TODO Auto-generated method stub
		return null;
	}

}
