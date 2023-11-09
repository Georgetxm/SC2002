package camsAction;

import java.util.HashMap;
import controllers.CampController;
import controllers.UserController;
import interactions.Interaction;
import types.CampAspects;
import types.Role;

public final class doSubmitAttendeeRegistration extends Interaction {

	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(data.get("Controller"))||
			!UserController.class.isInstance(data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		Object control = data.get("Controller");
		
		int userid=GetData.CurrentUser(data);
		int campid=GetData.CampID(data);
		//Gets list of camps the owner is in to check if he alr attending
		HashMap<Integer, String> camplist = ((CampController)control).FilterUser(userid).getCamps();
		//Get camp name just to check if data tallies
		String campname = (String) ((CampController)control).getCampDetails(campid).info().get(CampAspects.NAME);
		
		if(camplist.keySet().contains(campid)) {
			if(camplist.get(campid)!=campname) throw new Exception("Data error. CampID and CampName mismatch!");
			System.out.println("Already registered");
			return false;
		}
		
		if(((CampController)control).isAttendeeFull(campid)) {
			System.out.println("Camp is currently full. Please wait for others to withdraw.");
			return false;
		}
		
		((CampController)control).joinCamp(campid, userid, Role.ATTENDEE);
		System.out.println("Registered successfully as an attendee.");
		
		return true;
	}

}
