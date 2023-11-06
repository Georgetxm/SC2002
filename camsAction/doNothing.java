package camsAction;
import java.util.HashMap;

import interactions.Interaction;

public final class doNothing extends Interaction{
	@Override
	public final Boolean run(HashMap<String, Object> data) throws Exception{
		System.out.println("Nothing happened.");
		return true;
	}
}
