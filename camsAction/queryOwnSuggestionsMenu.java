package camsAction;

import java.util.HashMap;

import controllers.SuggestionController;
import interactions.UserMenu;

public class queryOwnSuggestionsMenu extends UserMenu {
	@Override
	protected Object run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(!SuggestionController.class.isInstance(data.get("Controller")))
			throw new Exception("Controller not able enough. Request Failed.");
		
		((SuggestionController) ((SuggestionController)data.get("Controller")).FilterUser(GetData.CurrentUser(data))).getSuggestions();
		
		return null;
	}

}
