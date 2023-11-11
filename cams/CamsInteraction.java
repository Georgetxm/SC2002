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
	 * View all enquiries associated with a particular camp
	 */
	viewAllEnquiry		= new MenuChoice(Perms.VIEW_CAMP_ENQUIRY,			"View all enquiries",		new queryAllEnquiriesMenu()),
	submitEnquiry		= new MenuChoice(Perms.SUBMIT_CAMP_ENQUIRY,			"Submit a new enquiry",		new doSubmitEnquiry()),
	viewOwnSuggestion	= new MenuChoice(Perms.SUBMIT_CAMP_SUGGESTION,		"View your suggestions",	new queryOwnSuggestionsMenu()),
	viewAllSuggestion	= new MenuChoice(Perms.VIEW_CAMP_SUGGESTION,		"View all suggestions",		new queryAllSuggestionsMenu()),
	submitSuggestion	= new MenuChoice(Perms.SUBMIT_CAMP_SUGGESTION,		"Submit a new suggestion",	new doSubmitSuggestion()),
	withdrawRegistration= new MenuChoice(Perms.WITHDRAW_AS_ATTENDEE,		"Withdraw from this camp",	new doDeleteAttendeeRegistration()),
	registerAsAttendee	= new MenuChoice(Perms.REGISTER_AS_ATTENDEE,		"Register as an attendee",	new doSubmitAttendeeRegistration()),
	registerAsCommittee	= new MenuChoice(Perms.REGISTER_AS_COMITTEE,		"Join the camp committee",	new doSubmitCommitteeRegistration());
	private static List<MenuChoice>
	singleSuggestionChoice = Arrays.asList(
		approveSuggestion,
		editSuggestion,
		deleteSuggestion),
	singleenquirychoices = Arrays.asList(
		editEnquiry,
		deleteEnquiry,
		submitReply
	),
	startmenuchoices = Arrays.asList(
		createCamp,
		viewAllCamps,
		viewOwnCamps,
		viewOwnSuggestion
	),
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
	othercampchoices = Arrays.asList(
		registerAsAttendee,
		registerAsCommittee,
		viewOwnEnquiry,
		submitEnquiry
	);
	
	
	public static StaticMenu 
	startmenu = new StaticMenu("Welcome! What would you like to do", startmenuchoices),
	OwnCampMenu = new StaticMenu("What would you like to do", owncampchoices),
	OtherCampMenu = new StaticMenu("What would you like to do",othercampchoices),
	SingleSuggestionMenu = new StaticMenu("What would you like to do with this suggestion?",singleSuggestionChoice),
	SingleEnquiryMenu = new StaticMenu("What would you like to do with this enquiry?",singleenquirychoices);
}
