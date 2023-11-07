package camsAction;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import controllers.CampController;
import controllers.UserController;
import core.CampInfo;

import java.util.Scanner;
import java.util.TreeMap;

import interactions.Interaction;
import types.CampAspects;

public class doSubmitCamp extends Interaction {

	@Override
	public final Integer run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(data.get("Controller"))||
			!UserController.class.isInstance(data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		CampController campcontrol = (CampController) data.get("Controller");
		UserController usercontrol = (UserController) data.get("Controller");
		int userid = GetData.CurrentUser(data);
		
		Scanner s = getScanner(data);
		//For each camp aspect required in camp details, calls its parse input function.
		//These are then compiled and given to campcontrol so it can register a camp
		//Typecasts are done when necessary
		int campid = campcontrol.submitCamp(new CampInfo(new TreeMap<>(Map.ofEntries(
				(Entry<CampAspects, ? extends Object>) ParseInput.CampName(s),
				(Entry<CampAspects, ? extends Object>) ParseInput.CampDate(s),
				(Entry<CampAspects, ? extends Object>) ParseInput.CampRegisterDate(s),
				(Entry<CampAspects, ? extends Object>) ParseInput.CampFaculty(s),
				(Entry<CampAspects, ? extends Object>) ParseInput.CampLocation(s),
				(Entry<CampAspects, ? extends Object>) ParseInput.CampSlots(s),
				(Entry<CampAspects, ? extends Object>) ParseInput.CampComitteeSlots(s),
				(Entry<CampAspects, ? extends Object>) ParseInput.CampDescription(s),
				new HashMap.SimpleEntry<CampAspects, Object>(CampAspects.STAFFIC,userid)
		))));
		//Asks camp control for camp details and pulls the camp name out of camp details
		String campname = (String) campcontrol.getCampDetails(campid).info().get(CampAspects.NAME);
		usercontrol.addCamp(userid, campname, campid);
		return campid;
	}

}
