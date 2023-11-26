package controllers;

/**
 * Represents a Controller ENUM class which is a singleton
 * Holds all the controllers for the program via the interface
 * Inherits from User class
 * 
 * @see UserControlInterface, CampControlInterface, EnquiryControlInterface,
 *      SuggestionControlInterface
 * @author Teo Xuan Ming
 * @version 1.1
 * @since 2021-11-24
 */
public enum Controller {

	/**
	 * The only instance of the Controller ENUM
	 */
	INSTANCE;

	/**
	 * The CampControlInterface object
	 */
	private CampControlInterface camp;

	/**
	 * The EnquiryControlInterface object
	 */
	private EnquiryControlInterface enquiry;

	/**
	 * The SuggestionControlInterface object
	 */
	private SuggestionControlInterface suggestion;

	/**
	 * The UserControlInterface object
	 */
	private UserControlInterface user;

	/**
	 * The Directory object
	 */
	private Directory directory;

	/**
	 * Default constructor for Controller
	 * 
	 * @return CampControlInterface
	 */
	public CampControlInterface Camp() {
		return camp;
	}

	/**
	 * Creates new CampControlInterface object with the given parameters, meant for
	 * use when reading from camp_list.csv
	 * 
	 * @param camp the CampControlInterface object
	 * @return void
	 */
	public void setCamp(CampControlInterface camp) {
		this.camp = camp;
	}

	/**
	 * Default constructor for Controller
	 * 
	 * @return EnquiryControlInterface
	 */
	public EnquiryControlInterface Enquiry() {
		return enquiry;
	}

	/**
	 * Creates new EnquiryControlInterface object with the given parameters, meant
	 * 
	 * @param enquiry
	 */
	public void setEnquiry(EnquiryControlInterface enquiry) {
		this.enquiry = enquiry;
	}

	/**
	 * Default constructor for Controller
	 * 
	 * @return
	 */
	public SuggestionControlInterface Suggestion() {
		return suggestion;
	}

	/**
	 * Creates new SuggestionControlInterface object with the given parameters,
	 * meant
	 * 
	 * @param suggestion
	 */
	public void setSuggestion(SuggestionControlInterface suggestion) {
		this.suggestion = suggestion;
	}

	/**
	 * Default constructor for UserController
	 * 
	 * @return
	 */
	public UserControlInterface User() {
		return user;
	}

	/**
	 * Creates new UserControlInterface object with the given parameters, meant
	 * 
	 * @param user
	 */
	public void setUser(UserControlInterface user) {
		this.user = user;
	}

	/**
	 * Default constructor for Directory
	 * 
	 * @return
	 */

	public Directory Directory() {
		return directory;
	}

	/**
	 * Creates new Directory object with the given parameters, meant
	 * 
	 * @param dir
	 */
	public void setDirectory(Directory dir) {
		this.directory = dir;
	}
}
