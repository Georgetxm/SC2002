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
		Object control = data.get("Controller");
		
		
		System.out.println("Camp has been deleted");
		//Remove the camp from all participants data
		Entry<Integer,Role> participantlist[] = ((CampController)control).getCampParticipantID(campid);
		for(Entry<Integer,Role> participant:participantlist) {
			switch(participant.getValue()) {
			case COMITTEE: 
				//Remove committee perms, but allow him to re-apply
				((UserController)control).denyPerms(participant.getKey(), EnumSet.of(
						Perms.SUBMIT_CAMP_SUGGESTION,
						Perms.EDIT_CAMP_SUGGESTION,
						Perms.DELETE_CAMP_SUGGESTION
					));
				((UserController)control).grantPerms(participant.getKey(), EnumSet.of(Perms.REGISTER_AS_COMITTEE));
			default: break;
			}
		}
		((CampController)control).deleteCamp(campid);
		System.out.println("This change will be reflected for participants");
		return true;
	}

}
