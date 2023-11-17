package camsAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import entities.Data;
import entities.UserInfoMissingException;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.CampAspects;
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
	 * Offers users a menu of filters to choose from, and prompts the user to enter the required value.
	 * @return true if all requests succeed, false if otherwise
	 * @throws UserInfoMissingException if if one of its child functions detect user information is incomplete (e.g. ill-formatted or missing userid, permissions
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final Boolean run() throws UserInfoMissingException {
		while(true) {
			List<MenuChoice> options = new ArrayList<MenuChoice>();
			for(CampAspects aspects:CampAspects.values()) {
				options.add(new MenuChoice(Perms.DEFAULT, "Filter by "+aspects.name(),new queryCampsFilteredMenu()));
			}
			choices=options;
			int option = givechoices();
			if(option<0) break;
			Scanner s = getScanner();
			Entry<CampAspects, ? extends Object> edited;
			switch(CampAspects.values()[option]) { //Depending on the aspect chosen, request data from user
			case NAME:					edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampName(s); 		break;
			case DATE: 					edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampDate(s); 		break;
			case REGISTRATION_DEADLINE: edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampRegisterDate(s);	break;
			case USERGROUP:				edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampFaculty(s);		break;
			case LOCATION: 				edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampLocation(s); 	break;
			case SLOTS: 				edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampSlots(s); 		break;
			case DESCRIPTION: 			edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampDescription(s); 	break;
			case COMMITTEESLOTS:		edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampComitteeSlots(s);break;
			case STAFFIC:	 			edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampStaffIC(s);	 	break;
			default: System.out.println("Cannot search by this field"); return false;
			}
			HashMap<CampAspects, ?extends Object> existingfilter = null;
			try {existingfilter =  GetData.Filter();}
			catch(MissingRequestedDataException e) {
				HashMap<CampAspects,Object> newfilter = new HashMap<CampAspects,Object>();
				newfilter.put(edited.getKey(),edited.getValue());
				Data.put("Filter", newfilter);
				System.out.println(choices.get(option).text()+":\n"+GetData.FromObject(edited.getValue()));
				try {checkandrun(option);}
				catch(MissingRequestedDataException e1) {
					System.out.println("Ran into an error. Please retry or return to main menu before retrying");
				}
				continue;
			}
			((HashMap<CampAspects,Object>)existingfilter).put(edited.getKey(),edited.getValue());
			Data.put("Filter",existingfilter);
			System.out.println("Filtering by "+choices.get(option).text());
			try {checkandrun(option);}
			catch(MissingRequestedDataException e) {
				System.out.println("Ran into an error. Please retry or return to main menu before retrying");
			}
			continue;
		}
		return true;
	}

}
