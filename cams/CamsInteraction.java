package cams;
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
import camsAction.queryOwnEnquriesMenu;
import camsAction.queryOwnSuggestionsMenu;
import interactions.MenuChoice;
import types.Perms;
/**
 * 
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
	public static MenuChoice viewAllCamps 	= new MenuChoice(Perms.DEFAULT, "View All Camps", new queryAllCampsMenu());
	/**
     * MenuChoice instance denoting the choice of viewing camps stored in their own camp list 
     * <p>
     * This can be either participating camps for students, or managing camps for staff
     */
	public static MenuChoice viewOwnCamps		= new MenuChoice(Perms.DEFAULT,						"View your own camps",		new queryOwnCampsMenu());
	/**
     * MenuChoice instance denoting the choice of approving a specific provided camp suggestion
     * <p>
     * It is implied the menu and user know what suggestion is being approved
     * Usually a user would have already selected a suggestion before this choice is given
     */	
	public static MenuChoice approveSuggestion	= new MenuChoice(Perms.APPROVE_CAMP_SUGGESTION, 	"Approve Suggestion", 		new doApproveSuggestion());
	/**
	 * MenuChoice instance denoting the choice of replying to a specific provided enquiry on the camp
	 * <p>
	 * It is implied the menu and user know what enquiry is being approved
     * Usually a user would have already selected a enquiry before this choice is given
	 */
	public static MenuChoice 	submitReply			= new MenuChoice(Perms.REPLY_CAMP_ENQUIRY,			"Reply to this enquiry",	new doSubmitReply());
	/**
	 * MenuChoice instance denoting the choice of editing a specific provided camp suggestion
	 * <p>
     * It is implied the menu and user know what suggestion is being edited
     * Usually a user would have already selected a suggestion before this choice is given
	 */
	public static MenuChoice 	editSuggestion		= new MenuChoice(Perms.EDIT_CAMP_SUGGESTION,		"Edit this suggestion",		new doEditSuggestion());
	/**
	 * MenuChoice instance denoting the choice of deleting a specific provided camp suggestion
	 * <p>
     * It is implied the menu and user know what suggestion is being deleted
     * Usually a user would have already selected a suggestion before this choice is given
	 */
	public static MenuChoice 	deleteSuggestion	= new MenuChoice(Perms.DELETE_CAMP_SUGGESTION,		"Delete this suggestion",	new doDeleteSuggestion());
	/**
	 * MenuChoice instance denoting the choice of editing a specific provided enquiry on the camp
	 * <p>
	 * It is implied the menu and user know what enquiry is being edited
     * Usually a user would have already selected a enquiry before this choice is given
	 */
	public static MenuChoice editEnquiry			= new MenuChoice(Perms.SUBMIT_CAMP_ENQUIRY,			"Edit this enquiry",		new doEditEnquiry());
	/**
	 * MenuChoice instance denoting the choice of replying to a specific provided enquiry on the camp
	 * <p>
	 * It is implied the menu and user know what enquiry is being approved
     * Usually a user would have already selected a enquiry before this choice is given
	 */
	public static MenuChoice deleteEnquiry		= new MenuChoice(Perms.SUBMIT_CAMP_ENQUIRY,			"Delete this enquiry",		new doDeleteEnquiry());
	/**
	 * MenuChoice instance denoting the choice of creating an entire new camp
	 */
	public static MenuChoice createCamp			= new MenuChoice(Perms.CREATE_CAMP,					"Create a new camp",		new doSubmitCamp());
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
	public static MenuChoice removeCampFilter	= new MenuChoice(Perms.DEFAULT, 					"Remove Filter",			new queryAllCampsMenu());
	/**
	 * MenuChoice instance denoting the choice of deleting a camp.
	 * <p>
	 * It is assumed this choice includes resolving all consequences as well, like removing the camp from students list of camps
	 * It is assumed the points earned via past enquiries and suggestions will be retained
	 * This is because this coder believes the camp being deleted does not devalue work the committee already spent on the camp
	 */
	public static MenuChoice deleteCamp			= new MenuChoice(Perms.DELETE_CAMP,					"Delete this camp",			new doDeleteCamp());
	/**
	 * MenuChoice instance denoting the choice of editing camp details
	 * <p>
	 * Not all camp details are allowed to be edited.
	 * If future versions require, allowing more camp details to be edited is a simple one liner
	 * Except for camp name, since camp name is also used as an additional layer of data integrity check
	 * Editing the camp name would require editing the camp name in all user camp lists
	 */
	public static MenuChoice editCamp			= new MenuChoice(Perms.EDIT_CAMP,					"Edit this camp details",	new doEditCampDetails());
	/**
	 * MenuChoice instance denoting the choice of toggling the visibility of a camp 
	 * <p>
	 * This is either from true to false, or false to true, depending on current visibility state
	 * Check the camp initialisation for the default visibility state
	 * @see entities.Camp
	 */
	public static MenuChoice toggleVisibility	= new MenuChoice(Perms.EDIT_CAMP,					"Toggle camp visibility",	new doToggleVisibility());
	/**
	 * MenuChoice instance denoting the choice of viewing one's own enquiries
	 * <p>
	 * This can either be for a particular camp, or in general
	 * It is implied both the user and menu know if its for a particular camp, or in general due to past choice selection
	 */
	public static MenuChoice viewOwnEnquiry		= new MenuChoice(Perms.SUBMIT_CAMP_ENQUIRY,			"View your own enquiries",	new queryOwnEnquriesMenu());
	/**
	 * MenuChoice instance denoting the choice of viewing all enquiries associated with a particular camp
	 * <p>
	 * As there is currently no permission associated with viewing all enquiries across camps, there is no current need to consider that option
	 * If one wishes to allow users to do so, they would have to declare a new permission
	 * @see Perms
	 * They would also need to ensure both intra and intercamp possibilities are handled
	 * @see camsAction.queryOwnEnquiriesMenu
	 */
	public static MenuChoice viewAllEnquiry		= new MenuChoice(Perms.VIEW_CAMP_ENQUIRY,			"View all enquiries",		new queryAllEnquiriesMenu());
	/**
	 * MenuChoice instance denoting the choice of submitting an enquiry to a camp
	 * <p>
	 * It is implied both the user and the menu know which camp the enquiry is being submitted for
	 * Usually a user would have already selected a camp before this choice is given
	 */
	public static MenuChoice submitEnquiry		= new MenuChoice(Perms.SUBMIT_CAMP_ENQUIRY,			"Submit a new enquiry",		new doSubmitEnquiry());
	/**
	 * MenuChoice instance denoting the choice of viewing one's own suggestions
	 * <p>
	 * This can either be for a particular camp, or in general
	 * It is implied both the user and menu know if its for a particular camp, or in general due to past choice selection
	 */
	public static MenuChoice viewOwnSuggestion	= new MenuChoice(Perms.SUBMIT_CAMP_SUGGESTION,		"View your suggestions",	new queryOwnSuggestionsMenu());
	/**
	 * MenuChoice instance denoting the choice of viewing all enquiries associated with a particular camp
	 * <p>
	 * As there is currently no permission associated with viewing all enquiries across camps, there is no current need to consider that option
	 * If one wishes to allow users to do so, they would have to declare a new permission
	 * @see Perms
	 * They would also need to ensure both intra and intercamp possibilities are handled
	 * @see queryOwnSuggestionsMenu
	 */
	public static MenuChoice viewAllSuggestion	= new MenuChoice(Perms.VIEW_CAMP_SUGGESTION,		"View all suggestions",		new queryAllSuggestionsMenu());
	/**
	 * MenuChoice instance denoting the choice of submitting a suggestion to a camp
	 * <p>
	 * It is implied both the user and the menu know which camp the suggestion is being submitted for
	 * Usually a user would have already selected a camp before this choice is given
	 */
	public static MenuChoice submitSuggestion	= new MenuChoice(Perms.SUBMIT_CAMP_SUGGESTION,		"Submit a new suggestion",	new doSubmitSuggestion());
	/**
	 * MenuChoice instance denoting the choice of withdrawing from a camp as an attendee
	 * <p>
	 * It is implied both the user and the menu already know which camp the withdrawal is from
	 * Usually a user would have already selected a camp before this choice is given
	 * As there is currently no permission associated with withdrawing from a camp as a committee member or staff, there is no need to consider other options
	 * If one wishes to allow users to do so, they would have to declare requisite permissions
	 * @see Perms
	 * They would also need to ensure newly affected user groups are handled
	 */
	public static MenuChoice withdrawRegistration= new MenuChoice(Perms.WITHDRAW_AS_ATTENDEE,		"Withdraw from this camp",	new doDeleteAttendeeRegistration());
	/**
	 * MenuChoice instance denoting the choice of registering for a camp as an attendee.
	 * <p>
	 * It is implied both the user and the menu know which camp the registration is for
	 * Usually a user would have already selected a camp before this choice is given
	 */
	public static MenuChoice registerAsAttendee	= new MenuChoice(Perms.REGISTER_AS_ATTENDEE,		"Register as an attendee",	new doSubmitAttendeeRegistration());
	/**
	 * MenuChoice instance denoting the choice of registering for a camp as a committee.
	 * <p>
	 * It is implied both the user and the menu know which camp the registration is for
	 * Usually a user would have already selected a camp before this choice is given
	 */
	public static MenuChoice registerAsCommittee	= new MenuChoice(Perms.REGISTER_AS_COMMITTEE,		"Join the camp committee",	new doSubmitCommitteeRegistration());
	/**
	 * List of menu choices to be used for the single suggestion menu
	 * <p>
	 * Users will be given these choices once they have selected a suggestion
	 */
	private static List<MenuChoice>singlesuggestionchoice = Arrays.asList(
		approveSuggestion,
		editSuggestion,
		deleteSuggestion);
	/**
	 * List of menu choices to be used for the single enquiry menu
	 * Users will be given these choices once they have selected an enquiry
	 */
	private static List<MenuChoice> singleenquirychoices = Arrays.asList(
		editEnquiry,
		deleteEnquiry,
		submitReply
	);
	/**
	 * List of menu choices to be used for the start menu
	 * Users will be given these choices immediately after logging in
	 */
	private static List<MenuChoice> startmenuchoices = Arrays.asList(
		createCamp,
		viewAllCamps,
		viewOwnCamps,
		viewOwnSuggestion,
		viewOwnEnquiry
	);
	/**
	 * List of menu choices to be used for the own camp menu
	 * Users will be given these choices if they have selected a camp on their camp list
	 */
	private static List<MenuChoice> owncampchoices = Arrays.asList(
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
	);
	/**
	 * List of menu choices to be used for the other camp menu
	 * Users will be given these choices if they have selected a camp not on their camp list
	 */
	private static List<MenuChoice> othercampchoices = Arrays.asList(
		registerAsAttendee,
		registerAsCommittee,
		viewOwnEnquiry,
		submitEnquiry
	);
	/**
	 * Denotes a Static Menu instance representing the menu users first see.
	 * @see startmenuchoices
	 */
	public static StaticMenu startmenu = new StaticMenu("Welcome! What would you like to do", startmenuchoices);
	/**
	 * Denotes a Static Menu instance representing the menu users see after they have selected a camp on their camp list
	 * @see owncampchoices
	 */
	public static StaticMenu OwnCampMenu = new StaticMenu("What would you like to do", owncampchoices);
	/**
	 * Denotes a Static Menu instance representing the menu users see after they have selected a camp not on their camp list
	 * @see othercampchoices
	 */
	public static StaticMenu OtherCampMenu = new StaticMenu("What would you like to do",othercampchoices);
	/**
	 * Denotes a Static Menu instance representing the menu users see after they have selected a suggestion
	 * @see singlesuggestionchoice
	 */
	public static StaticMenu SingleSuggestionMenu = new StaticMenu("What would you like to do with this suggestion?",singlesuggestionchoice);
	/**
	 * Denotes a Static Menu instance representing the menu users see after they have selected an enquiry
	 */
	public static StaticMenu SingleEnquiryMenu = new StaticMenu("What would you like to do with this enquiry?",singleenquirychoices);
}
