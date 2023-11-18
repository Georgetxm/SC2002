package camsAction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import entities.CampInfo;
import entities.Data;
import entities.UserInfoMissingException;
import types.CampAspect;
/**
 * Class that contains the error handling wrappers for Data exclusive to camsAction
 * Checks and throws the required exceptions as needed.
 * As this serves mainly as an error handling wrapper, classes in camsAction are free to access Data directly if they do not need error handling.
 * This class is static and should not have any instances, and its constructor is private
 * @see Data
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
final class GetData {
	/**
	 * Constructor is private as the class is static
	 */
	private GetData() {}
	/**
	 * Gets the user id of the current user from Data
	 * @return user id
	 * @throws UserInfoMissingException if user id cannot be found or is in the wrong format
	 */
	static final String CurrentUser() throws UserInfoMissingException {
		String userid;
		if(!Data.containsKey("CurrentUser")) throw new UserInfoMissingException("User not identified. Request Failed.");
		try {userid = (String) Data.get("CurrentUser");}
		catch(ClassCastException e) {
			throw new UserInfoMissingException("Invalid User ID. Request Failed.");
		}
		return userid;
	}
	/**
	 * Gets the camp id of the camp selected by the user from Data
	 * @return campid
	 * @throws MissingRequestedDataException if camp id is missing or malformed.
	 */
	static final int CampID() throws MissingRequestedDataException {
		int campid;
		if(!Data.containsKey("CurrentCamp")) throw new MissingRequestedDataException("Did not select camp. Request Failed.");
		try {campid = (int) Data.get("CurrentCamp");}
		catch(ClassCastException e) {
			throw new MissingRequestedDataException("Invalid Camp ID. Request Failed.");
		}
		return campid;
	}
	/**
	 * Gets the requested suggestion id of the suggestion selected by the user from Data.
	 * @return suggestion id
	 * @throws MissingRequestedDataException if suggestion id is missing or malformed
	 */
	static final int SuggestionID() throws MissingRequestedDataException {
		int suggestionid;
		if(!Data.containsKey("CurrentItem")) throw new MissingRequestedDataException("Did not select suggestion. Request Failed.");
		try {suggestionid = (int) Data.get("CurrentItem");}
		catch(ClassCastException e) {
			throw new MissingRequestedDataException("Invalid Suggestion ID. Request Failed.");
		}
		return suggestionid;
	}
	/**
	 * Gets the requested enquiry id of the enquiry selected by the user from Data.
	 * @return enquiry id
	 * @throws MissingRequestedDataException if enquiry id is missing or malformed
	 */
	static final int EnquiryID() throws MissingRequestedDataException {
		int enquiryid;
		if(!Data.containsKey("CurrentItem")) throw new MissingRequestedDataException("Did not select enquiry. Request Failed.");
		try {enquiryid = (int) Data.get("CurrentItem");}
		catch(ClassCastException e) {
			throw new MissingRequestedDataException("Invalid Enquiry ID. Request Failed.");
		}
		return enquiryid;
	}
	/**
	 * Gets the requested camp details the camp selected by the user from Data.
	 * @return camp details
	 * @throws MissingRequestedDataException if camp details are missing or malformed
	 */
	static final CampInfo CampInfo() throws MissingRequestedDataException{
		CampInfo campinfo;
		if(!Data.containsKey("CampInfo")) throw new MissingRequestedDataException("Camp info not retrieved");
		try {campinfo = (CampInfo) Data.get("CampInfo");}
		catch(ClassCastException e) {
			throw new MissingRequestedDataException("Invalid Camp Info. Request Failed.");
		}
		return campinfo;
	}
	/**
	 * Checks Data to see if the tag "isViewingOwnCamps" exists.
	 * @return true if the tag exists, and false if the tag does not exist
	 * @throws MissingRequestedDataException if the tag exists, but is malformed.
	 */
	static final Boolean isViewingOwnCamps() throws MissingRequestedDataException{
		Boolean isViewingOwnCamps;
		if(!Data.containsKey("isViewingOwnCamps")) return false;
		try {isViewingOwnCamps = (Boolean) Data.get("isViewingOwnCamps");}
		catch(ClassCastException e) {
			throw new MissingRequestedDataException("Invalid tag");
		}
		return isViewingOwnCamps;
	}
	/**
	 * Gets a hashmap of filters requested by the user from Data. If no filter is requested, returns empty hashmap.
	 * @return filter list
	 * @throws MissingRequestedDataException if filter list is malformed.
	 */
	@SuppressWarnings("unchecked")
	static final HashMap<CampAspect,Object> Filter() throws MissingRequestedDataException{
		HashMap<CampAspect,Object> filter;
		if(!Data.containsKey("Filter")) return new HashMap<CampAspect,Object>();
		try {filter = (HashMap<CampAspect,Object>) Data.get("Filter");}
		catch(ClassCastException e) {
			throw new MissingRequestedDataException("Invalid filter type");
		}
		return filter;
	}
	/**
	 * Recursively passes all constituent objects and turns them into strings
	 * @param value
	 * @return
	 */
	static final String FromObject(Object value){
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
