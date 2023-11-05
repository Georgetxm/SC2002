package interactions;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import core.Utility;
import types.Perms;

public abstract class UserMenu extends Interaction{
	protected MenuChoice choices[];
	protected final boolean givechoices(Set<Perms> userperm, HashMap<String,Object> data) throws Exception {
		System.out.println(this.message);
		int selected;
		int startcounter = 1;
		while(true) {
			for(MenuChoice choice: choices) {
				if(userperm.contains(choice.perms())) System.out.printf("%d: %s\n", startcounter++,choice.text());
			}
			System.out.printf("%d: %s\n", startcounter++,"Back");
			Scanner s=Utility.getScanner(data);
			selected = s.nextInt();
			if(selected==choices.length+1) {
				break;
			}
			else if(selected<=choices.length && selected > 0) choices[selected-1].action().run(data);
			startcounter = 1;
		}
		return true;
	}
}
