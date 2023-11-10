package cams;
import interactions.StaticMenu;

import java.util.Arrays;
import java.util.List;

import camsAction.doApproveSuggestion;
import camsAction.doDeleteCamp;
import camsAction.doDeleteSuggestion;
import camsAction.doEditSuggestion;
import camsAction.doSubmitAttendeeRegistration;
import camsAction.doSubmitCamp;
import camsAction.doSubmitCommitteeRegistration;
import camsAction.doEditCampDetails;
import camsAction.doSubmitEnquiry;
import camsAction.doSubmitSuggestion;
import camsAction.queryFilterCampByMenu;
import camsAction.queryViewAllCampsMenu;
import camsAction.queryViewAllEnquiryMenu;
import camsAction.queryViewAllSuggestionsMenu;
import camsAction.queryViewOwnCampsMenu;
import camsAction.queryViewOwnEnquiryMenu;
import camsAction.queryViewOwnSuggestionsMenu;
import interactions.MenuChoice;
import types.Perms;
	

public class CamsInteraction{
	//Menu choices
	public static MenuChoice 
	viewAllCamps 		= new MenuChoice(Perms.DEFAULT, 					"View All Camps", 			new queryViewAllCampsMenu()),
	viewOwnCamps		= new MenuChoice(Perms.DEFAULT,						"View your own camps",		new queryViewOwnCampsMenu()),
	approveSuggestion	= new MenuChoice(Perms.APPROVE_CAMP_SUGGESTION, 	"Approve Suggestion", 		new doApproveSuggestion()),
	editSuggestion		= new MenuChoice(Perms.EDIT_CAMP_SUGGESTION,		"Edit this suggestion",		new doEditSuggestion()),
	deleteSuggestion	= new MenuChoice(Perms.DELETE_CAMP_SUGGESTION,		"Delete this suggestion",	new doDeleteSuggestion()),
	createCamp			= new MenuChoice(Perms.CREATE_CAMP,					"Create a new camp",		new doSubmitCamp()),
	filterCampBy		= new MenuChoice(Perms.DEFAULT, 					"Add Filter",				new queryFilterCampByMenu()),
	removeCampFilter	= new MenuChoice(Perms.DEFAULT, 					"Remove Filter",			new queryViewAllCampsMenu()),
	deleteCamp			= new MenuChoice(Perms.DELETE_CAMP,					"Delete this camp",			new doDeleteCamp()),
	editCamp			= new MenuChoice(Perms.EDIT_CAMP,					"Edit this camp details",	new doEditCampDetails()),
	viewOwnEnquiry		= new MenuChoice(Perms.SUBMIT_CAMP_ENQUIRY,			"View your own enquiries",	new queryViewOwnEnquiryMenu()),
	viewAllEnquiry		= new MenuChoice(Perms.VIEW_CAMP_ENQUIRY,			"View all enquiries",		new queryViewAllEnquiryMenu()),
	submitEnquiry		= new MenuChoice(Perms.SUBMIT_CAMP_ENQUIRY,			"Submit a new enquiry",		new doSubmitEnquiry()),
	viewOwnSuggestion	= new MenuChoice(Perms.SUBMIT_CAMP_SUGGESTION,		"View your suggestions",	new queryViewOwnSuggestionsMenu()),
	viewAllSuggestion	= new MenuChoice(Perms.VIEW_CAMP_SUGGESTION,		"View all suggestions",		new queryViewAllSuggestionsMenu()),
	submitSuggestion	= new MenuChoice(Perms.SUBMIT_CAMP_SUGGESTION,		"Submit a new suggestion",	new doSubmitSuggestion()),
	registerAsAttendee	= new MenuChoice(Perms.REGISTER_AS_ATTENDEE,		"Register as an attendee",	new doSubmitAttendeeRegistration()),
	registerAsCommittee	= new MenuChoice(Perms.REGISTER_AS_COMITTEE,		"Join the camp committee",	new doSubmitCommitteeRegistration());
	private static List<MenuChoice>
	singleSuggestionChoice = Arrays.asList(
		approveSuggestion,
		editSuggestion,
		deleteSuggestion),
	startmenuchoices = Arrays.asList(
		createCamp,
		viewAllCamps,
		viewOwnCamps,
		viewOwnSuggestion
	),
	owncampchoices = Arrays.asList(
		deleteCamp,
		editCamp,
		viewOwnEnquiry,
		viewAllEnquiry,
		submitEnquiry,
		viewAllSuggestion,
		viewOwnSuggestion,
		submitSuggestion
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
	SingleSuggestionMenu = new StaticMenu("What would you like to do with this suggestion?",singleSuggestionChoice);
}
