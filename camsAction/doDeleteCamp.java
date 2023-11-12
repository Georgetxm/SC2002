package camsAction;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.NoSuchElementException;

import controllers.CampController;
import controllers.UserController;
import entities.Data;
import interactions.Interaction;
import types.Perms;

public class doDeleteCamp extends Interaction {

	@Override
	public final Boolean run() throws MissingRequestedDataException {
		if(!Data.containsKey("Controller")) throw new NoSuchElementException("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(Data.get("Controller"))||
			!UserController.class.isInstance(Data.get("Controller"))
		)	throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		int campid = GetData.CampID();
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
		System.out.println("Camp has been deleted. This change will be reflected for participants");
		return true;
	}

}
