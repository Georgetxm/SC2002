package controllers;

public interface EnquiryController {
	int addEnquiry(String enquiry, int ownerid, int campid);
	int editEnquiry(int enquiryid, String enquiry);
	String getEnquiry(int enquiryid);
	EnquiryController FilterCamp(int campid); //adds user filter
	EnquiryController FilterUser(int userid); //adds camp filter
	String[] getEnquiries(); //gets all suggestions that fulfill the filter, clears filter
	//EnquiryController.FilterCamp(campid).FilterUser(userid).getEnquiries();
	Boolean deleteEnquiry(int enquiryid); //delete suggestion
	Boolean finaliseEnquiry(int enquiryid); //mark as uneditable
	Boolean isEnquiryEditable(int enquiryid); //check if can edit
	
	int getOwner(int enquiryid);
	int getHostCamp(int enquiryid);
	
	int saveReply(int enquiryid, String reply);
	String[] getReply(int enquiryid);
}
