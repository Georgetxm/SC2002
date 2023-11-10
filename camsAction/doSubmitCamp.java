package camsAction;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import controllers.CampController;
import controllers.UserController;
import entities.CampInfo;
import entities.Data;

import java.util.Scanner;
import java.util.TreeMap;

import interactions.Interaction;
import types.CampAspects;

public class doSubmitCamp extends Interaction {

	@Override
	public final Integer run() throws Exception {
		if(!Data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(Data.get("Controller"))||
			!UserController.class.isInstance(Data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		String userid = GetData.CurrentUser();
		Scanner s = getScanner();
		//For each camp aspect required in camp details, calls its parse input function.
		//These are then compiled and given to campcontrol so it can register a camp
		//Typecasts are done when necessary
		int campid = ((CampController)control).addCamp(new CampInfo(new TreeMap<>(Map.ofEntries(
				(Entry<CampAspects, ? extends Object>) ParseInput.CampName(s),
				(Entry<CampAspects, ? extends Object>) ParseInput.CampDate(s),
				(Entry<CampAspects, ? extends Object>) ParseInput.CampRegisterDate(s),
				(Entry<CampAspects, ? extends Object>) ParseInput.CampFaculty(s),
				(Entry<CampAspects, ? extends Object>) ParseInput.CampLocation(s),
				(Entry<CampAspects, ? extends Object>) ParseInput.CampSlots(s),
				(Entry<CampAspects, ? extends Object>) new HashMap.SimpleEntry<CampAspects,Integer>(CampAspects.COMMITTEESLOTS,10),
				(Entry<CampAspects, ? extends Object>) ParseInput.CampDescription(s),
				new HashMap.SimpleEntry<CampAspects, Object>(CampAspects.STAFFIC,userid)
		))),userid);
		
		System.out.println("Camp has been created.");
		return campid;
	}

}
