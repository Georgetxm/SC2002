package camsAction;

import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;
import controllers.CampController;
import controllers.ControllerItemMissingException;
import controllers.SuggestionController;
import controllers.UserController;
import entities.Data;
import entities.UserInfoMissingException;
import interactions.Interaction;
import types.CampAspects;
/**
 * Interaction that represents the action of creating and saving a suggestion for some camp to the database.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doSubmitSuggestion extends Interaction {
	/**
	 * Requests the controller to save a suggestion by the current user on a given camp to the database.
	 * Then increments the points of the suggester by 1.
	 * Asks the controller if the camp is full or the user has already joined before requesting.
	 *@return true if controller accepts the request(s) and false if otherwise, or the camp is full, or the user is already registered
	 *@throws MissingRequestedDataException if the camp to be registered for cannot be found
	 *@throws UserInfoMissingException if the user id of the current user cannot be found
	 * @throws ControllerItemMissingException
	 */
	@Override
	public final Integer run() throws MissingRequestedDataException, UserInfoMissingException, ControllerItemMissingException {
		if (!Data.containsKey("Controller"))
			throw new NoSuchElementException("No controller found. Request Failed.");
		if (!CampController.class.isInstance(Data.get("Controller")) ||
				!UserController.class.isInstance(Data.get("Controller")) ||
				!SuggestionController.class.isInstance(Data.get("Controller")))
			throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");

		int campid = GetData.CampID();
		String userid = GetData.CurrentUser();
		if (campid != ((UserController) control).getCampCommitteeOfStudent(userid)) {
			System.out.println("Not a committee memeber of this camp");
			return -1;
		}
		// Asks campcontrol for the camp info and pulls out the info
		TreeMap<CampAspects, ? extends Object> info = ((CampController) control).getCampDetails(campid).info();

		Scanner s = getScanner();
		int choice = 0;
		while (true) {
			System.out.println("What would you like to amend:");
			int counter = 1;
			for (CampAspects aspect : info.keySet())// For each aspect, print aspect
				System.out.printf("%d: %s\n", counter, GetData.FromObject(info.get(aspect)));
			choice = s.nextInt(); // user chooses an aspect, see if choice is valid
			if (choice < 1 || choice > info.keySet().size()) {
				System.out.println("Invalid option");
				continue;
			}
			break;
		}

		Entry<CampAspects, ? extends Object> edited;
		CampAspects chosenaspect = (CampAspects) info.keySet().toArray()[choice - 1];
		switch (chosenaspect) { // Depending on the aspect chosen, request data from user
			case DATE:
				edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampDate(s);
				break;
			case REGISTRATION_DEADLINE:
				edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampRegisterDate(s);
				break;
			case LOCATION:
				edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampLocation(s);
				break;
			case SLOTS:
				edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampSlots(s);
				break;
			case DESCRIPTION:
				edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampDescription(s);
				break;
			default:
				System.out.println("This field cannot be changed.");
				return -1;
		}

		String reason;
		System.out.println("Please type your rationale:");
		reason = s.nextLine();

		int suggestionid = ((SuggestionController) control).addSuggestion(edited, reason, userid, campid);
		System.out.println("Your suggestion has been submitted.");

		((UserController) control).incrementPoints(userid, 1);
		System.out.println("Your points have been incremented");
		return suggestionid;
	}

}
