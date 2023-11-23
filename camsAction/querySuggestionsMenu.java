package camsAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cams.CamsInteraction;

import java.util.Scanner;

import controllers.CampControlInterface;
import controllers.Controller;
import controllers.ControllerItemMissingException;
import controllers.ControllerParamsException;
import controllers.SuggestionControlInterface;
import entities.UserInfoMissingException;
import interactions.Interaction;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.CampAspect;
import types.Perms;
/**
 * Interaction that represents the action of offering users a list of on their list suggestions to choose from.
 * Based on predefined conditions they might be filtered by camp.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class querySuggestionsMenu extends UserMenu {
	private List<Entry<Integer, Entry<CampAspect, ? extends Object>>> suggestionlist = null;
	/**
	 * Represents a menu of the user's own suggestions for them to choose from.
	 * <p>
	 * These suggestions can either be of one camp or across camps depending on whether the user has selected a camp beforehand.
	 * @return true if all requests succeed, false if otherwise
	 * @throws entities.UserInfoMissingException if the current usrid cannot be found
	 * @throws MissingRequestedDataException if the user cannot have suggestions, or the suggestion selected has an invalid id
	 */

	@Override
	protected void populate(String currentuser, Scanner s, Controller control)
			throws MissingRequestedDataException, UserInfoMissingException {
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		//Gets the dictionary of a user's suggestionid:suggestion, and makes it into a list. Except cos its Java, so there's a fuckton of casting.
		List<Entry<Integer, Entry<CampAspect, ? extends Object>>> suggestionlist = null;
		HashMap<Integer,Entry<CampAspect,? extends Object>> suggestionset;
		if(ownerid!=null) ((SuggestionControlInterface) control).FilterUser(this.ownerid);
		if(campid!=null) ((SuggestionControlInterface) control).FilterCamp(this.campid);
		try {
			suggestionset = ((SuggestionControlInterface)control).getSuggestions();
		} catch (ControllerParamsException | ControllerItemMissingException e) {
			throw new MissingRequestedDataException("User info does not tally with one that has suggestions");
		}
		//Populates the MenuChoices with DefaultPerms, the suggestion text, and SingleSuggestionMenu
		if(suggestionset!=null) {
			suggestionlist = new ArrayList<>(suggestionset.entrySet());
			for(Entry<Integer, Entry<CampAspect, ? extends Object>> entry : suggestionlist)
				options.add(new MenuChoice(Perms.DEFAULT, 
						entry.getValue().getKey().name()+":\n"+GetData.FromObject(entry.getValue().getValue()),
						CamsInteraction.SingleSuggestionMenu(entry.getKey())));
		}
		choices = options;
	}

	@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException, MissingRequestedDataException {
		populate(currentuser, s, control);
		int option = givechoices(currentuser, s, control);
		if(option<0){
			if(this.campid==null) return CamsInteraction.startmenu(currentuser);
			HashMap<Integer, String> campset = null;
			try {
				campset = ((CampControlInterface) ((CampControlInterface) control).FilterUser(userid)).getCamps();
			} catch (ControllerItemMissingException e) {
				throw new UserInfoMissingException("Cannot find user information");
			}
			return campset.keySet().contains(campid)?CamsInteraction.OwnCampMenu(campid, currentuser):CamsInteraction.OtherCampMenu(campid,currentuser);
		};
		int suggestionid = suggestionlist.get(option).getKey();
		System.out.println(">>"+choices.get(option).text());
		try {
			System.out.println(((SuggestionControlInterface) control).get(suggestionid).getValue());
			((SuggestionControlInterface) control).finalise(suggestionid);
		} catch (ControllerItemMissingException e) {
			throw new MissingRequestedDataException("Suggestion is invalid");
		}
		if(userid!=null) next = next.withuser(userid);
		if(campid!=null) next = next.withcamp(campid);
		if(filters!=null) next = next.withfilter(filters);
		if(this.ownerid!=null) next = next.withowner(this.ownerid);
		return next.withsuggestion(suggestionid);
	}

}
