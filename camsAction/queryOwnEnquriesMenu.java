package camsAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

import cams.CamsInteraction;
import controllers.Controller;
import controllers.ControllerItemMissingException;
import controllers.ControllerParamsException;
import controllers.EnquiryController;
import controllers.SuggestionController;
import entities.Data;
import entities.UserInfoMissingException;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.Perms;
/**
 * Interaction that represents the action of offering users a list of enquiries on their list to choose from.
 * Based on predefined conditions, it may be filtered by camp
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class queryOwnEnquriesMenu extends UserMenu {

	
	@Override
	public final Boolean run() throws UserInfoMissingException, MissingRequestedDataException, ControllerItemMissingException, ControllerParamsException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(!SuggestionController.class.isInstance(Data.get("Controller")))
			throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		int campid = -1;
		try {campid = GetData.CampID();}
		catch(MissingRequestedDataException e) {}
		if(campid>=0) ((Controller) control).FilterCamp(campid);
		
		String userid = GetData.CurrentUser();
		((Controller) control).FilterUser(userid);
		
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		//Gets the dictionary of a user's controllerid:suggestion, and makes it into a list. Except cos its Java, so there's a fuckton of casting.
		List<Entry<Integer, String>> enquirylist = null;
		HashMap<Integer, String> enquiryset;
		try {
			enquiryset = ((EnquiryController) control).getEnquiries();
		} catch (ControllerParamsException | ControllerItemMissingException e) {
			throw new MissingRequestedDataException("Enquiry filters are malformed");
		}
		if(enquiryset!=null) {
			enquirylist = new ArrayList<>(enquiryset.entrySet());
			//Populates the MenuChoices with DefaultPerms, the suggestion text, and SingleSuggestionMenu
			for(Entry<Integer, String> entry : enquirylist) 
				options.add(new MenuChoice(Perms.DEFAULT, entry.getValue(),CamsInteraction.SingleEnquiryMenu));
		}
		choices = options;
		while(true) {
			int option = givechoices();
			if(option<0) break;
			int enquiryid = enquirylist.get(option).getKey();
			Data.put("CurrentItem", enquiryid);
			System.out.println(">>"+choices.get(option).text());
			try {
				for(String reply:((EnquiryController) control).getReplies(enquiryid)) System.out.println(reply);
			} catch (ControllerItemMissingException e) {
				throw new MissingRequestedDataException("Enquiry id is invalid");
			}
			try {checkandrun(option);}
			catch(MissingRequestedDataException e) {
				System.out.println("Ran into an error. Please retry or return to main menu before retrying");
			}
		}
		return true;
	}
}
