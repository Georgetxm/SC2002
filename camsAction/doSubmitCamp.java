package camsAction;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import cams.CamsInteraction;
import controllers.Controller;
import entities.CampInfo;
import entities.UserInfoMissingException;
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
	 *@return start menu
	 *@throws UserInfoMissingException if the user id cannot be found.
	 */
	@Override
	public final Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException, MissingRequestedDataException {
		int thiscamp = control.Camp().add(new CampInfo(new TreeMap<>(Map.ofEntries(
				(Entry<CampAspect, ? extends Object>) ParseInput.CampName(s),
				(Entry<CampAspect, ? extends Object>) ParseInput.CampDate(s),
				(Entry<CampAspect, ? extends Object>) ParseInput.CampRegisterDate(s),
				(Entry<CampAspect, ? extends Object>) ParseInput.CampFaculty(s),
				(Entry<CampAspect, ? extends Object>) ParseInput.CampLocation(s),
				(Entry<CampAspect, ? extends Object>) ParseInput.CampSlots(s),
				(Entry<CampAspect, ? extends Object>) new HashMap.SimpleEntry<CampAspect,Integer>(CampAspect.COMMITTEESLOTS,10),
				(Entry<CampAspect, ? extends Object>) ParseInput.CampDescription(s),
				new HashMap.SimpleEntry<CampAspect, Object>(CampAspect.STAFFIC,currentuser)
		))));
		control.Directory().sync().add(entities.Camp.class, thiscamp);
		control.Directory().sync().link(Arrays.asList(
				new HashMap.SimpleEntry<Class<?>,Serializable>(entities.Camp.class,campid),
				new HashMap.SimpleEntry<Class<?>,Serializable>(entities.User.class,currentuser)
		));
		System.out.println("Camp has been created.");
		return CamsInteraction.startmenu(currentuser);
	}
}
