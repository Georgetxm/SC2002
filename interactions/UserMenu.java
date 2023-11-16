package interactions;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import camsAction.MissingRequestedDataException;
import controllers.UserController;
import entities.Data;
import entities.UserInfoMissingException;
import types.Perms;

/**
 * An interaction that when run, displays a menu to the user.
 * Contains a list of choices that make up the menu and some auxiliary functions to allow behaviour customisation.
 */
public abstract class UserMenu extends Interaction{
	/**
	 * A list of menu choices to be run.
	 * This has to be populated before any choices can be given, and on run() all menus first populate this choices before taking any other action.
	 */
	protected List<MenuChoice> choices;
	/**
	 * Offers users whatever choice exists in choices, + back, which causes this interaction to return to its previous caller.
	 * <p>
	 * Only shows the choices users have permission to see.
	 * @return the index in choices corresponding to the choice the user picked
	 * @throws UserInfoMissingException if the current user does not have a valid id.
	 */
	protected final int givechoices() throws UserInfoMissingException {
		if (!Data.containsKey("Controller"))
			throw new NoSuchElementException("No controller found. Request Failed.");
		if (!UserController.class.isInstance(Data.get("Controller")))
			throw new NoSuchElementException("Controller not able enough. Request Failed.");
		Object control = Data.get("Controller");
		
		String userid;
		try{userid = (String) Data.get("CurrentUser");}
		catch(ClassCastException e) {
			throw new UserInfoMissingException("Missing ID from static menu");
		}
		EnumSet<Perms> userperm = ((UserController) control).grantPerms(userid, EnumSet.noneOf(Perms.class));
		
		while(true) {
			int selected;
			int startcounter = 1;
			int realindex = 0;
			HashMap<Integer,Integer> TLB = new HashMap<Integer,Integer>();
			for(MenuChoice choice: choices) {
				if(userperm.contains(choice.perms())) {
					TLB.put(startcounter, realindex);
					System.out.printf("%d: %s\n", startcounter++,choice.text());
				}
				realindex++;
			}
			TLB.put(startcounter, -1);
			System.out.printf("%d: %s\n", startcounter++,"Back");
			Scanner s = getScanner();
			selected = s.nextInt();
			s.nextLine();
			if(TLB.keySet().contains(selected)) return TLB.get(selected);
		}
	}
	/**
	 * Checks to see if the user's choice is valid, and calls the chosen Interaction.
	 * @param choice
	 * @throws UserInfoMissingException if the Interaction to be ran threw it on running.
	 * @throws MissingRequestedDataException if the Interaction to be ran threw it on running.
	 */
	protected final void checkandrun(int choice) throws UserInfoMissingException,MissingRequestedDataException{
		if(choice>=0) choices.get(choice).action().run();
	}
	/**
	 * For all menus, the run sequence is as follow: <p>
	 * 1. Initialise and get ready any data needed to populate choices.<p>
	 * 2. Populate choices.<p>
	 * 3. Give choices.<p>
	 * 4. Based on what the user picked, set tags and data as necessary as user input may affect subsequent Interactions ran.<p>
	 * 5. check and run the chosen Interaction.<p>
	 * 6. Upon chosen Interaction's return, clear tags and data as necessary to give parent Interactions a clean slate if necessary.<p>
	 * 7. Return to parent Interaction (if any, else return to main()).<p>
	 * @throws UserInfoMissingException if ran Interaction threw it
	 * @throws MissingRequestedDataException if ran Interaction threw it
	 */
	protected abstract Object run() throws UserInfoMissingException,MissingRequestedDataException;
}
