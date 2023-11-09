package camsAction;
import interactions.Interaction;

public final class doNothing extends Interaction{
	@Override
	public final Boolean run() throws Exception{
		System.out.println("Nothing happened.");
		return true;
	}
}
