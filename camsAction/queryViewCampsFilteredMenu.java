package camsAction;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cams.CamsInteraction;
import controllers.CampController;
import controllers.Controller;
import controllers.UserController;
import entities.Data;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.CampAspects;
import types.Faculty;
import types.Perms;

public final class queryViewCampsFilteredMenu extends UserMenu {

	@Override
	public final Boolean run() throws Exception {
		if(!Data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(!CampController.class.isInstance(Data.get("Controller")))	
			throw new Exception("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		String userid = GetData.CurrentUser();
		Faculty userfaculty = ((UserController) control).getUserFaculty(userid);
		@SuppressWarnings("unchecked")
		EnumSet<Perms> userperm=(EnumSet<Perms>) Data.get("UserPerms");
		
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		options.add(CamsInteraction.filterCampBy);
		options.add(CamsInteraction.removeCampFilter);
		
		if(GetData.isViewingOwnCamps()) ((Controller) control).FilterUser(userid);
		if(!userperm.contains(Perms.VIEW_EVERY_CAMP)) 
			((CampController) control).filterVisible().FilterAspect(new HashMap.SimpleEntry<CampAspects, Object>(CampAspects.USERGROUP,userfaculty));
		List<Entry<CampAspects, ? extends Object>> filterlist = new ArrayList<Entry<CampAspects, ? extends Object>>(GetData.Filter().entrySet());
		for(Entry<CampAspects, ? extends Object> filter:filterlist)
			((Controller) control).FilterAspect(filter);
		List<Entry<Integer, String>> camplist = new ArrayList<Entry<Integer, String>>(((CampController) control).getCamps().entrySet());
		for(Entry<Integer, String> entry:camplist) {
			options.add(new MenuChoice(Perms.DEFAULT, entry.getValue(),new doNothing()));
		}
		choices = options;
		while(true) {
			int option = givechoices();
			if(option<0) break;
			if(option==0) {
				checkandrun(option);
				continue;
			}
			if(option==1) {
				Data.clear("Filter");
				checkandrun(option);
				continue;
			}
			Data.put("CurrentCamp", camplist.get(option).getKey());
			System.out.println(">>"+choices.get(option).text());
			checkandrun(option);
			continue;
		}
		return true;
	}

}
