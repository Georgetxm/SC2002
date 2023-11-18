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
	 * Offers users a menu of filters to choose from, and prompts the user to enter the required value.
	 * @return true if all requests succeed, false if otherwise
	 * @throws UserInfoMissingException if if one of its child functions detect user information is incomplete (e.g. ill-formatted or missing userid, permissions
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final Boolean run() throws UserInfoMissingException {
		while(true) {
			List<MenuChoice> options = new ArrayList<MenuChoice>();
			for(CampAspect aspects:CampAspect.values()) {
				options.add(new MenuChoice(Perms.DEFAULT, "Filter by "+aspects.name(),new queryCampsFilteredMenu()));
			}
			choices=options;
			int option = givechoices();
			if(option<0) break;
			Scanner s = getScanner();
			Entry<CampAspect, ? extends Object> edited;
			switch(CampAspect.values()[option]) { //Depending on the aspect chosen, request data from user
			case NAME:					edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampName(s); 		break;
			case DATE: 					edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampDate(s); 		break;
			case REGISTRATION_DEADLINE: edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampRegisterDate(s);	break;
			case USERGROUP:				edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampFaculty(s);		break;
			case LOCATION: 				edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampLocation(s); 	break;
			case SLOTS: 				edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampSlots(s); 		break;
			case DESCRIPTION: 			edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampDescription(s); 	break;
			case COMMITTEESLOTS:		edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampComitteeSlots(s);break;
			case STAFFIC:	 			edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampStaffIC(s);	 	break;
			default: System.out.println("Cannot search by this field"); return false;
			}
			HashMap<CampAspect, ?extends Object> existingfilter = null;
			try {existingfilter =  GetData.Filter();}
			catch(MissingRequestedDataException e) {
				HashMap<CampAspect,Object> newfilter = new HashMap<CampAspect,Object>();
				newfilter.put(edited.getKey(),edited.getValue());
				Data.put("Filter", newfilter);
				System.out.println(choices.get(option).text()+":\n"+GetData.FromObject(edited.getValue()));
				try {checkandrun(option);}
				catch(MissingRequestedDataException e1) {
					System.out.println("Ran into an error. Please retry or return to main menu before retrying");
				}
				continue;
			}
			((HashMap<CampAspect,Object>)existingfilter).put(edited.getKey(),edited.getValue());
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
