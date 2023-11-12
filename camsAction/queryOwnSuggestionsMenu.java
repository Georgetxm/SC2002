package camsAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import cams.CamsInteraction;

import java.util.NoSuchElementException;

import controllers.Controller;
import controllers.SuggestionController;
import entities.Data;
import entities.UserInfoMissingException;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.CampAspects;
import types.Perms;
/**
 * Interaction that represents the action of offering users a list of on their list suggestions to choose from.
 * Based on predefined conditions they might be filtered by camp.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class queryOwnSuggestionsMenu extends UserMenu {
	@Override
	public final Boolean run() throws UserInfoMissingException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(!SuggestionController.class.isInstance(Data.get("Controller")))
			throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		int campid = -1;
		try {campid = GetData.CampID();}
		catch(MissingRequestedDataException e) {}
		if(campid>=0) ((Controller) control).FilterCamp(campid);
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		//Gets the dictionary of a user's suggestionid:suggestion, and makes it into a list. Except cos its Java, so there's a fuckton of casting.
		List<Entry<Integer, Entry<CampAspects, ? extends Object>>> suggestionlist = new ArrayList<>(((SuggestionController) ((SuggestionController)
				control).FilterUser(GetData.CurrentUser())).getSuggestions().entrySet());
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
			try {checkandrun(option);}
			catch(MissingRequestedDataException e) {
				System.out.println("Ran into an error. Please retry or return to main menu before retrying");
			}
		}
		return true;
	}

}
