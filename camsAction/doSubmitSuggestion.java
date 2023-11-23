package camsAction;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import cams.CamsInteraction;
import controllers.CampControlInterface;
import controllers.Controller;
import controllers.ControllerItemMissingException;
import controllers.SuggestionControlInterface;
import controllers.UserControlInterface;
import entities.UserInfoMissingException;
import interactions.Interaction;
import types.CampAspect;
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
	 */@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException, MissingRequestedDataException {
		if(campid==null) throw new MissingRequestedDataException("Camp not found");
		Entry<CampAspect, ? extends Object> edited = null;
		if (campid != ((UserControlInterface) control).getCampCommitteeOfStudent(currentuser))
			System.out.println("Not a committee member of this camp");
		else {
			// Asks campcontrol for the camp info and pulls out the info
			TreeMap<CampAspect, ? extends Object> info = ((CampControlInterface) control).details(campid).info();
			int choice = 0;
			while (true) {
				System.out.println("What would you like to amend:");
				int counter = 1;
				for (CampAspect aspect : info.keySet())// For each aspect, print aspect
					System.out.printf("%d: %s\n", counter, GetData.FromObject(info.get(aspect)));
				choice = s.nextInt(); // user chooses an aspect, see if choice is valid
				if (choice < 1 || choice > info.keySet().size()) {
					System.out.println("Invalid option");
					continue;
				}
				break;
			}
			CampAspect chosenaspect = (CampAspect) info.keySet().toArray()[choice - 1];
			switch (chosenaspect) { // Depending on the aspect chosen, request data from user
				case DATE:
					edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampDate(s);
					break;
				case REGISTRATION_DEADLINE:
					edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampRegisterDate(s);
					break;
				case LOCATION:
					edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampLocation(s);
					break;
				case SLOTS:
					edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampSlots(s);
					break;
				case DESCRIPTION:
					edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampDescription(s);
					break;
				default:
					System.out.println("This field cannot be changed.");
			}
		}
		if(edited!=null) {
			String reason;
			System.out.println("Please type your rationale:");
			reason = s.nextLine();
			try {
				((SuggestionControlInterface) control).add(edited, reason, currentuser, campid);
			} catch (ControllerItemMissingException e) {
				throw new MissingRequestedDataException("Camp id is invalid");
			}
			System.out.println("Your suggestion has been submitted.");
			((UserControlInterface) control).incrementPoints(currentuser, 1);
			System.out.println("Your points have been incremented");
		}
		HashMap<Integer, String> usercamps = null;
		try {
			usercamps = ((CampControlInterface) ((CampControlInterface) control).FilterUser(currentuser)).getCamps();
		} catch (ControllerItemMissingException e) {
			throw new UserInfoMissingException("User id not valid");
		}
		next = (usercamps!=null&&usercamps.containsKey(campid))?CamsInteraction.OwnCampMenu(campid, currentuser):CamsInteraction.OtherCampMenu(campid,currentuser);
		if(this.userid!=null) next = next.withuser(userid);
		if(this.campid!=null) next = next.withcamp(campid);
		if(this.filters!=null) next = next.withfilter(filters);
		if(this.ownerid!=null) next = next.withowner(this.ownerid);
		return next;
	}

}
