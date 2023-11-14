package cams;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Scanner;

import controllers.MainController;
import entities.Camp;
import entities.Data;
import entities.Student;
import entities.User;
import entities.UserInfoMissingException;
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
		EnumSet<Perms> defaultperms = EnumSet.of(Perms.CREATE_CAMP,Perms.EDIT_CAMP);
		User Armstrong = new Student();
		ArrayList<User> userlist = new ArrayList<User>();
		ArrayList<Camp> camplist = new ArrayList<Camp>();
		userlist.add(Armstrong);
		MainController control = new MainController(userlist,camplist);
		control.grantPerms("Armstrong", defaultperms);
		Data.put("Controller", control);
		Data.put("UserPerms", Armstrong.getPerms());
		Data.put("Scanner", new Scanner(System.in));
		Data.put("CurrentUser", "Armstrong");
		CamsInteraction.startmenu.run();
	}

}
