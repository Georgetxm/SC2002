package camsAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;

import entities.Data;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.CampAspects;
import types.Perms;

public class queryFilterCampByMenu extends UserMenu {

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final Boolean run() throws Exception {
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		for(CampAspects aspects:CampAspects.values()) {
			options.add(new MenuChoice(Perms.DEFAULT, "Filter by "+aspects.name(),new queryViewAllCampsFilteredMenu()));
		}
		choices=options;
		while(true) {
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
			HashMap<CampAspects, ?extends Object> existingfilter;
			try {existingfilter =  GetData.Filter();}
			catch(NoSuchElementException e) {
				Data.put("Filter", edited);
				System.out.println("Filtering by "+choices.get(option).text());
				checkandrun(option);
				continue;
			}
			Data.put("Filter",((HashMap<CampAspects,Object>)existingfilter).put(edited.getKey(),edited.getValue()));
			System.out.println("Filtering by "+choices.get(option).text());
			checkandrun(option);
		}
		return true;
	}

}
