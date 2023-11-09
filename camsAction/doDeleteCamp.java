package camsAction;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import controllers.CampController;
import controllers.UserController;
import interactions.Interaction;
import types.Perms;

public class doDeleteCamp extends Interaction {

	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception {
		int campid = GetData.CampID(data);
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(data.get("Controller"))||
			!UserController.class.isInstance(data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		Object control = data.get("Controller");
		
		
		System.out.println("Camp has been deleted");
		//Remove the camp from all participants data
		HashSet<String> participantlist = ((CampController)control).getCampComittees(campid);
		for(String participant:participantlist) {
			//Remove committee perms, but allow him to re-apply
			((UserController)control).denyPerms(participant, EnumSet.of(
					Perms.SUBMIT_CAMP_SUGGESTION,
					Perms.EDIT_CAMP_SUGGESTION,
					Perms.DELETE_CAMP_SUGGESTION
				));
			((UserController)control).grantPerms(participant, EnumSet.of(Perms.REGISTER_AS_COMITTEE));
		}
		((CampController)control).deleteCamp(campid);
		System.out.println("This change will be reflected for participants");
		return true;
	}

}
