package interactions;
import java.util.Scanner;

import camsAction.MissingRequestedDataException;
import entities.Data;
import entities.UserInfoMissingException;

public abstract class Interaction {
	protected String message;
	protected abstract Object run() throws UserInfoMissingException,MissingRequestedDataException;
	protected static Scanner getScanner(){
		if(Data.containsKey("Scanner")&&Scanner.class.isInstance(Data.get("Scanner")))
		return (Scanner) Data.get("Scanner");
		return new Scanner(System.in);
		
	}
}
