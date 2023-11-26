package interactions;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import camsAction.MissingRequestedDataException;
import controllers.Controller;
import entities.UserInfoMissingException;
import types.Perms;

/**
 * An interaction that when run, displays a menu to the user.
 * Contains a list of choices that make up the menu and some auxiliary functions
 * to allow behaviour customisation.
 */
public abstract class UserMenu extends Interaction {
	/**
	 * A list of menu choices to be run.
	 * This has to be populated before any choices can be given, and on run() all
	 * menus first populate this choices before taking any other action.
	 */
	protected List<MenuChoice> choices;

	/**
	 * The function called to populate the list of choices.
	 * <p>
	 * The general format of run is populate, givechoices, set tags.
	 * This function makes it easier to define what the menu does.
	 * 
	 * @param currentuser the current user
	 * @param s           the scanner used for the app
	 * @param control     the container for each of the five active control
	 *                    implementation instances
	 * @throws MissingRequestedDataException if the current user cannot be found.
	 * @throws UserInfoMissingException      if the current user info is missing.
	 */
	protected abstract void populate(String currentuser, Scanner s, Controller control)
			throws MissingRequestedDataException, UserInfoMissingException;

	/**
	 * 
	 */
	/**
	 * @param currentuser the current user
	 * @param s           the scanner used for the app
	 * @param control     the container for each of the five active control
	 *                    implementation instances
	 *                    Offers users whatever choice exists in choices, + back,
	 *                    which causes this interaction to return to its previous
	 *                    caller.
	 *                    <p>
	 *                    Only shows the choices users have permission to see.
	 * @return the index in choices corresponding to the choice the user picked
	 * @throws UserInfoMissingException if the current user does not have a valid
	 *                                  id.
	 */
	protected final int givechoices(String currentuser, Scanner s, Controller control) throws UserInfoMissingException {

		EnumSet<Perms> userperm = control.User().grantPerms(currentuser, EnumSet.noneOf(Perms.class));

		while (true) {
			int selected;
			int startcounter = 1;
			int realindex = 0;
			HashMap<Integer, Integer> TLB = new HashMap<Integer, Integer>();
			for (MenuChoice choice : choices) {
				if (userperm.contains(choice.perms())) {
					TLB.put(startcounter, realindex);
					System.out.printf("%d: %s\n", startcounter++, choice.text());
				}
				realindex++;
			}
			TLB.put(startcounter, -1);
			System.out.printf("%d: %s\n", startcounter++, "Back");
			selected = s.nextInt();
			s.nextLine();
			if (TLB.keySet().contains(selected))
				return TLB.get(selected);
		}
	}
}
