package camsAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import controllers.Controller;
import entities.UserInfoMissingException;
import interactions.Interaction;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.CampAspect;
import types.Perms;
/**
 * Interaction that represents the action of offering users a list of aspects to filter camps by
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public class queryFilterCampByMenu extends UserMenu {

	/**
	 * Populates the menu with filters to choose from
	 */@Override
	protected final void populate(String currentuser, Scanner s, Controller control){
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		for(CampAspect aspects:CampAspect.values())
			options.add(new MenuChoice(Perms.DEFAULT, "Filter by "+aspects.name(),new queryCampsMenu()));
		choices=options;
		next = new queryCampsMenu();
	}

	/**
	 * Gives users choices then prompts the user to enter the required value.
	 * @return query camp menu with appropriate filters
	 * @throws UserInfoMissingException if if one of its child functions detect user information is incomplete (e.g. ill-formatted or missing userid, permissions
	 */
	@Override
	public
	final Interaction run(String currentuser, Scanner s, Controller control) throws UserInfoMissingException{
		populate(currentuser, s, control);
		int option = givechoices(currentuser, s, control);
		if(option<0) return this.userid!=null?next.withuser(userid):next;
		Entry<CampAspect, ? extends Object> edited;
		switch(CampAspect.values()[option]) { //Depending on the aspect chosen, request data from user
		case NAME:					edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampName(s); 			break;
		case DATE: 					edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampDate(s); 			break;
		case REGISTRATION_DEADLINE: edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampRegisterDate(s);	break;
		case USERGROUP:				edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampFaculty(s);		break;
		case LOCATION: 				edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampLocation(s); 		break;
		case SLOTS: 				edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampSlots(s); 		break;
		case DESCRIPTION: 			edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampDescription(s); 	break;
		case COMMITTEESLOTS:		edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampComitteeSlots(s);	break;
		case STAFFIC:	 			edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampStaffIC(s);	 	break;
		default: System.out.println("Cannot search by this field"); return this;
		}
		if(filters==null) filters = new HashMap<CampAspect,Object>();
		filters.put(edited.getKey(),edited.getValue());
		next.withfilter(filters);
		return this.userid!=null?next.withuser(userid):next;
	}

}
