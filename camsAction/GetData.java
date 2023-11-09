package camsAction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import core.CampInfo;

final class GetData {
	static final String CurrentUser(HashMap<String,Object> data) throws Exception {
		String userid;
		if(!data.containsKey("CurrentUser")) throw new Exception("User not identified. Request Failed.");
		try {userid = (String) data.get("CurrentUser");}
		catch(ClassCastException e) {
			throw new Exception("Invalid User ID. Request Failed.");
		}
		return userid;
	}
	static final int CampID(HashMap<String,Object> data) throws Exception {
		int campid;
		if(!data.containsKey("CurrentCamp")) throw new Exception("Did not select camp. Request Failed.");
		try {campid = (int) data.get("CurrentCamp");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Camp ID. Request Failed.");
		}
		return campid;
	}
	
	static final int SuggestionID(HashMap<String,Object> data) throws Exception {
		int suggestionid;
		if(!data.containsKey("CurrentItem")) throw new Exception("Did not select suggestion. Request Failed.");
		try {suggestionid = (int) data.get("CurrentItem");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Suggestion ID. Request Failed.");
		}
		return suggestionid;
	}
	static final int EnquiryID(HashMap<String,Object> data) throws Exception {
		int enquiryid;
		if(!data.containsKey("CurrentItem")) throw new Exception("Did not select enquiry. Request Failed.");
		try {enquiryid = (int) data.get("CurrentItem");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Enquiry ID. Request Failed.");
		}
		return enquiryid;
	}
	static final CampInfo CampInfo(HashMap<String,Object> data) throws Exception{
		CampInfo campinfo;
		if(!data.containsKey("CampInfo")) throw new Exception("Camp info not retrieved");
		try {campinfo = (CampInfo) data.get("CampInfo");}
		catch(ClassCastException e) {
			throw new Exception("Invalid Camp Info. Request Failed.");
		}
		return campinfo;
	}
	static final Boolean isSilenced(HashMap<String,Object> data) throws Exception{
		Boolean silenced;
		if(!data.containsKey("isSilenced")) return false;
		try {silenced = (Boolean) data.get("CampInfo");}
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
