package controllers;

public enum Controller {
    
    INSTANCE; 
 
    private CampControlInterface camp;
    private EnquiryControlInterface enquiry;
    private SuggestionControlInterface suggestion;
    private UserControlInterface user;
    private Directory directory;
    
	public CampControlInterface Camp() {
		return camp;
	}
	public void setCamp(CampControlInterface camp) {
		this.camp = camp;
	}
	public EnquiryControlInterface Enquiry() {
		return enquiry;
	}
	public void setEnquiry(EnquiryControlInterface enquiry) {
		this.enquiry = enquiry;
	}
	public SuggestionControlInterface Suggestion() {
		return suggestion;
	}
	public void setSuggestion(SuggestionControlInterface suggestion) {
		this.suggestion = suggestion;
	}
	public UserControlInterface User() {
		return user;
	}
	public void setUser(UserControlInterface user) {
		this.user = user;
	}
	public Directory Directory() {
		return directory;
	}
	public void setDirectory(Directory dir) {
		this.directory=dir;
	}	
}
