package controllers;

public interface EnquiryController {
	int saveEnquiry(String enquiry);
	int editEnquiry(int enquiryid, String enquiry);
	String getEnquiry(int suggestionid);
	Boolean deleteEnquiry(int enquiryid); //delete suggestion
	Boolean finaliseEnquiry(int enquiryid); //mark as uneditable
	Boolean isEnquiryEditable(int enquiryid); //check if can edit
	
	int saveReply(String reply);
	String[] getReply(int enquiryid);
}
