package cams;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeMap;

import javax.tools.JavaFileManager.Location;

import controllers.MainController;
import entities.Camp;
import entities.CampInfo;
import entities.Data;
import entities.Student;
import entities.User;
import entities.UserInfoMissingException;
import types.CampAspects;
import types.Faculty;
import types.Perms;

/**
 * The Java App class that contains main
 * 
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public class Cams {
	/**
	 * The first function called. Calls required bootstrapping functions to load in
	 * data and start the app.
	 * 
	 * @param args is the default arguments for main()
	 * @throws UserInfoMissingException if user does not have enough valid
	 *                                  information (i.e. userid, permissions)
	 */
	public static void main(String[] args) throws UserInfoMissingException {
		EnumSet<Perms> defaultperms = EnumSet.of(Perms.DELETE_CAMP,Perms.EDIT_CAMP);
		User Armstrong = new Student();
		ArrayList<User> userlist = new ArrayList<User>();
		ArrayList<Camp> camplist = new ArrayList<Camp>();
		HashSet<String> fakeAttendees = new HashSet<String>();
		HashSet<String> fakeCampCommittee = new HashSet<String>();
		// Fake camp info
		LocalDate fakeCreationDate = LocalDate.of(2021, 11, 11);
		TreeMap<CampAspects, Object> fakeCampInfoMap = new TreeMap<CampAspects, Object>();
		fakeCampInfoMap.put(CampAspects.NAME, (String) "Camp1");
		fakeCampInfoMap.put(CampAspects.DATE, fakeCreationDate);
		fakeCampInfoMap.put(CampAspects.REGISTRATION_DEADLINE, fakeCreationDate.plusDays(1));
		fakeCampInfoMap.put(CampAspects.USERGROUP, Faculty.ADM);
		fakeCampInfoMap.put(CampAspects.LOCATION, types.Location.ADM);
		fakeCampInfoMap.put(CampAspects.SLOTS, (Integer) 200);
		fakeCampInfoMap.put(CampAspects.COMMITTEESLOTS, (Integer) 10);
		fakeCampInfoMap.put(CampAspects.DESCRIPTION, "Some Descriptions");
		fakeCampInfoMap.put(CampAspects.STAFFIC, (String) "Abdul Bari");
		CampInfo fakeCampInfo = new CampInfo(fakeCampInfoMap);
		fakeAttendees.add("Brandon Wright");
		fakeAttendees.add("Jokic Nikola");
		fakeCampCommittee.add("LeBron James");
		camplist.add(new Camp(fakeCampInfo, fakeAttendees, fakeCampCommittee, false, fakeCreationDate));
		// end of fake camp info
		userlist.add(Armstrong);
		MainController control = new MainController(userlist, camplist);
		control.grantPerms("Armstrong", defaultperms);
		Data.put("Controller", control);
		Data.put("UserPerms", Armstrong.getPerms());
		Data.put("Scanner", new Scanner(System.in));
		Data.put("CurrentUser", "Armstrong");
		CamsInteraction.startmenu.run();
	}

}
