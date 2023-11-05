package interactions;
import java.util.HashMap;
import java.util.Set;

import types.Perms;

public class StaticMenu extends UserMenu{
	public StaticMenu(String title,MenuChoice choice[]) {
		this.message=title;
		this.choices=choice;
	}
	public final boolean run(HashMap<String, Object> data) throws Exception{
		Set<Perms> userperms;
		if(!data.containsKey("UserPerms")) throw new Exception("User has no permissions! Menu cannot load without permissions.");
		try {userperms = (Set<Perms>) data.get("UserPerms");}
		catch(ClassCastException e) {
			throw new Exception("User Permission format is invalid.");
		}
		this.givechoices(userperms,data);
		return true;
	}
}
