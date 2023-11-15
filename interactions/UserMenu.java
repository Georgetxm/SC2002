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

public abstract class UserMenu extends Interaction{
	protected List<MenuChoice> choices;
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
	protected final void checkandrun(int choice) throws UserInfoMissingException,MissingRequestedDataException{
		if(choice>=0) choices.get(choice).action().run();
	}
}
