package camsAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import controllers.CampController;
import entities.Data;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.Perms;

public class queryViewAllCampsMenu extends UserMenu {

	@Override
	public final Boolean run() throws Exception {
		if(!Data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(!CampController.class.isInstance(Data.get("Controller")))	
			throw new Exception("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		List<Entry<Integer, String>> camplist = new ArrayList<Entry<Integer, String>>(((CampController) control).getCamps().entrySet());
		for(Entry<Integer, String> entry:camplist) {
			options.add(new MenuChoice(Perms.DEFAULT, entry.getValue(),new doNothing()));
		}
		choices = options;
		int option = givechoices();
		Data.put("CurrentCamp", camplist.get(option).getKey());
		System.out.println(choices.get(option).text());
		checkandrun(option);
		return true;
	}

}
