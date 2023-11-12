package camsAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import cams.CamsInteraction;
import controllers.SuggestionController;
import entities.Data;
import entities.UserInfoMissingException;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.CampAspects;
import types.Perms;

public class queryAllSuggestionsMenu extends UserMenu {

	@Override
	public final Boolean run() throws UserInfoMissingException, MissingRequestedDataException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(!SuggestionController.class.isInstance(Data.get("Controller")))
			throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		//Gets the dictionary of a user's suggestionid:suggestion, and makes it into a list. Except cos its Java, so there's a fuckton of casting.
		List<Entry<Integer, Entry<CampAspects, ? extends Object>>> suggestionlist = new ArrayList<>(((SuggestionController) control).getSuggestions().entrySet());
		//Populates the MenuChoices with DefaultPerms, the suggestion text, and SingleSuggestionMenu
		for(Entry<Integer, Entry<CampAspects, ? extends Object>> entry : suggestionlist) {
			options.add(new MenuChoice(Perms.DEFAULT, entry.getValue().getKey().name()+":\n"+GetData.FromObject(entry.getValue().getValue()),CamsInteraction.SingleSuggestionMenu));
		}
		choices = options;
		while(true) {
			int option = givechoices();
			if(option<0) break;
			int suggestionid = suggestionlist.get(option).getKey();
			Data.put("CurrentItem", suggestionid);
			System.out.println(">>"+choices.get(option).text());
			System.out.println(((SuggestionController) control).getSuggestion(suggestionid).getValue());
			checkandrun(option);
		}
		return true;
	}

}
