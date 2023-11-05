package cams;
import interactions.StaticMenu;
import camsAction.doNothing;
import interactions.MenuChoice;
import types.Perms;
	

public class CamsInteraction{
	private static MenuChoice viewAllCampsChoice = new MenuChoice(Perms.DEFAULT, "View All Camps", new doNothing());
	private static MenuChoice startmenuchoices[] = {viewAllCampsChoice};
	public static StaticMenu startmenu = new StaticMenu("Welcome! What would you like to do", startmenuchoices);
}
