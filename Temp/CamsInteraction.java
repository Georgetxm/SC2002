package Temp;
import interactions.StaticMenu;

import java.util.Arrays;
import java.util.List;

import camsAction.doApproveSuggestion;
import camsAction.doDeleteAttendeeRegistration;
import camsAction.doDeleteCamp;
import camsAction.doDeleteEnquiry;
import camsAction.doDeleteSuggestion;
import camsAction.doEditSuggestion;
import camsAction.doSubmitAttendeeRegistration;
import camsAction.doSubmitCamp;
import camsAction.doSubmitCommitteeRegistration;
import camsAction.doEditCampDetails;
import camsAction.doEditEnquiry;
import camsAction.doSubmitEnquiry;
import camsAction.doSubmitReply;
import camsAction.doSubmitSuggestion;
import camsAction.doToggleVisibility;
import camsAction.queryFilterCampByMenu;
import camsAction.queryAllCampsMenu;
import camsAction.queryAllEnquiriesMenu;
import camsAction.queryAllSuggestionsMenu;
import camsAction.queryOwnCampsMenu;
import camsAction.queryOwnEnquiryMenu;
import camsAction.queryOwnSuggestionsMenu;
import interactions.MenuChoice;
import types.Perms;
/**
 * Holds static menu choices and static user menus used in Cams
 * 
 * @see MenuChoice
 * @see StaticMenu
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */

/**
 * 
 */
/**
 * 
 */
/**
 * 
 */
