package interactions;
import java.util.HashMap;
import java.util.List;

public final class StaticMenu extends UserMenu{
	public StaticMenu(String title,List<MenuChoice> choice) {
		this.message=title;
		this.choices=choice;
	}
	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception{
		System.out.println(this.message);
		int choice = this.givechoices(data);
		this.checkandrun(choice, data);
		return true;
	}
}
