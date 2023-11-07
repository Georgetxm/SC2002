package camsAction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Scanner;

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
		List<Entry<CampAspects,Object>> infolist = new ArrayList<Entry<CampAspects, Object>>(campcontrol.getCampDetails(campid).info().entrySet());

		Scanner s = getScanner(data);
		System.out.println("What would you like to amend:");
		for(int i=1;i<=infolist.size();i++) {
			String value;
			if(String.class.isInstance(infolist.get(i-1).getValue())) value = (String) infolist.get(i-1).getValue();
			if(Enum.class.isInstance(infolist.get(i-1).getValue())) value = ((Enum<?>) infolist.get(i-1).getValue()).name();
			if(Integer.class.isInstance(infolist.get(i-1).getValue())) value = infolist.get(i-1).getValue().toString();
			if(LocalDate.class.isInstance(infolist.get(i-1).getValue())) value = DateTimeFormatter.ofPattern("dd.MMMM uuuu", Locale.ENGLISH).format((TemporalAccessor) infolist.get(i-1).getValue());
		}
		int choice = s.nextInt();
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		String reason;
		System.out.println("Please type your rationale:");
		reason = s.nextLine();
		
		System.out.println("Your suggestion has been submitted.");
		
		usercontrol.incrementPoints(userid, 1);
		System.out.println("Your points have been incremented");
		System.out.println("This function is incomplete. Await CampInfo finalisation");
		return 1;
	}

}
