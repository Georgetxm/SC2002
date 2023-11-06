package camsAction;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import controllers.CampController;
import controllers.UserController;
import interactions.Interaction;
import types.CampAspects;
import types.Perms;
import types.Role;

public final class doSubmitAttendeeRegistration extends Interaction {

	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(data.get("Controller"))||
			!UserController.class.isInstance(data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		CampController campcontrol = (CampController) data.get("Controller");
		UserController usercontrol = (UserController) data.get("Controller");
		
		int userid=GetData.CurrentUser(data);
		int campid=GetData.CampID(data);
		String campname = (String) campcontrol.getCampDetails(campid).info().get(CampAspects.NAME);
		
		if(usercontrol.getCamp(userid).contains(new HashMap.SimpleEntry<String,Integer>(campname,campid))) {
			System.out.println("Already registered");
			return false;
		}
		
		if(campcontrol.isAttendeeFull(campid)) {
			System.out.println("Camp is currently full. Please wait for others to withdraw.");
			return false;
		}
		
		campcontrol.submitUser(campid, userid, Role.ATTENDEE);
		usercontrol.addCamp(userid, campname, campid);
		System.out.println("Registered successfully as an attendee.");
		
		return true;
	}

}
