package camsAction;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

import cams.CamsInteraction;
import controllers.Controller;
import controllers.EnquiryController;
import controllers.SuggestionController;
import entities.Data;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.Perms;

public final class queryAllEnquiriesMenu extends UserMenu {

	@Override
	public final Boolean run() throws Exception {
		if(!Data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(!SuggestionController.class.isInstance(Data.get("Controller")))
			throw new Exception("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		int campid = -1;
		try {campid = GetData.CampID();}
		catch(NoSuchElementException e) {}
		if(campid>=0) ((Controller) control).FilterCamp(campid);
		
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		//Gets the dictionary of a user's controllerid:suggestion, and makes it into a list. Except cos its Java, so there's a fuckton of casting.
		List<Entry<Integer, String>> enquirylist = new ArrayList<>(((EnquiryController) control).getEnquiries().entrySet());
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
