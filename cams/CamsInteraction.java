package cams;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import camsAction.doApproveSuggestion;
import camsAction.doChangePassword;
import camsAction.doDeleteAttendeeRegistration;
import camsAction.doDeleteCamp;
import camsAction.doDeleteEnquiry;
import camsAction.doDeleteSuggestion;
import camsAction.doEditCampDetails;
import camsAction.doEditEnquiry;
import camsAction.doEditSuggestion;
import camsAction.doGenerateAttendanceList;
import camsAction.doGeneratePerformanceReport;
import camsAction.doNothing;
import camsAction.doRejectSuggestion;
import camsAction.doSubmitAttendeeRegistration;
import camsAction.doSubmitCamp;
import camsAction.doSubmitCommitteeRegistration;
import camsAction.doSubmitEnquiry;
import camsAction.doSubmitReply;
import camsAction.doSubmitSuggestion;
import camsAction.doToggleVisibility;
import camsAction.queryCampsMenu;
import camsAction.queryEnquiriesMenu;
import camsAction.queryFilterCampByMenu;
import camsAction.querySuggestionsMenu;
import interactions.MenuChoice;
import interactions.StaticMenu;
import types.Perms;
/**
 * 
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
import types.Role;

/**
 * 
 */
/**
 * 
 */
/**
 * Static class to hold static menu choices and static user menus used in the CAMS app.
 * Each choice a menu can provide is represented by a MenuChoice object.
 * Selecting that choice will call an Interaction, which will either be a second menu, or will interact with the entity classes via a controller.
 * The objective of these multilevel menus is to inform the user and figure out his exact intention.
 * Once we know what the user exactly intends to do we can execute the relevant commands (e.g. add user A to camp B), prompting the user for parameters if needed.
 * Not all menus have their choices predefined, but those that do are initialised at compile time in this class
 * @see interactions.Interaction
 * @see MenuChoice
 * @see StaticMenu
 */
