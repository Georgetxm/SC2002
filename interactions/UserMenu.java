package interactions;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

import entities.Data;
import types.Perms;

public abstract class UserMenu extends Interaction{
	protected List<MenuChoice> choices;
	protected final int givechoices() throws Exception {
		if(!Data.containsKey("UserPerms")) throw new Exception("User has no permissions! Menu cannot load without permissions.");
		if(!EnumSet.class.isInstance(Data.get("UserPerms"))) 
			throw new Exception("User Permission format is invalid.");
		@SuppressWarnings("unchecked")
		EnumSet<Perms> userperm=(EnumSet<Perms>) Data.get("UserPerms");
		
		int selected;
		int startcounter = 1;
		while(true) {
			for(MenuChoice choice: choices) {
				if(userperm.contains(choice.perms())) System.out.printf("%d: %s\n", startcounter++,choice.text());
			}
			System.out.printf("%d: %s\n", startcounter++,"Back");
			Scanner s=getScanner();
			selected = s.nextInt();
			if(selected==choices.size()+1) {
				return -1;
			}
			else if(selected<=choices.size() && selected > 0) return selected-1;
		}
	}
	protected final void checkandrun(int choice) throws Exception {
		if(choice>=0) choices.get(choice).action().run();
	}
}
