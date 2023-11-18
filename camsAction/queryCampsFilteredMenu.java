package camsAction;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import cams.CamsInteraction;
import controllers.CampController;
import controllers.ControllerItemMissingException;
import controllers.UserController;
import entities.Data;
import entities.UserInfoMissingException;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.CampAspects;
import types.Faculty;
import types.Perms;
/**
 * Interaction that represents the action of offering users a list of camps filtered by some predefined aspect(s).
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class queryCampsFilteredMenu extends UserMenu {
	/**
	 * Represents a menu of user camps for them to choose after applying filters as necessary
	 * <p>
	 * If ViewingOwnCamps tag is true, only shows the user their own camps
	 * Provides an option to remove filters
	 * Checks whether the user is part of the camp or not and sends it to OwnCampMenu or OtherCampMenu as appropriate
	 * @return true if all requests succeed, false if otherwise
	 * @throws entities.UserInfoMissingException if the current usrid cannot be found
	 * @throws MissingRequestedDataException if the user cannot have suggestions, or the suggestion selected has an invalid id
	 */
	@Override
	public final Boolean run() throws MissingRequestedDataException, UserInfoMissingException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(!CampController.class.isInstance(Data.get("Controller")))	
			throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");

		while(true) {
			String userid = GetData.CurrentUser();
			Faculty userfaculty = ((UserController) control).getUserFaculty(userid);
			EnumSet<Perms> userperm= ((UserController) control).grantPerms(userid,EnumSet.noneOf(Perms.class));
			
			List<MenuChoice> options = new ArrayList<MenuChoice>();
			options.add(CamsInteraction.filterCampBy);
			options.add(CamsInteraction.removeCampFilter);
			
			if(GetData.isViewingOwnCamps()) ((CampController) control).FilterUser(userid);
			if(!userperm.contains(Perms.VIEW_EVERY_CAMP)) 
				((CampController) control).filterVisible().FilterAspect(new HashMap.SimpleEntry<CampAspects, Object>(CampAspects.USERGROUP,userfaculty));
			List<Entry<CampAspects, ? extends Object>> filterlist = new ArrayList<Entry<CampAspects, ? extends Object>>(GetData.Filter().entrySet());
			for(Entry<CampAspects, ? extends Object> filter:filterlist)
				((CampController) control).FilterAspect(filter);
			ArrayList<Entry<Integer, String>> camplist = new ArrayList<Entry<Integer, String>>();
			HashMap<Integer, String> campset;
			try {
				campset = ((CampController) control).getCamps();
			} catch (ControllerItemMissingException e) {
				throw new MissingRequestedDataException("Invalid user id, cannot find owned camps.");
			}
			if(campset!=null) {
				camplist = new ArrayList<>(campset.entrySet());
				for(Entry<Integer, String> entry:camplist) {
					options.add(new MenuChoice(
							Perms.DEFAULT, 
							entry.getValue(),
							((CampController) control).getCampAttendees(entry.getKey()).contains(userid) ?
								CamsInteraction.OwnCampMenu : 
								CamsInteraction.OtherCampMenu));
				}
			}
			choices = options;
			int option = givechoices();
			if(option<0) break;
			if(option==0) {
				try {checkandrun(option);}
				catch(MissingRequestedDataException e) {
					System.out.println("Ran into an error. Please retry or return to main menu before retrying");
				}
				continue;
			}
			if(option==1) {
				Data.clear("Filter");
				try {checkandrun(option);}
				catch(MissingRequestedDataException e) {
					System.out.println("Ran into an error. Please retry or return to main menu before retrying");
				}
				continue;
			}
			Data.put("CurrentCamp", camplist.get(option-2).getKey());
			System.out.println(">>"+choices.get(option).text());
			try {checkandrun(option);}
			catch(MissingRequestedDataException e) {
				System.out.println("Ran into an error. Please retry or return to main menu before retrying");
			}
			continue;
		}
		return true;
	}

}
