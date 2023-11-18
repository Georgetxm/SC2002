package camsAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import cams.CamsInteraction;
import controllers.SuggestionController;
import entities.Data;
import entities.UserInfoMissingException;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.CampAspect;
import types.Perms;
import controllers.Controller;
import controllers.ControllerItemMissingException;
import controllers.ControllerParamsException;
/**
 * Interaction that represents the action of offering users a list of suggestions from a single camp to choose from.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public class queryAllSuggestionsMenu extends UserMenu {
	/**
	 * Provides users with a menu of suggestions from a given camp to choose from.
	 * @return true if all requests succeed and false if otherwise
	 * @throws UserInfoMissingException if user id is invalid
	 * @throws MissingRequestedDataException if campid or suggestion id is invalid
	 */
	@Override
	public final Boolean run() throws UserInfoMissingException, MissingRequestedDataException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(!SuggestionController.class.isInstance(Data.get("Controller")))
			throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Controller control = (Controller) Data.get("Controller");

		while(true) {
			int campid = GetData.CampID();
			
			List<MenuChoice> options = new ArrayList<MenuChoice>();
			//Gets the dictionary of a user's suggestionid:suggestion, and makes it into a list. Except cos its Java, so there's a fuckton of casting.
			List<Entry<Integer, Entry<CampAspect, ? extends Object>>> suggestionlist = null;
			HashMap<Integer, Entry<CampAspect, ? extends Object>> suggestionset;
			try {
				suggestionset = ((SuggestionController) ((SuggestionController) control).FilterCamp(campid)).getSuggestions();
			} catch (ControllerParamsException | ControllerItemMissingException e) {
				throw new MissingRequestedDataException("Camp id is invalid");
			}
			if(suggestionset!=null) {
				suggestionlist = new ArrayList<>(suggestionset.entrySet());
				//Populates the MenuChoices with DefaultPerms, the suggestion text, and SingleSuggestionMenu
				for(Entry<Integer, Entry<CampAspect, ? extends Object>> entry : suggestionlist)
					options.add(new MenuChoice(
							Perms.DEFAULT, 
							entry.getValue().getKey().name()+":\n"+GetData.FromObject(entry.getValue().getValue()),
							CamsInteraction.SingleSuggestionMenu)
					);
			}
			choices = options;
			int option = givechoices();
			if(option<0) break;
			int suggestionid = suggestionlist.get(option).getKey();
			Data.put("CurrentItem", suggestionid);
			System.out.println(">>"+choices.get(option).text());
			try {
				System.out.println(((SuggestionController) control).getSuggestion(suggestionid).getValue());
				((SuggestionController) control).finaliseSuggestion(suggestionid);
			} catch (ControllerItemMissingException e) {
				throw new MissingRequestedDataException("Suggestion id is invalid");
			}
			try {checkandrun(option);}
			catch(MissingRequestedDataException e) {
				System.out.println("Ran into an error. Please retry or return to main menu before retrying");
			}
		}
		return true;
	}

}
