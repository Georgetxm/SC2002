package interactions;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import types.Perms;

public abstract class UserMenu extends Interaction{
	protected List<MenuChoice> choices;
	protected final int givechoices(HashMap<String,Object> data) throws Exception {
		if(!data.containsKey("UserPerms")) throw new Exception("User has no permissions! Menu cannot load without permissions.");
		if(!EnumSet.class.isInstance(data.get("UserPerms"))) 
			throw new Exception("User Permission format is invalid.");
		@SuppressWarnings("unchecked")
		EnumSet<Perms> userperm=(EnumSet<Perms>) data.get("UserPerms");
		
		int selected;
		int startcounter = 1;
		while(true) {
			for(MenuChoice choice: choices) {
				if(userperm.contains(choice.perms())) System.out.printf("%d: %s\n", startcounter++,choice.text());
			}
			System.out.printf("%d: %s\n", startcounter++,"Back");
			Scanner s=getScanner(data);
			selected = s.nextInt();
			if(selected==choices.size()+1) {
				break;
			}
			else if(selected<=choices.size() && selected > 0) return startcounter-1;
			startcounter = 1;
		}
		return -1;
	}
	protected final void checkandrun(int choice, HashMap<String,Object> data) throws Exception {
		if(choice>=0) choices.get(choice).action().run(data);
	}
}
