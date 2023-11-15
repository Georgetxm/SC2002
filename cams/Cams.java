package cams;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeMap;

import controllers.ControllerItemMissingException;
import controllers.ControllerParamsException;
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
	 * @throws ControllerParamsException
	 * @throws ControllerItemMissingException
	 */
	public static void main(String[] args) throws UserInfoMissingException, ControllerItemMissingException, ControllerParamsException {
		//load in 
		//login
		EnumSet<Perms> defaultperms = EnumSet.of(Perms.DELETE_CAMP,Perms.EDIT_CAMP,Perms.VIEW_EVERY_CAMP);
		User Armstrong = new Student();
		HashMap<String, User> userlist = new HashMap<String, User>();
		HashMap<Integer, Camp> camplist = new HashMap<Integer, Camp>();
		HashSet<String> fakeAttendees = new HashSet<String>();
		HashSet<String> fakeCampCommittee = new HashSet<String>();
		userlist.put("Armstrong", Armstrong);
		
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
		Camp fakeCamp = new Camp(fakeCampInfo, fakeAttendees, fakeCampCommittee, false, fakeCreationDate);
		camplist.put(fakeCamp.getCampid(), fakeCamp);
		// end of fake camp info
		
		MainController control = new MainController(userlist, camplist);
		control.grantPerms("Armstrong", defaultperms);
		Data.put("Controller", control);
		Data.put("UserPerms", Armstrong.getPerms());
		Data.put("Scanner", new Scanner(System.in));
		Data.put("CurrentUser", "Armstrong");
		CamsInteraction.startmenu.run();
	}

}
