package cams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import types.Perms;

public class Cams {

	public static void main(String[] args) throws Exception {
		HashMap<String,Object> data = new HashMap<String,Object>();
		List<Perms> defaultperms = Arrays.asList(new Perms[]{Perms.DEFAULT});
		data.put("UserPerms", defaultperms);
		data.put("Scanner", new Scanner(System.in));
		CamsInteraction.startmenu.run(data);
	}

}
