package cams;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Scanner;

import controllers.MainController;
import entities.Camp;
import entities.Data;
import entities.User;
import types.Faculty;
import types.Perms;

public class Cams {

	public static void main(String[] args) throws Exception {
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
