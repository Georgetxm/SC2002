package camsAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import cams.CamsInteraction;
import controllers.CampController;
import entities.Data;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.Perms;

public class queryViewOwnCamps extends UserMenu {

	@Override
	public final Boolean run() throws Exception {
		if(!Data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(!CampController.class.isInstance(Data.get("Controller")))	
			throw new Exception("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		String userid = GetData.CurrentUser();
		Data.put("isViewingOwnCamps", true);
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		options.add(CamsInteraction.filterCampBy);
		List<Entry<Integer, String>> camplist = new ArrayList<Entry<Integer, String>>(((CampController) ((CampController)
				control).FilterUser(userid)).getCamps().entrySet());
		for(Entry<Integer, String> entry:camplist) {
			options.add(new MenuChoice(Perms.DEFAULT, entry.getValue(),CamsInteraction.OwnCampMenu));
		}
		choices = options;
		while(true) {
			int option = givechoices();
			if(option<0) break;
			if(option==0) {
				checkandrun(option);
				continue;
			}
			Data.put("CurrentCamp", camplist.get(option).getKey());
			System.out.println(">>"+choices.get(option).text());
			checkandrun(option);
		}
		Data.clear("isViewingOwnCamps");
		return true;
	}

}
