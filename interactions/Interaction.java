package interactions;
import java.util.Scanner;

import entities.Data;

public abstract class Interaction {
	protected String message;
	protected abstract Object run() throws Exception;
	protected static Scanner getScanner() throws Exception {
		if(!Data.containsKey("Scanner")) throw new Exception("Scanner not initialised. Request Failed.");
		if(!Scanner.class.isInstance(Data.get("Scanner")))
		throw new Exception("Scanner not found. Request Failed.");
		return (Scanner) Data.get("Scanner");
	}
}
