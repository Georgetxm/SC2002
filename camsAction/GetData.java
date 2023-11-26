package camsAction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.Map.Entry;

import javax.xml.crypto.Data;
/**
 * Class that contains a method for parsing the datatypes we use as string.
 * As this serves mainly as an error handling wrapper, classes in camsAction are free to access Data directly if they do not need error handling.
 * This class is static and should not have any instances, and its constructor is private
 * @see Data
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
final class GetData {
	/**
	 * Constructor is private as the class is static
	 */
	private GetData() {}
	/**
	 * Recursively passes all constituent objects and turns them into strings
	 * @param value the value of an object needed to parse into string
	 * @return the string value of a given class
	 */
	static final String FromObject(Object value){
		String valuestring="";
		if(Iterable.class.isInstance(value)) 
			for(Object thing:(Iterable<?>)value) valuestring+=(FromObject(thing)+"\n");
		else if(Entry.class.isInstance(value)) valuestring = String.format("%s:\t%s", FromObject(((Entry<?,?>) value).getKey()), FromObject(((Entry<?,?>) value).getValue()));
		else if(String.class.isInstance(value)) valuestring = (String) value;
		else if(LocalDate.class.isInstance(value)) valuestring = DateTimeFormatter.ofPattern("dd.MMMM uuuu", Locale.ENGLISH).format((TemporalAccessor) value);
		else valuestring = value.toString();
		return valuestring;
	}
}
