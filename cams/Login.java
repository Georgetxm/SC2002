package cams;

import java.util.HashMap;
import java.util.Scanner;

import entities.User;

public final class Login {
	public static final String getCurrentUser(Scanner s,HashMap<String,User> userlist) {
		while(true) {
			System.out.println("User ID:");
			String userid = s.nextLine();
			System.out.println("Password:");
			if(userlist.containsKey(userid)&&userlist.get(userid).getPassword().equals(s.nextLine())) return userid;
			System.out.println(userlist.get(userid).getPassword());
		}
	}
}
