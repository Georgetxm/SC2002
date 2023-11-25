package camsAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import cams.CamsInteraction;
import controllers.Controller;
import controllers.ControllerItemMissingException;
import entities.UserInfoMissingException;
import interactions.Interaction;
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
public final class queryEnquriesMenu extends UserMenu {
	private List<Serializable> enquirylist = null;
	/**
	 * Represents a menu of the user's own enquiries for them to choose.
	 * <p>
	 * These enquiries can either be for a given camp, or across camps depending on whether the user has selected a camp.
	 * @return true if all requests succeed, false if otherwise
	 * @throws entities.UserInfoMissingException if the current usrid cannot be found
	 * @throws MissingRequestedDataException if the user cannot have enquiries, or the enquiry selected has an invalid id
	 */@Override
	protected void populate(String currentuser, Scanner s, Controller control){
		if(ownerid!=null) control.Directory().sync().with(entities.User.class, ownerid);
		if(campid!=null) control.Directory().sync().with(entities.Camp.class, campid);
		
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		//Gets the dictionary of a user's controllerid:suggestion, and makes it into a list. Except cos its Java, so there's a fuckton of casting.

		HashSet<Serializable> enquiryset=null;
		enquiryset = control.Directory().sync().get(entities.Enquiry.class);
		if(enquiryset!=null) {
			enquirylist = new ArrayList<Serializable>(enquiryset);
			//Populates the MenuChoices with DefaultPerms, the suggestion text, and SingleSuggestionMenu
			for(Serializable entry : enquirylist) 
				options.add(new MenuChoice(Perms.DEFAULT, control.Enquiry().get((int) entry),CamsInteraction.SingleEnquiryMenu((int) entry)));
		}
		choices = options;
	}

	@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException, MissingRequestedDataException {
		populate(currentuser, s, control);
		int option = givechoices(currentuser, s, control);
		if(option<0) {
			if(this.campid==null) return CamsInteraction.startmenu(currentuser);
			HashSet<Serializable> campset = null;
			campset = control.Directory().sync().with(entities.User.class, currentuser).get(entities.Camp.class);
			next = campset.contains(campid)?CamsInteraction.OwnCampMenu(campid, currentuser):CamsInteraction.OtherCampMenu(campid,currentuser);
		}
		else{
			int enquiryid = (int) enquirylist.get(option);
			System.out.println(">>"+choices.get(option).text());
			try {
				for(String reply:control.Enquiry().getReplies(enquiryid)) System.out.println(reply);
				control.Enquiry().finalise(enquiryid);
			} catch (ControllerItemMissingException e) {
				throw new MissingRequestedDataException("Enquiry id is invalid");
			}
			next = CamsInteraction.SingleEnquiryMenu(enquiryid).withenquiry(enquiryid);
		}
		
		if(userid!=null) next = next.withuser(userid);
		if(campid!=null) next = next.withcamp(campid);
		if(filters!=null) next = next.withfilter(filters);
		if(this.ownerid!=null) next = next.withowner(this.ownerid);
		return next;
	}
}
