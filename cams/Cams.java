package cams;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeMap;

import controllers.MainController;
import entities.Camp;
import entities.CampInfo;
import entities.Data;
import entities.Staff;
import entities.Student;
import entities.User;
import entities.UserInfoMissingException;
import types.CampAspects;
import types.Faculty;

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
	public static void main(String[] args) throws UserInfoMissingException{
		//load in 
		//login
		HashMap<String, User> userlist = new HashMap<String, User>();
		HashMap<Integer, Camp> camplist = new HashMap<Integer, Camp>();
		HashSet<String> fakeAttendees = new HashSet<String>();
		HashSet<String> fakeCampCommittee = new HashSet<String>();

		Student Armstrong = new Student("Armstrong","Password",Faculty.ADM);
		Staff Aldrin = new Staff("Aldrin","Password");
		userlist.put("Armstrong", Armstrong);
		userlist.put("Aldrin", Aldrin);
		// Fake camp info
		LocalDate fakeCreationDate = LocalDate.of(2021, 11, 11);
		HashSet<LocalDate> dateset = new HashSet<LocalDate>();
		dateset.add(fakeCreationDate);
		TreeMap<CampAspects, Object> fakeCampInfoMap = new TreeMap<CampAspects, Object>();
		fakeCampInfoMap.put(CampAspects.NAME, (String) "Camp1");
		fakeCampInfoMap.put(CampAspects.DATE, dateset);
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
		// end of fake camp info
		
		MainController control = new MainController(userlist, camplist);

		Data.put("Controller", control);
		Data.put("Scanner", new Scanner(System.in));
		while(true) {
			Data.put("CurrentUser", "Aldrin");
			CamsInteraction.startmenu.run();
			Data.put("CurrentUser", "Armstrong");
			CamsInteraction.startmenu.run();
		}
	}

}
