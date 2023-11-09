package camsAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import controllers.SuggestionController;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.CampAspects;
import types.Perms;

public class queryOwnSuggestionsMenu extends UserMenu {
	@Override
	protected Object run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(!SuggestionController.class.isInstance(data.get("Controller")))
			throw new Exception("Controller not able enough. Request Failed.");
		
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		Entry<CampAspects, ? extends Object>[] suggestionlist = ((SuggestionController) ((SuggestionController)data.get("Controller")).FilterUser(GetData.CurrentUser(data))).getSuggestions();
		for(Entry<CampAspects, ? extends Object> entry : suggestionlist) {
			options.add(new MenuChoice(Perms.DEFAULT, Camp))
		}
		
		return null;
	}

}
