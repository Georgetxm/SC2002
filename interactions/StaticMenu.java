package interactions;

import java.util.List;
import java.util.Scanner;

import camsAction.MissingRequestedDataException;
import controllers.Controller;
import entities.UserInfoMissingException;

/**
 * Represents user menus where the dependencies and behaviours are defined at
 * compile time.
 * Since the code logic will be the same for all static menus, all menus that
 * are static will be instances of this class.
 * See CamsInteraction, where all static choices and static menus are defined.
 */
public final class StaticMenu extends UserMenu {
	/**
	 * Since choices and the message is statically defined, they will be required to
	 * initialise an instance of this class
	 * 
	 * @param title  the message to be printed at the top before choices are given
	 * @param choice the list of choices offered
	 * @param back   the interaction to return to when the user chooses to go back
	 */
	public StaticMenu(String title, List<MenuChoice> choice, Interaction back) {
		this.message = title;
		this.choices = choice;
		this.next = back;
	}

	@Override
	protected void populate(String currentuser, Scanner s, Controller control) {
	}

	@Override
	public final Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException, MissingRequestedDataException {
		int choice = givechoices(currentuser, s, control);
		if (choice >= 0)
			next = choices.get(choice).action();
		if (this.userid != null)
			next = next.withuser(userid);
		if (this.campid != null)
			next = next.withcamp(campid);
		if (this.suggestionid != null)
			next = next.withsuggestion(suggestionid);
		if (this.enquiryid != null)
			next = next.withenquiry(enquiryid);
		if (this.filters != null)
			next = next.withfilter(filters);
		this.userid = null;
		this.campid = null;
		this.suggestionid = null;
		this.enquiryid = null;
		this.filters = null;
		return next;
	}

}
