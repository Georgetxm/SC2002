package cams;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Scanner;

import types.Perms;

public class Cams {

	public static void main(String[] args) throws Exception {
		HashMap<String,Object> data = new HashMap<String,Object>();
		EnumSet<Perms> defaultperms = EnumSet.of(Perms.DEFAULT);
		data.put("UserPerms", defaultperms);
		data.put("Scanner", new Scanner(System.in));
		CamsInteraction.startmenu.run(data);
	}

}