public final class CamsInteraction{ //Menu choices
	/**
     * This class is static. There should be no instances of it.
     */
	private CamsInteraction() {}
	/**
     * MenuChoice instance denoting the choice of viewing all camps
     */
	public static MenuChoice viewAllCamps=new MenuChoice(Perms.DEFAULT, "View All Camps", new queryCampsMenu());
	/**
     * MenuChoice instance denoting the choice of viewing camps stored in their own camp list 
     * <p>
     * This can be either participating camps for students, or managing camps for staff
     * @param userid the user to filter the camps by
     * @return the menu choice
     */
	public static MenuChoice viewOwnCamps(String userid) {
		return new MenuChoice(Perms.DEFAULT,"View your own camps",new queryCampsMenu().withuser(userid));
	}
	/**
     * MenuChoice instance denoting the choice of approving a specific provided camp suggestion
     * <p>
     * It is implied the menu and user know what suggestion is being approved
     * Usually a user would have already selected a suggestion before this choice is given
     * @return the menu choice
     */	
	public static MenuChoice approveSuggestion(Integer suggestionid) {
		return new MenuChoice(Perms.APPROVE_CAMP_SUGGESTION, "Approve Suggestion", new doApproveSuggestion().withsuggestion(suggestionid));
	}
	/**
	 * MenuChoice instance denoting the choice of replying to a specific provided enquiry on the camp
	 * <p>
	 * It is implied the menu and user know what enquiry is being approved
     * Usually a user would have already selected a enquiry before this choice is given
     * @param enquiryid the enquiry the reply is submitted to
     * @return the menu choice
	 */
	public static MenuChoice submitReply(Integer enquiryid) {
		return new MenuChoice(Perms.REPLY_CAMP_ENQUIRY,			"Reply to this enquiry",	new doSubmitReply().withenquiry(enquiryid));
	}
	/**
	 * MenuChoice instance denoting the choice of editing a specific provided camp suggestion
	 * <p>
     * It is implied the menu and user know what suggestion is being edited
     * Usually a user would have already selected a suggestion before this choice is given
     * @param suggestionid the suggestion to be edited
     * @return the menu choice
	 */
	public static MenuChoice editSuggestion(Integer suggestionid) {
		return new MenuChoice(Perms.EDIT_CAMP_SUGGESTION,"Edit this suggestion",new doEditSuggestion().withsuggestion(suggestionid));
	}
	/**
	 * MenuChoice instance denoting the choice of deleting a specific provided camp suggestion
	 * <p>
     * It is implied the menu and user know what suggestion is being deleted
     * Usually a user would have already selected a suggestion before this choice is given
     * @param suggestionid the suggestion to be deleted
     * @return the menu choice
	 */
	public static MenuChoice 	deleteSuggestion(Integer suggestionid) {
		return new MenuChoice(Perms.DELETE_CAMP_SUGGESTION,		"Delete this suggestion",	new doDeleteSuggestion().withsuggestion(suggestionid));
	}
	/**
	 * MenuChoice instance denoting the choice of editing a specific provided enquiry on the camp
	 * <p>
	 * It is implied the menu and user know what enquiry is being edited
     * Usually a user would have already selected a enquiry before this choice is given
     * @param enquiryid the enquiry to be edited
     * @return the menu choice
	 */
	public static MenuChoice editEnquiry(Integer enquiryid) {
		return new MenuChoice(Perms.SUBMIT_CAMP_ENQUIRY,			"Edit this enquiry",		new doEditEnquiry().withenquiry(enquiryid));
	}
	/**
	 * MenuChoice instance denoting the choice of replying to a specific provided enquiry on the camp
	 * <p>
	 * It is implied the menu and user know what enquiry is being approved
     * Usually a user would have already selected a enquiry before this choice is given
     * @param enquiryid the enquiry to be deleted
     * @return the menu choice
	 */
	public static MenuChoice deleteEnquiry(Integer enquiryid) {
		return new MenuChoice(Perms.SUBMIT_CAMP_ENQUIRY,			"Delete this enquiry",		new doDeleteEnquiry().withenquiry(enquiryid));
	}
	/**
	 * MenuChoice instance denoting the choice of creating an entire new camp
	 */
	public static MenuChoice createCamp = new MenuChoice(Perms.CREATE_CAMP,"Create a new camp",new doSubmitCamp());
	/**
	 * MenuChoice instance denoting the choice of adding a filter of choice to the existing list of camps viewed
	 * <p>
	 * This (currently) leads to a menu where the user gets to choose a filter to add to existing filters
	 */
	public static MenuChoice filterCampBy		= new MenuChoice(Perms.DEFAULT, 					"Add Filter",				new queryFilterCampByMenu());
	/**
	 * MenuChoice instance denoting the choice of removing all existing filters to the list of camps viewed.
	 * <p>
	 * This list does not include filter by camps owned as that itself is its own choice
	 * @see viewOwnCamps
	 */
	public static MenuChoice removeCampFilter	= new MenuChoice(Perms.DEFAULT, 					"Remove Filter",			new queryCampsMenu());
	/**
	 * MenuChoice instance denoting the choice of deleting a camp.
	 * <p>
	 * It is assumed this choice includes resolving all consequences as well, like removing the camp from students list of camps
	 * It is assumed the points earned via past enquiries and suggestions will be retained
	 * This is because this coder believes the camp being deleted does not devalue work the committee already spent on the camp
	 * @param campid the camp to be deleted
     * @return the menu choice
	 */
	public static MenuChoice deleteCamp(Integer campid) {
		return new MenuChoice(Perms.DELETE_CAMP,"Delete this camp",	new doDeleteCamp().withcamp(campid));
	}
	/**
	 * MenuChoice instance denoting the choice of editing camp details
	 * <p>
	 * Not all camp details are allowed to be edited.
	 * If future versions require, allowing more camp details to be edited is a simple one liner
	 * Except for camp name, since camp name is also used as an additional layer of data integrity check
	 * Editing the camp name would require editing the camp name in all user camp lists
	 * @param campid the camp to be edited
     * @return the menu choice
	 */
	public static MenuChoice editCamp(Integer campid) {
		return new MenuChoice(Perms.EDIT_CAMP,"Edit this camp details",	new doEditCampDetails().withcamp(campid));
	}
	/**
	 * MenuChoice instance denoting the choice of toggling the visibility of a camp 
	 * <p>
	 * This is either from true to false, or false to true, depending on current visibility state
	 * Check the camp initialisation for the default visibility state
	 * @see entities.Camp
	 * @param campid the camp to have its visibility toggled
     * @return the menu choice
	 */
	public static MenuChoice toggleVisibility(Integer campid) {
		return new MenuChoice(Perms.EDIT_CAMP,	"Toggle camp visibility",	new doToggleVisibility().withcamp(campid));
	}
	/**
	 * MenuChoice instance denoting the choice of viewing one's own enquiries
	 * <p>
	 * This can either be for a particular camp, or in general
	 * It is implied both the user and menu know if its for a particular camp, or in general due to past choice selection
	 * @param userid the user to filter enquiries by
     * @return the menu choice
	 */
	public static MenuChoice viewOwnEnquiry(String userid) {
		return new MenuChoice(Perms.SUBMIT_CAMP_ENQUIRY,	"View your own enquiries",	new queryEnquiriesMenu().withowner(userid));
	}
	/**
	 * MenuChoice instance denoting the choice of viewing all enquiries associated with a particular camp
	 * <p>
	 * As there is currently no permission associated with viewing all enquiries across camps, there is no current need to consider that option
	 * If one wishes to allow users to do so, they would have to declare a new permission
	 * @see Perms
	 * They would also need to ensure both intra and intercamp possibilities are handled
	 * @see camsAction.queryEnquiriesMenu
	 */
	public static MenuChoice viewAllEnquiry	= new MenuChoice(Perms.VIEW_CAMP_ENQUIRY,"View all enquiries",new queryEnquiriesMenu());
	/**
	 * MenuChoice instance denoting the choice of submitting an enquiry to a camp
	 * <p>
	 * It is implied both the user and the menu know which camp the enquiry is being submitted for
	 * Usually a user would have already selected a camp before this choice is given
	 * @param campid the id of the camp to be submitted to
     * @return the menu choice
	 */
	public static MenuChoice submitEnquiry(Integer campid) {
		return new MenuChoice(Perms.SUBMIT_CAMP_ENQUIRY,	"Submit a new enquiry",	new doSubmitEnquiry().withcamp(campid));
	}
	/**
	 * MenuChoice instance denoting the choice of viewing one's own suggestions
	 * <p>
	 * This can either be for a particular camp, or in general
	 * It is implied both the user and menu know if its for a particular camp, or in general due to past choice selection
	 * @param userid the user to filter suggestions by
     * @return the menu choice
	 */
	public static MenuChoice viewOwnSuggestion(String userid) {
		return new MenuChoice(Perms.SUBMIT_CAMP_SUGGESTION,		"View your suggestions",	new querySuggestionsMenu().withowner(userid));
	}
	/**
	 * MenuChoice instance denoting the choice of viewing all enquiries associated with a particular camp
	 * <p>
	 * As there is currently no permission associated with viewing all enquiries across camps, there is no current need to consider that option
	 * If one wishes to allow users to do so, they would have to declare a new permission
	 * @see Perms
	 * They would also need to ensure both intra and intercamp possibilities are handled
	 * @see querySuggestionsMenu
	 */
	public static MenuChoice viewAllSuggestion	= new MenuChoice(Perms.VIEW_CAMP_SUGGESTION,		"View all suggestions",		new querySuggestionsMenu());
	/**
	 * MenuChoice instance denoting the choice of submitting a suggestion to a camp
	 * <p>
	 * It is implied both the user and the menu know which camp the suggestion is being submitted for
	 * Usually a user would have already selected a camp before this choice is given
	 * @param campid the camp to submit suggestions to
     * @return the menu choice
	 */
	public static MenuChoice submitSuggestion(Integer campid) {
		return new MenuChoice(Perms.SUBMIT_CAMP_SUGGESTION,		"Submit a new suggestion",	new doSubmitSuggestion().withcamp(campid));
	}
	/**
	 * MenuChoice instance denoting the choice of withdrawing from a camp as an attendee
	 * <p>
	 * It is implied both the user and the menu already know which camp the withdrawal is from
	 * Usually a user would have already selected a camp before this choice is given
	 * As there is currently no permission associated with withdrawing from a camp as a committee member or staff, there is no need to consider other options
	 * If one wishes to allow users to do so, they would have to declare requisite permissions
	 * @see Perms
	 * They would also need to ensure newly affected user groups are handled
	 * @param campid the camp to be withdrawn from
     * @return the menu choice
	 */
	public static MenuChoice withdrawRegistration(Integer campid) {
		return new MenuChoice(Perms.WITHDRAW_AS_ATTENDEE,"Withdraw from this camp",	new doDeleteAttendeeRegistration().withcamp(campid));
	}
	/**
	 * MenuChoice instance denoting the choice of registering for a camp as an attendee.
	 * <p>
	 * It is implied both the user and the menu know which camp the registration is for
	 * Usually a user would have already selected a camp before this choice is given
	 * @param campid the camp the user is registering for
     * @return the menu choice
	 */
	public static MenuChoice registerAsAttendee (Integer campid) {
		return new MenuChoice(Perms.REGISTER_AS_ATTENDEE,"Register as an attendee",	new doSubmitAttendeeRegistration().withcamp(campid));
	}
	/**
	 * MenuChoice instance denoting the choice of registering for a camp as a committee.
	 * <p>
	 * It is implied both the user and the menu know which camp the registration is for
	 * Usually a user would have already selected a camp before this choice is given
	 * @param campid the camp the user is registering for
     * @return the menu choice
	 */
	public static MenuChoice registerAsCommittee(Integer campid) {
		return new MenuChoice(Perms.REGISTER_AS_COMMITTEE,	"Join the camp committee",	new doSubmitCommitteeRegistration().withcamp(campid));
	}
	/**
	 * MenuChoice instance denoting the choice of registering for a camp as a committee.
	 * <p>
	 * It is implied both the user and the menu know which camp the registration is for
	 * Usually a user would have already selected a camp before this choice is given
	 * @param campid the camp to generate the report for
     * @return the menu choice
	 */
	public static MenuChoice generateAttendanceList(Integer campid) {
		return new MenuChoice(Perms.REPLY_CAMP_ENQUIRY,"Generate attendance list",GenerateAttendanceList(campid).withcamp(campid));
	}
	/**
	 * MenuChoice instance denoting the choice of generating an attendance list of attendees.
	 * <p>
	 * It is implied both the user and the menu know which camp the list is for
	 * Usually a user would have already selected a camp before this choice is given
	 * @param campid the camp to generate the report for 
     * @return the menu choice
	 */
	public static MenuChoice generateForAttendee(Integer campid) {
		return new MenuChoice(Perms.DEFAULT,"Generate for only attendees",new doGenerateAttendanceList().withcamp(campid).withroles(EnumSet.of(Role.ATTENDEE)));
	}
	/**
	 * MenuChoice instance denoting the choice of generating an attendance list of camp committee members.
	 * <p>
	 * It is implied both the user and the menu know which camp the list is for
	 * Usually a user would have already selected a camp before this choice is given
	 * @param campid the camp to generate the report for 
     * @return the menu choice
	 */
	public static MenuChoice generateForCommittee(Integer campid) {
		return new MenuChoice(Perms.DEFAULT,"Generate for only camp committee members",new doGenerateAttendanceList().withcamp(campid).withroles(EnumSet.of(Role.COMMITTEE)));
	}
	/**
	 * MenuChoice instance denoting the choice of registering for all participants.
	 * <p>
	 * It is implied both the user and the menu know which camp the list is for
	 * Usually a user would have already selected a camp before this choice is given
	 * @param campid the camp to generate the report for 
     * @return the menu choice
	 */
	public static MenuChoice generateForBoth(Integer campid) {
		return new MenuChoice(Perms.DEFAULT,"Generate for both attendees and camp committee members",new doGenerateAttendanceList().withcamp(campid).withroles(EnumSet.of(Role.COMMITTEE,Role.ATTENDEE)));
	}
	/**
	 * MenuChoice instance denoting the choice of generating a performance report.
	 * <p>
	 * It is implied both the user and the menu know which camp the report is for
	 * Usually a user would have already selected a camp before this choice is given
	 * @param campid the camp to generate the report for 
     * @return the menu choice
	 */
	public static MenuChoice generatePerformanceReport(Integer campid) {
		return new MenuChoice(Perms.APPROVE_CAMP_SUGGESTION,"Generate performance report", new doGeneratePerformanceReport().withcamp(campid));
	}
	
