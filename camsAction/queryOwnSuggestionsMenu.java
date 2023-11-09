package camsAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import cams.CamsInteraction;
import controllers.SuggestionController;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.CampAspects;
import types.Perms;

public final class queryOwnSuggestionsMenu extends UserMenu {
	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(!SuggestionController.class.isInstance(data.get("Controller")))
			throw new Exception("Controller not able enough. Request Failed.");
		
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		//Gets the dictionary of a user's suggestionid:suggestion, and makes it into a list. Except cos its Java, so there's a fuckton of casting.
		List<Entry<Integer, Entry<CampAspects, ? extends Object>>> suggestionlist = new ArrayList<>(((SuggestionController) ((SuggestionController)
				data.get("Controller")).FilterUser(GetData.CurrentUser(data))).getSuggestions().entrySet());
		//Populates the MenuChoices with DefaultPerms, the suggestion text, and SingleSuggestionMenu
		for(Entry<Integer, Entry<CampAspects, ? extends Object>> entry : suggestionlist) {
			options.add(new MenuChoice(Perms.DEFAULT, entry.getValue().getKey().name()+":\n"+GetData.FromObject(entry.getValue().getValue()),CamsInteraction.SingleSuggestionMenu));
		}
		choices = options;
		int option = givechoices(data);
		data.put("CurrentItem", suggestionlist.get(option).getKey());
		System.out.println(choices.get(option).text());
		checkandrun(option,data);
		return true;
	}

}