public class CamsInteraction{ //Menu choices
	public static MenuChoice 
	/**
     * MenuChoice instance denoting the choice of viewing all camps
     */
	viewAllCamps 		= new MenuChoice(Perms.DEFAULT, 					"View All Camps", 			new queryAllCampsMenu()),
	/**
     * MenuChoice instance denoting the choice of viewing camps stored in their own camp list 
     * This can be either participating camps for students, or managing camps for staff
     */
	viewOwnCamps		= new MenuChoice(Perms.DEFAULT,						"View your own camps",		new queryOwnCampsMenu()),
	/**
     * MenuChoice instance denoting the choice of approving a specific provided camp suggestion
     * It is implied the menu and user know what suggestion is being approved
     * Usually a user would have already selected a suggestion before this choice is given
     */	
	approveSuggestion	= new MenuChoice(Perms.APPROVE_CAMP_SUGGESTION, 	"Approve Suggestion", 		new doApproveSuggestion()),
	/**
	 * MenuChoice instance denoting the choice of replying to a specific provided enquiry on the camp
	 * It is implied the menu and user know what enquiry is being approved
     * Usually a user would have already selected a enquiry before this choice is given
	 */
	submitReply			= new MenuChoice(Perms.REPLY_CAMP_ENQUIRY,			"Reply to this enquiry",	new doSubmitReply()),
	/**
	 * MenuChoice instance denoting the choice of editing a specific provided camp suggestion
     * It is implied the menu and user know what suggestion is being edited
     * Usually a user would have already selected a suggestion before this choice is given
	 */
	editSuggestion		= new MenuChoice(Perms.EDIT_CAMP_SUGGESTION,		"Edit this suggestion",		new doEditSuggestion()),
	/**
	 * MenuChoice instance denoting the choice of deleting a specific provided camp suggestion
     * It is implied the menu and user know what suggestion is being deleted
     * Usually a user would have already selected a suggestion before this choice is given
	 */
	deleteSuggestion	= new MenuChoice(Perms.DELETE_CAMP_SUGGESTION,		"Delete this suggestion",	new doDeleteSuggestion()),
	/**
	 * MenuChoice instance denoting the choice of editing a specific provided enquiry on the camp
	 * It is implied the menu and user know what enquiry is being edited
     * Usually a user would have already selected a enquiry before this choice is given
	 */
	editEnquiry			= new MenuChoice(Perms.SUBMIT_CAMP_ENQUIRY,			"Edit this enquiry",		new doEditEnquiry()),
	/**
	 * MenuChoice instance denoting the choice of replying to a specific provided enquiry on the camp
	 * It is implied the menu and user know what enquiry is being approved
     * Usually a user would have already selected a enquiry before this choice is given
	 */
	deleteEnquiry		= new MenuChoice(Perms.SUBMIT_CAMP_ENQUIRY,			"Delete this enquiry",		new doDeleteEnquiry()),
	/**
	 * MenuChoice instance denoting the choice of creating an entire new camp
	 */
	createCamp			= new MenuChoice(Perms.CREATE_CAMP,					"Create a new camp",		new doSubmitCamp()),
	/**
	 * MenuChoice instance denoting the choice of adding a filter of choice to the existing list of camps viewed
	 * This (currently) leads to a menu where the user gets to choose a filter to add to existing filters
	 */
	filterCampBy		= new MenuChoice(Perms.DEFAULT, 					"Add Filter",				new queryFilterCampByMenu()),
	/**
	 * MenuChoice instance denoting the choice of removing all existing filters to the list of camps viewed.
	 * This list does not include filter by camps owned as that itself is its own choice
	 * @see viewOwnCamps
	 */
	removeCampFilter	= new MenuChoice(Perms.DEFAULT, 					"Remove Filter",			new queryAllCampsMenu()),
	/**
	 * MenuChoice instance denoting the choice of deleting a camp.
	 * It is assumed this choice includes resolving all consequences as well, like removing the camp from students list of camps
	 * It is assumed the points earned via past enquiries and suggestions will be retained
	 * This is because this coder believes the camp being deleted does not devalue work the committee already spent on the camp
	 */
	deleteCamp			= new MenuChoice(Perms.DELETE_CAMP,					"Delete this camp",			new doDeleteCamp()),
	/**
	 * MenuChoice instance denoting the choice of editing camp details
	 * Not all camp details are allowed to be edited.
	 * If future versions require, allowing more camp details to be edited is a simple one liner
	 * Except for camp name, since camp name is also used as an additional layer of data integrity check
	 * Editing the camp name would require editing the camp name in all user camp lists
	 */
	editCamp			= new MenuChoice(Perms.EDIT_CAMP,					"Edit this camp details",	new doEditCampDetails()),
	/**
	 * MenuChoice instance denoting the choice of toggling the visibility of a camp 
	 * This is either from true to false, or false to true, depending on current visibility state
	 * Check the camp initialisation for the default visibility state
	 * @see Camp
	 */
	toggleVisibility	= new MenuChoice(Perms.EDIT_CAMP,					"Toggle camp visibility",	new doToggleVisibility()),
	/**
	 * MenuChoice instance denoting the choice of viewing one's own enquiries
	 * This can either be for a particular camp, or in general
	 * It is implied both the user and menu know if its for a particular camp, or in general due to past choice selection
	 */
	viewOwnEnquiry		= new MenuChoice(Perms.SUBMIT_CAMP_ENQUIRY,			"View your own enquiries",	new queryOwnEnquiryMenu()),
	/**
	 * MenuChoice instance denoting the choice of viewing all enquiries associated with a particular camp
	 * As there is currently no permission associated with viewing all enquiries across camps, there is no current need to consider that option
	 * If one wishes to allow users to do so, they would have to declare a new permission
	 * @see Perms
	 * They would also need to ensure both intra and intercamp possibilities are handled
	 * @see queryOwnEnquiryMenu
	 */
	viewAllEnquiry		= new MenuChoice(Perms.VIEW_CAMP_ENQUIRY,			"View all enquiries",		new queryAllEnquiriesMenu()),
	/**
	 * MenuChoice instance denoting the choice of submitting an enquiry to a camp
	 * It is implied both the user and the menu know which camp the enquiry is being submitted for
	 * Usually a user would have already selected a camp before this choice is given
	 */
	submitEnquiry		= new MenuChoice(Perms.SUBMIT_CAMP_ENQUIRY,			"Submit a new enquiry",		new doSubmitEnquiry()),
	/**
	 * MenuChoice instance denoting the choice of viewing one's own suggestions
	 * This can either be for a particular camp, or in general
	 * It is implied both the user and menu know if its for a particular camp, or in general due to past choice selection
	 */
	viewOwnSuggestion	= new MenuChoice(Perms.SUBMIT_CAMP_SUGGESTION,		"View your suggestions",	new queryOwnSuggestionsMenu()),
	/**
	 * MenuChoice instance denoting the choice of viewing all enquiries associated with a particular camp
	 * As there is currently no permission associated with viewing all enquiries across camps, there is no current need to consider that option
	 * If one wishes to allow users to do so, they would have to declare a new permission
	 * @see Perms
	 * They would also need to ensure both intra and intercamp possibilities are handled
	 * @see queryOwnSuggestionsMenu
	 */
	viewAllSuggestion	= new MenuChoice(Perms.VIEW_CAMP_SUGGESTION,		"View all suggestions",		new queryAllSuggestionsMenu()),
	/**
	 * MenuChoice instance denoting the choice of submitting a suggestion to a camp
	 * It is implied both the user and the menu know which camp the suggestion is being submitted for
	 * Usually a user would have already selected a camp before this choice is given
	 */
	submitSuggestion	= new MenuChoice(Perms.SUBMIT_CAMP_SUGGESTION,		"Submit a new suggestion",	new doSubmitSuggestion()),
	/**
	 * MenuChoice instance denoting the choice of withdrawing from a camp as an attendee
	 * It is implied both the user and the menu already know which camp the withdrawal is from
	 * Usually a user would have already selected a camp before this choice is given
	 * As there is currently no permission associated with withdrawing from a camp as a committee member or staff, there is no need to consider other options
	 * If one wishes to allow users to do so, they would have to declare requisite permissions
	 * @see Perms
	 * They would also need to ensure newly affected user groups are handled
	 */
	withdrawRegistration= new MenuChoice(Perms.WITHDRAW_AS_ATTENDEE,		"Withdraw from this camp",	new doDeleteAttendeeRegistration()),
	/**
	 * MenuChoice instance denoting the choice of registering for a camp as an attendee.
	 * It is implied both the user and the menu know which camp the registration is for
	 * Usually a user would have already selected a camp before this choice is given
	 */
	registerAsAttendee	= new MenuChoice(Perms.REGISTER_AS_ATTENDEE,		"Register as an attendee",	new doSubmitAttendeeRegistration()),
	/**
	 * MenuChoice instance denoting the choice of registering for a camp as a committee.
	 * It is implied both the user and the menu know which camp the registration is for
	 * Usually a user would have already selected a camp before this choice is given
	 */
	registerAsCommittee	= new MenuChoice(Perms.REGISTER_AS_COMITTEE,		"Join the camp committee",	new doSubmitCommitteeRegistration());
	private static List<MenuChoice>
	/**
	 * List of menu choices to be used for the single suggestion menu
	 * Users will be given these choices once they have selected a suggestion
	 */
	singlesuggestionchoice = Arrays.asList(
		approveSuggestion,
		editSuggestion,
		deleteSuggestion),
	/**
	 * List of menu choices to be used for the single enquiry menu
	 * Users will be given these choices once they have selected an enquiry
	 */
	singleenquirychoices = Arrays.asList(
		editEnquiry,
		deleteEnquiry,
		submitReply
	),
	/**
	 * List of menu choices to be used for the start menu
	 * Users will be given these choices immediately after logging in
	 */
	startmenuchoices = Arrays.asList(
		createCamp,
		viewAllCamps,
		viewOwnCamps,
		viewOwnSuggestion
	),
	/**
	 * List of menu choices to be used for the own camp menu
	 * Users will be given these choices if they have selected a camp on their camp list
	 */
	owncampchoices = Arrays.asList(
		deleteCamp,
		editCamp,
		toggleVisibility,
		viewOwnEnquiry,
		viewAllEnquiry,
		submitEnquiry,
		viewAllSuggestion,
		viewOwnSuggestion,
		submitSuggestion,
		withdrawRegistration
	),
	/**
	 * List of menu choices to be used for the other camp menu
	 * Users will be given these choices if they have selected a camp not on their camp list
	 */
	othercampchoices = Arrays.asList(
		registerAsAttendee,
		registerAsCommittee,
		viewOwnEnquiry,
		submitEnquiry
	);
	
	
	public static StaticMenu 
	/**
	 * Denotes a Static Menu instance representing the menu users first see.
	 * @see startmenuchoices
	 */
	startmenu = new StaticMenu("Welcome! What would you like to do", startmenuchoices),
	/**
	 * Denotes a Static Menu instance representing the menu users see after they have selected a camp on their camp list
	 * @see owncampchoices
	 */
	OwnCampMenu = new StaticMenu("What would you like to do", owncampchoices),
	/**
	 * Denotes a Static Menu instance representing the menu users see after they have selected a camp not on their camp list
	 * @see othercampchoices
	 */
	OtherCampMenu = new StaticMenu("What would you like to do",othercampchoices),
	/**
	 * Denotes a Static Menu instance representing the menu users see after they have selected a suggestion
	 * @see singlesuggestionchoice
	 */
	SingleSuggestionMenu = new StaticMenu("What would you like to do with this suggestion?",singlesuggestionchoice),
	/**
	 * Denotes a Static Menu instance representing the menu users see after they have selected an enquiry
	 */
	SingleEnquiryMenu = new StaticMenu("What would you like to do with this enquiry?",singleenquirychoices);
}