	/**
     * @return the menu choice
	 */
	public static MenuChoice changePassword() {
		return new MenuChoice(Perms.DEFAULT,"Change your password", new doChangePassword());
	}
	/**
	 * @param suggestionid the suggestion to be rejected
     * @return the menu choice
	 */
	public static MenuChoice rejectSuggestion(Integer suggestionid) {
		return new MenuChoice(Perms.APPROVE_CAMP_SUGGESTION,"Reject this suggestion",new doRejectSuggestion().withsuggestion(suggestionid));
	}

	/**
	 * List of menu choices to be used for the single suggestion menu
	 * <p>
	 * Users will be given these choices once they have selected a suggestion
	 * @param suggestionid the suggestion this menu relates to
     * @return the list of initialised choices constructed via factory
	 */
	private static List<MenuChoice>singlesuggestionchoice(int suggestionid){ 
		return 	Arrays.asList(
			approveSuggestion(suggestionid),
			editSuggestion(suggestionid),
			deleteSuggestion(suggestionid),
			rejectSuggestion(suggestionid)
	);}
		
	/**
	 * List of menu choices to be used for the single enquiry menu
	 * Users will be given these choices once they have selected an enquiry
	 * @param enquiryid the enquiry this menu relates to
	 * @return the list of initialised choices constructed via factory
	 */
	private static List<MenuChoice> singleenquirychoices(int enquiryid){
		return Arrays.asList(
			editEnquiry(enquiryid),
			deleteEnquiry(enquiryid),
			submitReply(enquiryid)
		);
	}
	/**
	 * List of menu choices to be used for the start menu
	 * Users will be given these choices immediately after logging in
	 * @param userid the current user
	 * @return the list of initialised choices constructed via factory
	 */
	private static List<MenuChoice> startmenuchoices(String userid){
		return Arrays.asList(
			createCamp,
			viewAllCamps,
			viewOwnCamps(userid),
			viewOwnSuggestion(userid),
			viewOwnEnquiry(userid),
			changePassword()
	);}
	/**
	 * List of menu choices to be used for the own camp menu
	 * Users will be given these choices if they have selected a camp on their camp list
	 * @param campid the camp the menu is about
	 * @param userid the current user, as we need to check perms for subsequent menu traversal
	 * @return the list of initialised choices constructed via factory
	 */
	private static List<MenuChoice> owncampchoices(Integer campid,String userid){
		return Arrays.asList(
		deleteCamp(campid),
		editCamp(campid),
		toggleVisibility(campid),
		viewOwnEnquiry(userid),
		viewAllEnquiry,
		submitEnquiry(campid),
		viewAllSuggestion,
		viewOwnSuggestion(userid),
		submitSuggestion(campid),
		withdrawRegistration(campid),
		generatePerformanceReport(campid),
		generateAttendanceList(campid)
	);}
	/**
	 * List of menu choices to be used for the other camp menu
	 * Users will be given these choices if they have selected a camp not on their camp list
	 * @param campid the camp the menu is about
	 * @param userid the current user, as we need to check perms for subsequent menu traversal
	 * @return the list of initialised choices constructed via factory
	 */
	private static List<MenuChoice> othercampchoices(Integer campid, String userid){
		return Arrays.asList(
			registerAsAttendee(campid),
			registerAsCommittee(campid),
			viewOwnEnquiry(userid),
			submitEnquiry(campid)
		);
	}
	/**
	 * @param campid the camp to generate an attendance list of
	 * @return the list of initialised choices constructed via factory
	 */
	private static List<MenuChoice> attendancelistchoices(Integer campid){
		return Arrays.asList(
			generateForAttendee(campid),
			generateForCommittee(campid),
			generateForBoth(campid)
	);}
	/**
	 * Denotes a Static Menu instance representing the menu users first see.
	 * @see startmenuchoices
	 * @param userid the current user
	 * @return the entire menu with initialised choices, as well as the interaction if none of the choices are selected
	 */
	public static StaticMenu startmenu(String userid) {
		return new StaticMenu("Welcome! What would you like to do", startmenuchoices(userid), new doNothing());
	}
	/**
	 * Denotes a Static Menu instance representing the menu users see after they have selected a camp on their camp list
	 * @see owncampchoices
	 * @param campid the camp the menu is about
	 * @param userid the current user, as we need to check perms for subsequent menu traversal
	 * @return the entire menu with initialised choices, as well as the interaction if none of the choices are selected
	 */
	public static StaticMenu OwnCampMenu(Integer campid, String userid) {
		return new StaticMenu("What would you like to do", owncampchoices(campid,userid),new queryCampsMenu());
	}
	/**
	 * Denotes a Static Menu instance representing the menu users see after they have selected a camp not on their camp list
	 * @see othercampchoices
	 * @param campid the camp the menu is about
	 * @param userid the current user, as we need to check perms for subsequent menu traversal
	 * @return the entire menu with initialised choices, as well as the interaction if none of the choices are selected
	 */
	public static StaticMenu OtherCampMenu(Integer campid,String userid) {
		return new StaticMenu("What would you like to do",othercampchoices(campid,userid),new queryCampsMenu());
	}
	/**
	 * Denotes a Static Menu instance representing the menu users see after they have selected a suggestion
	 * @see singlesuggestionchoice
	 * @param suggestionid the suggestion the menu relates to
	 * @return the entire menu with initialised choices, as well as the interaction if none of the choices are selected
	 */
	public static StaticMenu SingleSuggestionMenu(int suggestionid) {
		return new StaticMenu("What would you like to do with this suggestion?",singlesuggestionchoice(suggestionid),new querySuggestionsMenu());
	}
	/**
	 * Denotes a Static Menu instance representing the menu users see after they have selected an enquiry
	 * @param enquiryid the enquiry the menu relates to
	 * @return the entire menu with initialised choices, as well as the interaction if none of the choices are selected
	 */
	public static StaticMenu SingleEnquiryMenu(int enquiryid) {
		return new StaticMenu("What would you like to do with this enquiry?",singleenquirychoices(enquiryid), new queryEnquiriesMenu());
	}
	/**
	 * Denotes a static menu instance representing the menu users see after they click generate attendance list.<p>
	 * Asks more specifically which user groups to be included.
	 * @param campid the camp to generate the report of
	 * @return the entire menu with initialised choices, as well as the interaction if none of the choices are selected
	 */
	public static StaticMenu GenerateAttendanceList(int campid) {
		return new StaticMenu("Who would you like to generate an attendence list of?",attendancelistchoices(campid), new queryCampsMenu());
	}
}
