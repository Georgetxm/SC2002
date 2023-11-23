package cams;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeMap;

import camsAction.MissingRequestedDataException;
import controllers.Controller;
import entities.Camp;
import entities.CampInfo;
import entities.Staff;
import entities.Student;
import entities.User;
import entities.UserInfoMissingException;
import types.CampAspect;
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
		TreeMap<CampAspect, Object> fakeCampInfoMap = new TreeMap<CampAspect, Object>();
		fakeCampInfoMap.put(CampAspect.NAME, (String) "Camp1");
		fakeCampInfoMap.put(CampAspect.DATE, dateset);
		fakeCampInfoMap.put(CampAspect.REGISTRATION_DEADLINE, fakeCreationDate.plusDays(1));
		fakeCampInfoMap.put(CampAspect.USERGROUP, Faculty.ADM);
		fakeCampInfoMap.put(CampAspect.LOCATION, types.Location.ADM);
		fakeCampInfoMap.put(CampAspect.SLOTS, (Integer) 200);
		fakeCampInfoMap.put(CampAspect.COMMITTEESLOTS, (Integer) 10);
		fakeCampInfoMap.put(CampAspect.DESCRIPTION, "Some Descriptions");
		fakeCampInfoMap.put(CampAspect.STAFFIC, (String) "Abdul Bari");
		CampInfo fakeCampInfo = new CampInfo(fakeCampInfoMap);
		fakeAttendees.add("Brandon Wright");
		fakeAttendees.add("Jokic Nikola");
		fakeCampCommittee.add("LeBron James");
		Camp fakeCamp = new Camp(fakeCampInfo, fakeAttendees, fakeCampCommittee, false, fakeCreationDate);
		// end of fake camp info
		
		Controller control = Controller.INSTANCE;
		control.setCamp(new controllers.CampController());
		control.setEnquiry(new controllers.EnquiryController());
		control.setSuggestion(new controllers.SuggestionController());
		control.setUser(new controllers.UserController());
		control.setDirectory(new controllers.Lookup());
		Scanner s = new Scanner(System.in);
		while(true) {
			String currentuser = Login.getCurrentUser(s,userlist);
			interactions.Interaction next = CamsInteraction.startmenu(currentuser);
			interactions.Interaction current;
			while(next!=null) {
				current = next;
				try {
					next = next.run(currentuser, s, control);
				} catch (UserInfoMissingException e) {
					break;
				} catch (MissingRequestedDataException e) {
					next = current;
					e.printStackTrace();
				}
			}
		}
	}

}
