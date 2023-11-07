package camsAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

import controllers.CampController;
import controllers.UserController;
import core.CampInfo;
import interactions.Interaction;
import types.CampAspects;

public final class doSubmitSuggestion extends Interaction{
	//Currently using distinct control interfaces for dual inheritance
	@Override
	public final Integer run(HashMap<String, Object> data) throws Exception {
		if(!data.containsKey("Controller")) throw new Exception("No controller found. Request Failed.");
		if(
			!CampController.class.isInstance(data.get("Controller"))||
			!UserController.class.isInstance(data.get("Controller"))
		)	throw new Exception("Controller not able enough. Request Failed.");
		CampController campcontrol = (CampController) data.get("Controller");
		UserController usercontrol = (UserController) data.get("Controller");
		
		int campid = GetData.CampID(data);
		int userid = GetData.CurrentUser(data);
		//Asks campcontroller for the camp details, and puts them into a list
		List<Entry<CampAspects,? extends Object>> infolist = new ArrayList<Entry<CampAspects, ? extends Object>>(campcontrol.getCampDetails(campid).info().entrySet());

		Scanner s = getScanner(data);
		int choice;
		Entry<CampAspects, ? extends Object> editedvalue;
		
		while(true) {
			System.out.println("What would you like to amend:");
			for(int i=1;i<=infolist.size();i++)
				System.out.printf("%d: %s\n",i,GetData.FromObject(infolist.get(i-1)));
			choice = s.nextInt();
			if(choice<1||choice>infolist.size()) {
				System.out.println("Invalid option");
				continue;
			}
			break;
		}
		
		switch(infolist.get(choice-1).getKey()) {
		case DATE: 				editedvalue = (Entry<CampAspects, ? extends Object>) ParseInput.CampDate(s); 		break;
		case LASTREGISTERDATE: 	editedvalue = (Entry<CampAspects, ? extends Object>) ParseInput.CampRegisterDate(s);break;
		case LOCATION: 			editedvalue = (Entry<CampAspects, ? extends Object>) ParseInput.CampLocation(s); 	break;
		case SLOTS: 			editedvalue = (Entry<CampAspects, ? extends Object>) ParseInput.CampSlots(s); 		break;
		case DESCRIPTION: 		editedvalue = (Entry<CampAspects, ? extends Object>) ParseInput.CampDescription(s); break;
		default: System.out.println("This field cannot be changed."); return -1;
		}
		
		infolist.remove(choice-1);
		infolist.add(editedvalue);
		CampInfo edited = new CampInfo(new TreeMap<>(infolist.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue))));
		
		String reason;
		System.out.println("Please type your rationale:");
		reason = s.nextLine();
		
		int suggestionid = campcontrol.submitSuggestion(campid, edited, reason, userid);
		System.out.println("Your suggestion has been submitted.");
		
		usercontrol.incrementPoints(userid, 1);
		System.out.println("Your points have been incremented");
		return suggestionid;
	}

}
