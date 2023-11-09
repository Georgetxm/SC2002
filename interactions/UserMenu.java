package interactions;
import java.util.EnumSet;
import java.util.HashMap;
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
			Scanner s=getScanner();
			selected = s.nextInt();
			if(TLB.keySet().contains(selected)) return TLB.get(selected);
		}
	}
	protected final void checkandrun(int choice) throws Exception {
		if(choice>=0) choices.get(choice).action().run();
	}
}
