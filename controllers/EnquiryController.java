package controllers;

public interface EnquiryController {
	int save(String enquiry);
	int edit(int enquiryid, String enquiry);
	String get(int suggestionid);
	Boolean delete(int enquiryid); //delete suggestion
	Boolean finalise(int enquiryid); //mark as uneditable
	Boolean isEditable(int enquiryid); //check if can edit
	
	int saveReply(String reply);
	String[] getReply(int enquiryid);
}
