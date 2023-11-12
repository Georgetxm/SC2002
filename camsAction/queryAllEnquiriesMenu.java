package camsAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import cams.CamsInteraction;
import controllers.EnquiryController;
import controllers.SuggestionController;
import entities.Data;
import entities.UserInfoMissingException;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.Perms;
/**
 * Interaction that represents the action of offering the user a list of enquiries to choose from.
 * These enquiries may be filtered by camp, depending on past data.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class queryAllEnquiriesMenu extends UserMenu {

	@Override
	public final Boolean run() throws UserInfoMissingException, MissingRequestedDataException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(!SuggestionController.class.isInstance(Data.get("Controller")))
			throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		int campid;
		try {
			campid = GetData.CampID();
		} catch (MissingRequestedDataException e) {
			System.out.println("Missing camp id! Maybe restart from the main menu?");
			return false;
		}
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		//Gets the dictionary of a user's controllerid:suggestion, and makes it into a list. Except cos its Java, so there's a fuckton of casting.
		List<Entry<Integer, String>> enquirylist = new ArrayList<>(((EnquiryController) ((EnquiryController) control).FilterCamp(campid)).getEnquiries().entrySet());
		//Populates the MenuChoices with DefaultPerms, the suggestion text, and SingleSuggestionMenu
		for(Entry<Integer, String> entry : enquirylist) {
			options.add(new MenuChoice(Perms.DEFAULT, entry.getValue(),CamsInteraction.SingleEnquiryMenu));
		}
		choices = options;
		while(true) {
			int option = givechoices();
			if(option<0) break;
			int enquiryid = enquirylist.get(option).getKey();
			Data.put("CurrentItem", enquiryid);
			System.out.println(">>"+choices.get(option).text());
			checkandrun(option);
		}
		return true;
	}

}
