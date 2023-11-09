package camsAction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.Map.Entry;

import entities.CampInfo;
import entities.Data;

final class GetData {
	static final String CurrentUser() throws Exception {
		String userid;
		if(!Data.containsKey("CurrentUser")) throw new Exception("User not identified. Request Failed.");
		try {userid = (String) Data.get("CurrentUser");}
		catch(ClassCastException e) {
			throw new Exception("Invalid User ID. Request Failed.");
		}
		return userid;
	}
	static final int CampID() throws Exception {
		int campid;
		if(!Data.containsKey("CurrentCamp")) throw new Exception("Did not select camp. Request Failed.");
		try {campid = (int) Data.get("CurrentCamp");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Camp ID. Request Failed.");
		}
		return campid;
	}
	
	static final int SuggestionID() throws Exception {
		int suggestionid;
		if(!Data.containsKey("CurrentItem")) throw new Exception("Did not select suggestion. Request Failed.");
		try {suggestionid = (int) Data.get("CurrentItem");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Suggestion ID. Request Failed.");
		}
		return suggestionid;
	}
	static final int EnquiryID() throws Exception {
		int enquiryid;
		if(!Data.containsKey("CurrentItem")) throw new Exception("Did not select enquiry. Request Failed.");
		try {enquiryid = (int) Data.get("CurrentItem");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Enquiry ID. Request Failed.");
		}
		return enquiryid;
	}
	static final CampInfo CampInfo() throws Exception{
		CampInfo campinfo;
		if(!Data.containsKey("CampInfo")) throw new Exception("Camp info not retrieved");
		try {campinfo = (CampInfo) Data.get("CampInfo");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Camp Info. Request Failed.");
		}
		return campinfo;
	}
	static final Boolean isSilenced() throws Exception{
		Boolean silenced;
		if(!Data.containsKey("isSilenced")) return false;
		try {silenced = (Boolean) Data.get("CampInfo");}
		catch(ClassCastException e) {
			throw new Exception("Invalid silenced tag");
		}
		return silenced;
	}
	//This is a function that recursively passes all constituent objects and turns them into strings.
	static final String FromObject(Object value) throws Exception{
		String valuestring="";
		if(Iterable.class.isInstance(value)) 
			for(Object thing:(Iterable<?>)value) valuestring+=(FromObject(thing)+"\n");
		else if(Entry.class.isInstance(value)) valuestring = String.format("%s:\t%s", FromObject(((Entry<?,?>) value).getKey()), FromObject(((Entry<?,?>) value).getValue()));
		else if(String.class.isInstance(value)) valuestring = (String) value;
		else if(LocalDate.class.isInstance(value)) valuestring = DateTimeFormatter.ofPattern("dd.MMMM uuuu", Locale.ENGLISH).format((TemporalAccessor) value);
		else valuestring = value.toString();
		return valuestring;
	}
}
