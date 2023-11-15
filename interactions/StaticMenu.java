package interactions;
import java.util.List;

import camsAction.MissingRequestedDataException;
import controllers.ControllerItemMissingException;
import controllers.ControllerParamsException;
import entities.UserInfoMissingException;

public final class StaticMenu extends UserMenu{
	public StaticMenu(String title,List<MenuChoice> choice) {
		this.message=title;
		this.choices=choice;
	}
	@Override
	public final Boolean run() throws UserInfoMissingException, ControllerItemMissingException, ControllerParamsException{
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
