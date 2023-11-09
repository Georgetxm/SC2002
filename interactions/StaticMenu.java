package interactions;
import java.util.List;

public final class StaticMenu extends UserMenu{
	public StaticMenu(String title,List<MenuChoice> choice) {
		this.message=title;
		this.choices=choice;
	}
	@Override
	public final Boolean run() throws Exception{
		while(true) {
			System.out.println(this.message);
			int choice = this.givechoices();
			if(choice<0) break;
			this.checkandrun(choice);
		}
		return true;
	}
}
