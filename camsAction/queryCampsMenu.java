package camsAction;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import cams.CamsInteraction;
import controllers.CampControlInterface;
import controllers.UserControlInterface;
import controllers.Controller;
import controllers.ControllerItemMissingException;
import entities.UserInfoMissingException;
import interactions.Interaction;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.CampAspect;
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
public class queryCampsMenu extends UserMenu {
	private List<Entry<Integer, String>> camplist = null;
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
	protected final void populate(String currentuser, Scanner s, Controller control) throws UserInfoMissingException{
		Faculty userfaculty = ((UserControlInterface) control).getUserFaculty(currentuser);
		EnumSet<Perms> userperm= ((UserControlInterface) control).grantPerms(currentuser,EnumSet.noneOf(Perms.class));
		
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		options.add(CamsInteraction.filterCampBy);
		if(userid!=null) {
			((CampControlInterface) control).FilterUser(userid);
			System.out.println("administered user filter");
		}
		if(filters!=null) {
			List<Entry<CampAspect, ? extends Object>> filterlist = new ArrayList<Entry<CampAspect, ? extends Object>>(filters.entrySet());
			for(Entry<CampAspect, ? extends Object> filter:filterlist)
				((CampControlInterface) control).FilterAspect(filter);
		}
		if(!userperm.contains(Perms.VIEW_EVERY_CAMP)) //filter should return Controller. This may lead to issues.
			((CampControlInterface) control).filterVisible().FilterAspect(new HashMap.SimpleEntry<CampAspect, Object>(CampAspect.USERGROUP,userfaculty));
		HashMap<Integer, String> campset = null;
		try {
			campset = ((CampControlInterface) control).getCamps();
		} catch (ControllerItemMissingException e) {
			System.out.println("Cannot find user or invalid filter");
		}
		if(campset!=null) {
			camplist = new ArrayList<Entry<Integer, String>>(campset.entrySet());
			for(Entry<Integer, String> entry:camplist) {
				try {
					campset = ((CampControlInterface) ((CampControlInterface) control).FilterUser(currentuser)).getCamps();
				} catch (ControllerItemMissingException e) {
					throw new UserInfoMissingException("Cannot find user information");
				}
				options.add(
					new MenuChoice(Perms.DEFAULT, entry.getValue(), 
						(campset!=null&&campset.keySet().contains(entry.getKey()))?
							CamsInteraction.OwnCampMenu(entry.getKey(),currentuser).withcamp(entry.getKey()):
							CamsInteraction.OtherCampMenu(entry.getKey(),currentuser).withcamp(entry.getKey())
				));
			}
		}
		choices = options;
	}

	@Override
	public
	final Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException{
		populate(currentuser, s, control);
		int choice = givechoices(currentuser,s,control);
		if(choice>=0) {
			next = choices.get(choice).action();
			if(this.filters!=null) next = next.withfilter(filters);
			if(this.userid!=null) next = next.withuser(userid);
		}
		else if(this.filters!=null) {
			next = new queryCampsMenu();
			if(this.userid!=null) next=next.withuser(userid);
		}
		else next = CamsInteraction.startmenu(currentuser);
		return next;
	}

}
