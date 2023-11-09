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
		Object control=data.get("Controller");
		if(
			!CampController.class.isInstance(control)||
			!UserController.class.isInstance(control)
		)	throw new Exception("Controller not able enough. Request Failed.");

		
		int userid=GetData.CurrentUser(data);
		int campid=GetData.CampID(data);

		HashMap<Integer, String> camplist = ((CampController) control).FilterUser(userid).getCamps();
		String campname = (String) ((CampController) control).getCampDetails(campid).info().get(CampAspects.NAME);
		
		if(camplist.keySet().contains(campid)) {
			if(camplist.get(campid)!=campname) throw new Exception("Data error. CampID and CampName mismatch!");
			System.out.println("Already registered");
			return false;
		}
		
		if(((CampController) control).isCommiteeFull(campid)) {
			System.out.println("Committee is full. Please apply again next time.");
			return false;
		}
		((CampController) control).joinCamp(campid, userid, Role.COMITTEE);
		((UserController) control).grantPerms(userid, EnumSet.of(
			Perms.SUBMIT_CAMP_SUGGESTION,
			Perms.EDIT_CAMP_SUGGESTION,
			Perms.DELETE_CAMP_SUGGESTION
		));
		((UserController) control).denyPerms(userid, EnumSet.of(Perms.REGISTER_AS_COMITTEE));
		System.out.println("Registered successfully as a comittee member.");
		
		return true;
	}

}
