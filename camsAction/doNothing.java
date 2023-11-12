package camsAction;
import interactions.Interaction;

public final class doNothing extends Interaction{
	@Override
	public final Boolean run(){
		System.out.println("Nothing happened.");
		return true;
	}
}
