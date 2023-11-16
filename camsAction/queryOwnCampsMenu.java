package camsAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import cams.CamsInteraction;
import controllers.CampController;
import controllers.ControllerItemMissingException;
import entities.Data;
import entities.UserInfoMissingException;
import interactions.MenuChoice;
import interactions.UserMenu;
import types.Perms;
/**
 * Interaction that represents the action of offering users a list of the camps in their list to choose from.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public class queryOwnCampsMenu extends UserMenu {
	/**
	 * Represents a menu of the user's own camps for them to choose
	 * <p>
	 * Sets the ViewingOwnCamps tag for all child menus and removes it for all parent menus
	 * @return true if all requests succeed, false if otherwise
	 * @throws UserInfoMissingException if the current usrid cannot be found
	 * @throws MissingRequestedDataException if the user cannot have camps, or the camp selected has an invalid id
	 */
	@Override
	public final Boolean run() throws UserInfoMissingException, MissingRequestedDataException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(!CampController.class.isInstance(Data.get("Controller")))	
			throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		String userid = GetData.CurrentUser();
		Data.put("isViewingOwnCamps", true);
		List<MenuChoice> options = new ArrayList<MenuChoice>();
		options.add(CamsInteraction.filterCampBy);
		ArrayList<Entry<Integer, String>> camplist = new ArrayList<Entry<Integer, String>>();
		HashMap<Integer, String> campset = null;
		try {
			campset = ((CampController) ((CampController) control).FilterUser(userid)).getCamps();
		} catch (ControllerItemMissingException e) {
			throw new MissingRequestedDataException("Invalid user id, cannot find owned camps");
		}
		if(campset!=null) {
				camplist = new ArrayList<Entry<Integer, String>>(campset.entrySet());
			for(Entry<Integer, String> entry:camplist) {
				options.add(new MenuChoice(Perms.DEFAULT, entry.getValue(),CamsInteraction.OwnCampMenu));
			}
		}
		choices = options;
		while(true) {
			int option = givechoices();
			if(option<0) break;
			if(option==0) {
				try {checkandrun(option);}
				catch(MissingRequestedDataException e) {
					System.out.println("Ran into an error. Please retry or return to main menu before retrying");
				}
				continue;
			}
			Data.put("CurrentCamp", camplist.get(option).getKey());
			System.out.println(">>"+choices.get(option).text());
			try {checkandrun(option);}
			catch(MissingRequestedDataException e) {
				System.out.println("Ran into an error. Please retry or return to main menu before retrying");
			}
		}
		Data.clear("isViewingOwnCamps");
		return true;
	}

}
