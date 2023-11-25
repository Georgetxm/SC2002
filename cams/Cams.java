package cams;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

import camsAction.MissingRequestedDataException;
import controllers.Controller;
// import controllers.MainController;
import entities.Camp;
import entities.Enquiry;
import entities.Suggestion;
import entities.User;
import entities.UserInfoMissingException;
import types.CampAspect;
import types.Role;

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
		HashMap<String, User> userlist = new HashMap<String, User>();
		HashMap<Integer, Camp> camplist = new HashMap<Integer, Camp>();
		HashMap<Integer, Enquiry> enquirylist = new HashMap<Integer, Enquiry>();
		HashMap<Integer, Suggestion> suggestionlist = new HashMap<Integer, Suggestion>();

		// Read CSV files from lists folder
		ReadWriteUserCSV.readUserCSV(userlist, "lists");
		ReadWriteCampCSV.readCampCSV(camplist, "lists");
		ReadWriteEnquiryCSV.readEnquiryCSV(enquirylist, "lists");
		ReadWriteSuggestionCSV.readSuggestionCSV(suggestionlist, "lists");

		Scanner s = new Scanner(System.in);
		Controller control = Controller.INSTANCE;
		control.setCamp(new controllers.CampController(camplist));
		control.setUser(new controllers.UserController(userlist));
		control.setEnquiry(new controllers.EnquiryController(enquirylist));
		control.setSuggestion(new controllers.SuggestionController(suggestionlist));
		control.setDirectory(new controllers.Lookup().sync());
		while (true) {
			String currentuser = Login.getCurrentUser(s, userlist);
			interactions.Interaction next = CamsInteraction.startmenu(currentuser);
			interactions.Interaction current;
			while (next != null) {
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
