package camsAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import cams.CamsInteraction;
import controllers.Controller;
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
	 *Populates the menu choices with camps for the user to choose, and an option to filter that list.
	 *<p>
	 *If the user had previously selected to view only the camps linked to them, it will be filtered to reflect that.
	 *If the user does not have the view all camps perms, only visible camps belonging to his faculty or whole NTU will be shown.
	 *If the user had previously set some filters, the camps will be filtered to reflect that
	 */
	@Override
	protected final void populate(String currentuser, Scanner s, Controller control){
		Faculty userfaculty = control.User().getUserFaculty(currentuser);
		EnumSet<Perms> userperm= control.User().grantPerms(currentuser,EnumSet.noneOf(Perms.class));
		HashSet<Serializable> usercamps = control.Directory().sync().with(entities.User.class, currentuser).get(entities.Camp.class);
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		options.add(CamsInteraction.filterCampBy);
		if(userid!=null) {
			control.Directory().with(entities.User.class,userid);
		}
		Set<Serializable> viewingset=new HashSet<Serializable>();
		if(!userperm.contains(Perms.VIEW_EVERY_CAMP)) {
			control.Directory().sync().withvisibility();
		}
		if(filters!=null) {
			List<Entry<CampAspect, ? extends Object>> filterlist = new ArrayList<Entry<CampAspect, ? extends Object>>(filters.entrySet());
			viewingset = new HashSet<Serializable>(control.Directory().get(entities.Camp.class));
			for (Iterator<Serializable> it = viewingset.iterator(); it.hasNext();) {
			    Serializable element = it.next();
			    Faculty campfaculty = (Faculty) control.Camp().details((int) element).info().get(CampAspect.USERGROUP);
			    if(
			    	!userperm.contains(Perms.VIEW_EVERY_CAMP)&&
			    	(campfaculty!=userfaculty||campfaculty!=Faculty.WHOLE_NTU)	
			    ) {
			    	it.remove();break;
			    }
			    else for(Entry<CampAspect, ? extends Object> filter:filterlist)
			    	if(control.Camp().details((int) element).info().get(filter.getKey())!=filter.getValue()){
			    		it.remove();
			    		break;
			    	}
			}
		}
		else viewingset = control.Directory().get(entities.Camp.class);
		HashMap<Integer,String> campset = new HashMap<Integer,String>();
		for(Serializable id: viewingset)
			campset.put((Integer)id, (String) control.Camp().details((int) id).info().get(CampAspect.NAME));
		if(campset!=null) {
			camplist = new ArrayList<Entry<Integer, String>>(campset.entrySet());
			for(Entry<Integer, String> entry:camplist) {
				options.add(
					new MenuChoice(Perms.DEFAULT, entry.getValue(), 
						(usercamps!=null&&usercamps.contains(entry.getKey()))?
							CamsInteraction.OwnCampMenu(entry.getKey(),currentuser).withcamp(entry.getKey()):
							CamsInteraction.OtherCampMenu(entry.getKey(),currentuser).withcamp(entry.getKey())
				));
			}
		}
		choices = options;
	}

	/**
	 *Gives users the choices.
	 *@return The appropriate single camp menu with camp, user and filter tags if a camp is chosen, or
	 *query camp menu without filters, i.e. only preserving the user tag if the user previously added filters but pressed back, or
	 *start menu if the user did not previously add filters but pressed back.
	 */
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
