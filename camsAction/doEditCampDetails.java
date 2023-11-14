package camsAction;

import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import controllers.CampController;
import entities.Data;
import interactions.Interaction;
import types.CampAspects;
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
	 */
	@Override
	public final Boolean run() throws MissingRequestedDataException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(Data.get("Controller"))
		)	throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		int campid = GetData.CampID();
		//Asks campcontrol for the camp info and pulls out the info
		TreeMap<CampAspects,? extends Object> info = ((CampController) control).getCampDetails(campid).info();

		Scanner s = getScanner();
		int choice=0;
		while(true) {
			System.out.println("What would you like to amend:");
			int counter = 1;
			for(CampAspects aspect:info.keySet())// For each aspect, print aspect
				System.out.printf("%d: %s\n",counter,GetData.FromObject(info.get(aspect)));
			choice = s.nextInt(); //user chooses an aspect, see if choice is valid
			if(choice<1||choice>info.keySet().size()) {
				System.out.println("Invalid option");
				continue;
			}
			break;
		}

		Entry<CampAspects, ? extends Object> edited;
		CampAspects chosenaspect = (CampAspects) info.keySet().toArray()[choice-1];
		switch(chosenaspect) { //Depending on the aspect chosen, request data from user
		case DATE: 				edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampDate(s); 		break;
		case REGISTRATION_DEADLINE: 	edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampRegisterDate(s);break;
		case LOCATION: 			edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampLocation(s); 	break;
		case SLOTS: 			edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampSlots(s); 		break;
		case DESCRIPTION: 		edited = (Entry<CampAspects, ? extends Object>) ParseInput.CampDescription(s); break;
		default: System.out.println("This field cannot be changed."); return false;
		}
		
		((CampController) control).editCampDetails(campid,edited);
		return true;
	}
}
