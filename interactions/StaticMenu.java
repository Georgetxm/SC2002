package interactions;
import java.util.List;

import camsAction.MissingRequestedDataException;
import entities.UserInfoMissingException;

/**
 * Represents user menus where the dependencies and behaviours are defined at compile time.
 * Since the code logic will be the same for all static menus, all menus that are static will be instances of this class.
 * See CamsInteraction, where all static choices and static menus are defined.
 */
public final class StaticMenu extends UserMenu{
	/**
	 * Since choices and the message is statically defined, they will be required to initialise an instance of this class
	 * @param title the message to be printed at the top before choices are given
	 * @param choice the list of choices offered
	 */
	public StaticMenu(String title,List<MenuChoice> choice) {
		this.message=title;
		this.choices=choice;
	}
	/**
	 * Offers users the choice declared, and runs the function associated with the choice the user makes.
	 * The simplest implementation of a user menu.
	 * <p>
	 * 1. choices is populated on instantiation.<p>
	 * 2. The user is given the choices, and picks one<p>
	 * 3. As no flags or data is set, can immediately run the Interaction associated with the choice.<p>
	 * 4. As no flags or data has to be cleared, immediately returns once the selected Interaction returns
	 */
	@Override
	public final Boolean run() throws UserInfoMissingException{
		while(true) {
			System.out.println(this.message);
			int choice = this.givechoices();
			if(choice<0) break;
			try {checkandrun(choice);}
			catch(MissingRequestedDataException e) {
				System.out.println("Ran into an error. Please retry or return to main menu before retrying");
			}
		}
		return true;
	}
}
