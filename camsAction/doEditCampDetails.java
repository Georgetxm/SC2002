package camsAction;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import cams.CamsInteraction;
import controllers.CampControlInterface;
import controllers.Controller;
import controllers.ControllerItemMissingException;
import entities.UserInfoMissingException;
import interactions.Interaction;
import types.CampAspect;
/**
 * Interaction that represents the action of changing the value of a single camp detail for a camp
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public class doEditCampDetails extends Interaction {
	/**
	 * Requests the controller to change the values of one aspect in a camp's camp details.
	 * Asks users for the aspect to be amended and the desired values to construct the controller request.
	 *@return true if controller accepts the request(s) and false if otherwise, or the user attempted to edit an uneditable field.
	 *@throws MissingRequestedDataException if camp whose camp details were to be edited cannot be found.
	 */@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException, MissingRequestedDataException {
		if(campid==null||userid==null) throw new MissingRequestedDataException("Camp or user editing not found");
		TreeMap<CampAspect,? extends Object> info = ((CampControlInterface) control).details(campid).info();
		int choice=0;
		while(true) {
			System.out.println("What would you like to amend:");
			int counter = 1;
			for(CampAspect aspect:info.keySet())// For each aspect, print aspect
				System.out.printf("%d: %s\n",counter++,GetData.FromObject(info.get(aspect)));
			choice = s.nextInt(); //user chooses an aspect, see if choice is valid
			if(choice<1||choice>info.keySet().size()) {
				System.out.println("Invalid option");
				continue;
			}
			break;
		}

		Entry<CampAspect, ? extends Object> edited=null;
		CampAspect chosenaspect = (CampAspect) info.keySet().toArray()[choice-1];
		switch(chosenaspect) { //Depending on the aspect chosen, request data from user
		case DATE: 				edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampDate(s); 		break;
		case USERGROUP:			edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampFaculty(s); 		break;
		case REGISTRATION_DEADLINE: 	edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampRegisterDate(s);break;
		case LOCATION: 			edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampLocation(s); 	break;
		case SLOTS: 			edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampSlots(s); 		break;
		case DESCRIPTION: 		edited = (Entry<CampAspect, ? extends Object>) ParseInput.CampDescription(s); break;
		default: System.out.println("This field cannot be changed.");
		}
		if(edited!=null)((CampControlInterface) control).editDetails(campid,edited);
		HashMap<Integer, String> usercamps = null;
		try {
			usercamps = ((CampControlInterface) ((CampControlInterface) control).FilterUser(currentuser)).getCamps();
		} catch (ControllerItemMissingException e) {
			throw new UserInfoMissingException("User id not valid");
		}
		next = (usercamps!=null&&usercamps.containsKey(campid))?CamsInteraction.OwnCampMenu(campid, currentuser):CamsInteraction.OtherCampMenu(campid,currentuser);
		if(this.userid!=null) next = next.withuser(userid);
		if(this.filters!=null) next = next.withfilter(filters);
		return next.withcamp(campid);
	}
}

