package core;

import java.util.HashMap;
import java.util.Scanner;

public class Utility {
	public static Scanner getScanner(HashMap<String,Object> data) throws Exception {
		Scanner s;
		if(!data.containsKey("Scanner")) throw new Exception("Scanner not initialised. Request Failed.");
		try {s = (Scanner) data.get("Scanner");}
		catch(ClassCastException e) {
			throw new Exception("Scanner not found. Request Failed.");
		}
		return s;
	}
}
