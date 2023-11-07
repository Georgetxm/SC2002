package camsAction;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import controllers.CampController;
import controllers.UserController;

import java.util.Scanner;

import interactions.Interaction;
import types.CampAspects;

public class doSubmitCamp extends Interaction {

	@Override
	protected Object run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(data.get("Controller"))||
			!UserController.class.isInstance(data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		CampController campcontrol = (CampController) data.get("Controller");
		UserController usercontrol = (UserController) data.get("Controller");
		int userid = GetData.CurrentUser(data);
		
		Scanner s = getScanner(data);
		Entry<CampAspects, String> name = ParseInput.CampName(s);
		Entry<CampAspects, HashSet<LocalDate>> date = ParseInput.CampDate(s);
//		CampAspects.LASTREGISTERDATE: LocalDate regdate
//		CampAspects.USERGROUP: Faculty faculty
//		CampAspects.LOCATION: Location location
//		CampAspects.SLOTS: Integer totalslots
//		CampAspects.COMITTEESLOTS: Integer comitteeslots
//		CampAspects.DESCRIPTION: String description
//		CampAspects.STAFFIC: Integer userid
		campcontrol.submitCamp(null);
		
		// TODO Auto-generated method stub
		return null;
	}

}
