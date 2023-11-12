package camsAction;

import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;
import controllers.CampController;
import controllers.SuggestionController;
import controllers.UserController;
import entities.Data;
import entities.UserInfoMissingException;
import interactions.Interaction;
import types.CampAspects;

public final class doSubmitSuggestion extends Interaction {
	// Currently using distinct control interfaces for dual inheritance
	@Override
	public final Integer run() throws MissingRequestedDataException, UserInfoMissingException {
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
