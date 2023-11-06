package core;

import java.util.HashMap;
import java.util.Scanner;

public class Utility {
	public static Scanner getScanner(HashMap<String,Object> data) throws Exception {
		if(!data.containsKey("Scanner")) throw new Exception("Scanner not initialised. Request Failed.");
		if(!Scanner.class.isInstance(data.get("Scanner")))
		throw new Exception("Scanner not found. Request Failed.");
		return (Scanner) data.get("Scanner");
	}
}
