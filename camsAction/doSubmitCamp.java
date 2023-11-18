package camsAction;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import controllers.CampController;
import controllers.UserController;
import entities.CampInfo;
import entities.Data;
import entities.UserInfoMissingException;

import java.util.Scanner;
import java.util.TreeMap;

import interactions.Interaction;
import types.CampAspect;
/**
 * Interaction that represents the creating and saving a camp into the database.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public class doSubmitCamp extends Interaction {
	/**
	 * Requests the controller to create and save a camp based off user submitted details.
	 * Queries the user for details before submitting the request.
	 *@return true if controller accepts the request(s) and false if otherwise.
	 *@throws UserInfoMissingException if the user id cannot be found.
	 */
	@Override
	public final Integer run() throws UserInfoMissingException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(Data.get("Controller"))||
			!UserController.class.isInstance(Data.get("Controller"))
		)	throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		String userid = GetData.CurrentUser();
		Scanner s = getScanner();
		//For each camp aspect required in camp details, calls its parse input function.
		//These are then compiled and given to campcontrol so it can register a camp
		//Typecasts are done when necessary
		int campid = ((CampController)control).addCamp(new CampInfo(new TreeMap<>(Map.ofEntries(
				(Entry<CampAspect, ? extends Object>) ParseInput.CampName(s),
				(Entry<CampAspect, ? extends Object>) ParseInput.CampDate(s),
				(Entry<CampAspect, ? extends Object>) ParseInput.CampRegisterDate(s),
				(Entry<CampAspect, ? extends Object>) ParseInput.CampFaculty(s),
				(Entry<CampAspect, ? extends Object>) ParseInput.CampLocation(s),
				(Entry<CampAspect, ? extends Object>) ParseInput.CampSlots(s),
				(Entry<CampAspect, ? extends Object>) new HashMap.SimpleEntry<CampAspect,Integer>(CampAspect.COMMITTEESLOTS,10),
				(Entry<CampAspect, ? extends Object>) ParseInput.CampDescription(s),
				new HashMap.SimpleEntry<CampAspect, Object>(CampAspect.STAFFIC,userid)
		))),userid);
		
		System.out.println("Camp has been created.");
		return campid;
	}

}
