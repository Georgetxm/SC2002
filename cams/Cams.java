package cams;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Scanner;

import controllers.MainController;
import entities.Camp;
import entities.Data;
import entities.User;
import entities.UserInfoMissingException;
import types.Faculty;
import types.Perms;
/**
 * The Java App class that contains main
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public class Cams {
	/**
	 * The first function called. Calls required bootstrapping functions to load in data and start the app.
	 * @param args is the default arguments for main()
	 * @throws UserInfoMissingException if user does not have enough valid information (i.e. userid, permissions)
	 */
	public static void main(String[] args) throws UserInfoMissingException {
		EnumSet<Perms> defaultperms = EnumSet.of(Perms.DEFAULT, Perms.CREATE_CAMP);
		User Armstrong = new User("Armstrong","",Faculty.ADM,defaultperms);
		ArrayList<User> userlist = new ArrayList<User>();
		ArrayList<Camp> camplist = new ArrayList<Camp>();
		userlist.add(Armstrong);
		Data.put("Controller", new MainController(userlist,camplist));
		Data.put("UserPerms", defaultperms);
		Data.put("Scanner", new Scanner(System.in));
		Data.put("CurrentUser", "Armstrong");
		CamsInteraction.startmenu.run();
	}

}
