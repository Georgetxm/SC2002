package camsAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import cams.CamsInteraction;
import controllers.Controller;
import controllers.ControllerItemMissingException;
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
		List<Serializable> suggestionlist = null;
		HashSet<Serializable> suggestionset;
		if(ownerid!=null) control.Directory().sync().with(entities.User.class, ownerid);
		if(campid!=null) control.Directory().sync().with(entities.Camp.class, campid);
		suggestionset = control.Directory().sync().get(entities.Suggestion.class);
		//Populates the MenuChoices with DefaultPerms, the suggestion text, and SingleSuggestionMenu
		if(suggestionset!=null) {
			suggestionlist = new ArrayList<Serializable>(suggestionset);
			for(Serializable entryid : suggestionlist) {
				Entry<Entry<CampAspect, ? extends Object>, String> entry = null;
				try {
					entry = control.Suggestion().get((int) entryid);
				} catch (ControllerItemMissingException e) {
					throw new MissingRequestedDataException("Enquiry selected is invalid");
				}
				options.add(new MenuChoice(Perms.DEFAULT, 
						entry.getKey().getKey().name()+":\n"+GetData.FromObject(entry.getKey().getValue()),
						CamsInteraction.SingleSuggestionMenu((int) entryid)));
			}
				
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
			HashSet<Serializable> campset = null;
			campset = control.Directory().sync().with(entities.User.class, currentuser).get(entities.Camp.class);
			return campset.contains(campid)?CamsInteraction.OwnCampMenu(campid, currentuser):CamsInteraction.OtherCampMenu(campid,currentuser);
		};
		int suggestionid = suggestionlist.get(option).getKey();
		System.out.println(">>"+choices.get(option).text());
		try {
			System.out.println(control.Suggestion().get(suggestionid));
			control.Suggestion().finalise(suggestionid);
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
