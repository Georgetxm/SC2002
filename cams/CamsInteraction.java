package cams;
import interactions.StaticMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import camsAction.doApproveSuggestion;
import camsAction.doDeleteSuggestion;
import camsAction.doEditSuggestion;
import camsAction.doNothing;
import interactions.MenuChoice;
import types.Perms;
	

public class CamsInteraction{
	//Menu choices
	private static MenuChoice 
	viewAllCamps 		= new MenuChoice(Perms.DEFAULT, 					"View All Camps", 			new doNothing()),
	approveSuggestion	= new MenuChoice(Perms.APPROVE_CAMP_SUGGESTION, 	"Approve Suggestion", 		new doApproveSuggestion()),
	editSuggestion		= new MenuChoice(Perms.EDIT_CAMP_SUGGESTION,		"Edit this suggestion",		new doEditSuggestion()),
	deleteSuggestion	= new MenuChoice(Perms.DELETE_CAMP_SUGGESTION,		"Delete this suggestion",	new doDeleteSuggestion());
	
	static List<MenuChoice>
	singleSuggestionChoice = Arrays.asList(
		approveSuggestion,
		editSuggestion,
		deleteSuggestion),
	startmenuchoices = Arrays.asList(
		viewAllCamps
	);
	
	
	public static StaticMenu 
	startmenu = new StaticMenu("Welcome! What would you like to do", startmenuchoices),
	SingleSuggestionMenu = new StaticMenu("What would you like to do with this suggestion?",singleSuggestionChoice);
}
