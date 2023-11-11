package controllers;

import java.util.HashMap;

public interface EnquiryController extends Controller{
	int addEnquiry(String enquiry, String ownerid, int campid);
	int editEnquiry(int enquiryid, String enquiry);
	String getEnquiry(int enquiryid);
	HashMap<Integer,String> getEnquiries(); //gets all suggestions that fulfill the filter, clears filter
	//EnquiryController.FilterCamp(campid).FilterUser(userid).getEnquiries();
	Boolean deleteEnquiry(int enquiryid); //delete suggestion
	Boolean finaliseEnquiry(int enquiryid); //mark as uneditable
	Boolean isEnquiryEditable(int enquiryid); //check if can edit
	
	String getOwner(int enquiryid);
	int getHostCamp(int enquiryid);
	
	int saveReply(int enquiryid, String reply);
	String[] getReply(int enquiryid);
}
