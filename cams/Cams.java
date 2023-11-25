package cams;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import camsAction.MissingRequestedDataException;
import controllers.Controller;
// import controllers.MainController;
import entities.Camp;
import entities.Enquiry;
import entities.Suggestion;
import entities.User;
import entities.UserInfoMissingException;

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
		HashMap<Integer, Enquiry> enquiries = new HashMap<Integer, Enquiry>();
		HashMap<Integer, Suggestion> suggestions = new HashMap<Integer, Suggestion>();
		// Read CSV files from lists folder
		ReadWriteUserCSV.readUserCSV(userlist, "lists");
		ReadWriteCampCSV.readCampCSV(camplist, "lists");
		ReadWriteEnquiryCSV.readEnquiryCSV(enquiries, "lists");
		ReadWriteSuggestionCSV.readSuggestionCSV(suggestions, "lists");
		System.out.println(userlist);
		Scanner s = new Scanner(System.in);
		Controller control = Controller.INSTANCE;
		control.setCamp(new controllers.CampController(camplist));
		control.setEnquiry(new controllers.EnquiryController(enquiries));
		control.setSuggestion(new controllers.SuggestionController(suggestions));
		control.setUser(new controllers.UserController(userlist));
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
