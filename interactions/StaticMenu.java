package interactions;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import types.Perms;

public final class StaticMenu extends UserMenu{
	public StaticMenu(String title,List<MenuChoice> choice) {
		this.message=title;
		this.choices=choice;
	}
	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception{
		if(!data.containsKey("UserPerms")) throw new Exception("User has no permissions! Menu cannot load without permissions.");
		if(!EnumSet.class.isInstance(data.get("UserPerms"))) 
			throw new Exception("User Permission format is invalid.");
		@SuppressWarnings("unchecked")
		EnumSet<Perms> userperms=(EnumSet<Perms>) data.get("UserPerms");
		this.givechoices(userperms,data);
		return true;
	}
}
