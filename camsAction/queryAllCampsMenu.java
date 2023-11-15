package camsAction;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import cams.CamsInteraction;
import controllers.CampController;
import controllers.UserController;
import controllers.Controller;
import entities.Data;
import entities.UserInfoMissingException;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.CampAspects;
import types.Faculty;
import types.Perms;
/**
 * Interaction that represents the action of giving the user a list of camps to choose from, not filtered by any camp aspect.
 * However, they can be filtered by user depending on past user data..
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public class queryAllCampsMenu extends UserMenu {
	
	/**
	 *Represents a menu displaying various camps for the user to choose, and an option to filter that list.
	 *<p>
	 *If the user had previously selected to view only the camps linked to them, it will be filtered to reflect that.
	 *If the user does not have the view all camps perms, only visible camps belonging to his faculty will be shown.
	 *@return true if all controller requests succeed
	 *@throws MissingRequestedDataException if the ViewingOwnCamps tag is malformed.
	 *@throws UserInfoMissingException if the user or their perms cannot be identified.
	 */
	@Override
	public final Boolean run() throws MissingRequestedDataException, UserInfoMissingException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(!CampController.class.isInstance(Data.get("Controller")))	
			throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Controller control = (Controller) Data.get("Controller");
		
		String userid = GetData.CurrentUser();
		Faculty userfaculty = ((UserController) control).getUserFaculty(userid);
		@SuppressWarnings("unchecked")
		EnumSet<Perms> userperm=(EnumSet<Perms>) Data.get("UserPerms");
		
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		options.add(CamsInteraction.filterCampBy);
		
		if(GetData.isViewingOwnCamps()) control.FilterUser(userid);
		if(!userperm.contains(Perms.VIEW_EVERY_CAMP)) //filter should return Controller. This may lead to issues.
			((CampController) control).filterVisible().FilterAspect(new HashMap.SimpleEntry<CampAspects, Object>(CampAspects.USERGROUP,userfaculty));
		List<Entry<Integer, String>> camplist = null;
		HashMap<Integer, String> campset = ((CampController) control).getCamps();
		if(campset!=null) {
			camplist = new ArrayList<Entry<Integer, String>>(campset.entrySet());
			for(Entry<Integer, String> entry:camplist)
				options.add(new MenuChoice(
						Perms.DEFAULT, 
						entry.getValue(),
						((CampController) control).getCampAttendees(entry.getKey()).contains(userid) ?
							CamsInteraction.OwnCampMenu : 
							CamsInteraction.OtherCampMenu)
				);
		}
		choices = options;
		
		while(true) {
			int option = givechoices();
			if(option<0) break;
			if(option==0) {
				try {checkandrun(option);}
				catch(MissingRequestedDataException e) {
					System.out.println("Ran into an error. Please retry or return to main menu before retrying");
				}
				continue;
			}
			Data.put("CurrentCamp", camplist.get(option-1).getKey());
			System.out.println(">>"+choices.get(option).text());
			try {checkandrun(option);}
			catch(MissingRequestedDataException e) {
				System.out.println("Ran into an error. Please retry or return to main menu before retrying");
			}
		}
		if(GetData.isViewingOwnCamps()) Data.clear("isViewingOwnCamps");
		return true;
	}

}
