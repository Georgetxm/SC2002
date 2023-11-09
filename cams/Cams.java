package cams;

import java.util.EnumSet;
import java.util.Scanner;

import entities.Data;
import types.Perms;

public class Cams {

	public static void main(String[] args) throws Exception {
		EnumSet<Perms> defaultperms = EnumSet.of(Perms.DEFAULT, Perms.CREATE_CAMP);
		Data.put("UserPerms", defaultperms);
		Data.put("Scanner", new Scanner(System.in));
		Data.put("CurrentUser", "Armstrong");
		CamsInteraction.startmenu.run();
	}

}
