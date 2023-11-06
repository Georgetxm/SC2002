package camsAction;

import java.util.EnumSet;
import java.util.HashMap;

import controllers.CampController;
import controllers.UserController;
import interactions.Interaction;
import types.CampAspects;
import types.Perms;
import types.Role;

public final class doSubmitComitteeRegistration extends Interaction {

	@Override
	protected Object run(HashMap<String, Object> data) throws Exception {
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
		
		if(campcontrol.isCommiteeFull(campid)) {
			System.out.println("Committee is full. Please apply again next time.");
			return false;
		}
		
		campcontrol.submitUser(campid, userid, Role.ATTENDEE);
		usercontrol.addCamp(userid, campname, campid);
		usercontrol.grantPerms(userid, EnumSet.of(
			Perms.SUBMIT_CAMP_SUGGESTION,
			Perms.EDIT_CAMP_SUGGESTION,
			Perms.DELETE_CAMP_SUGGESTION
		));
		System.out.println("Registered successfully as a comittee member.");
		
		
		// TODO Auto-generated method stub
		return null;
	}

}
