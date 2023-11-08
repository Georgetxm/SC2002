package camsAction;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map.Entry;

import controllers.CampController;
import controllers.UserController;
import interactions.Interaction;
import types.Perms;
import types.Role;

public class doDeleteCamp extends Interaction {

	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception {
		int campid = GetData.CampID(data);
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(data.get("Controller"))||
			!UserController.class.isInstance(data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		CampController campcontrol = (CampController) data.get("Controller");
		UserController usercontrol = (UserController) data.get("Controller");
		
		//Delete the camp
		campcontrol.deleteCamp(campid);
		System.out.println("Camp has been deleted");
		//Remove the camp from all participants data
		Entry<Integer,Role> participantlist[] = campcontrol.getCampParticipantID(campid);
		for(Entry<Integer,Role> participant:participantlist) {
			switch(participant.getValue()) {
			case ATTENDEE: usercontrol.deleteCamp(campid, campid); break;
			case COMITTEE: 
				//Set CampComittee to some error value
				usercontrol.setCampComittee(campid, "", 0);
				//Remove committee perms, but allow him to re-apply
				usercontrol.denyPerms(participant.getKey(), EnumSet.of(
						Perms.SUBMIT_CAMP_SUGGESTION,
						Perms.EDIT_CAMP_SUGGESTION,
						Perms.DELETE_CAMP_SUGGESTION
					));
				usercontrol.grantPerms(participant.getKey(), EnumSet.of(Perms.REGISTER_AS_COMITTEE));
			default: break;
			}
		}
		System.out.println("This change will be reflected for participants");
		return true;
	}

}
