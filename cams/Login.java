package cams;

import java.util.HashMap;
import java.util.Scanner;

import entities.User;

/**
 * Class that deals with logging in
 */
public final class Login {
	/**
	 * Takes in a bunch of users and logs the user in
	 * @param s universal scanner
	 * @param userlist list of users extracted from the file
	 * @return userid userid of current user
	 */
	public static final String getCurrentUser(Scanner s,HashMap<String,User> userlist) {
		while(true) {
			System.out.println("User ID:");
			String userid = s.nextLine();
			System.out.println("Password:");
			String attempt1=s.nextLine();
			if(attempt1=="") attempt1 = s.nextLine();
			if(userlist.containsKey(userid)&&userlist.get(userid).getPassword().equals(attempt1)) return userid;
			else System.out.println("Invalid password or id");
		}
	}
}
