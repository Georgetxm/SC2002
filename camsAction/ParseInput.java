package camsAction;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Map.Entry;

import types.CampAspect;
import types.Faculty;
import types.Location;
/**
 * Contains the functions relating to string manipulation of user inputs that are exclusively used by camsAction.
 * This class is static, there should be no instances of this class, and the constructor is private
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
final class ParseInput {
	/**
	 * Class is static, constructor thus private.
	 */
	private ParseInput() {}
	/**
	 * Queries a user for camp name and creates the appropriate storage container to store it.
	 * @param s Scanner to be used.
	 * @return the hashmap entry denoting the a camp name
	 */
	static final Entry<CampAspect,String> CampName(Scanner s){
		System.out.println("Type Camp Name\n");
		return new HashMap.SimpleEntry<CampAspect,String>(CampAspect.NAME,s.nextLine());
	}
	/**
	 * Queries a user for camp dates and creates the appropriate storage container to store it.
	 * @param s Scanner to be used.
	 * @return the hashmap entry denoting the a camp name
	 */
	static final Entry<CampAspect,HashSet<LocalDate>> CampDate(Scanner s){
		System.out.println("Now please enter your camp dates.");
		String input = "";
		HashSet<LocalDate> datelist = new HashSet<LocalDate>();
		while(true) {
			System.out.println("Type new date in the form YYYY-MM-DD, or leave blank if finished");
			input = s.nextLine();
			if(input=="")break;
			try {datelist.add(LocalDate.parse(input));}
			catch(DateTimeParseException e) {System.out.println("Date format is wrong");}
			System.out.println("Date saved. Enter more dates or press enter to exit");
		}
		return new HashMap.SimpleEntry<CampAspect,HashSet<LocalDate>>(CampAspect.DATE,datelist);
	}
	/**
	 * Queries a user for the registration deadline and creates the appropriate storage container to store it.
	 * @param s Scanner to be used.
	 * @return the hashmap entry denoting the a camp name
	 */
	static final SimpleEntry<CampAspect, LocalDate> CampRegisterDate(Scanner s){
		System.out.println("Now please enter the registration deadline");
		LocalDate date;
		while(true) {
			System.out.println("Type new date in the form YYYY-MM-DD.");
			try {date=LocalDate.parse(s.nextLine());}
			catch(DateTimeParseException e) {
				System.out.println("Date format is wrong");
				continue;
			}
			break;
		}
		return new HashMap.SimpleEntry<CampAspect,LocalDate>(CampAspect.REGISTRATION_DEADLINE,date);
	}
	/**
	 * Queries a user for the camp host faculty and creates the appropriate storage container to store it.
	 * @param s Scanner to be used.
	 * @return the hashmap entry denoting the a camp name
	 */
	static final Entry<CampAspect,Faculty> CampFaculty(Scanner s){
		System.out.println("Please choose a host faculty");
		Faculty[] facultylist = Faculty.class.getEnumConstants();
		int choice = 0;
		while(true) {
			for(int i=1;i<=facultylist.length;i++ )
				System.out.printf("%d:%s\n",i,facultylist[i-1].name());
			choice = -9;
			while(true){
				String inputs = s.nextLine();
				try {
					choice = Integer.parseInt(inputs);
				 }catch(NumberFormatException e) {} 
				if(choice!=-9) break;
				System.out.println("Please type in a valid option");
			}
			if(choice<1||choice>facultylist.length) {
				System.out.println("Invalid option chosen");
				continue;
			}
			break;
		}
		return new HashMap.SimpleEntry<CampAspect,Faculty>(CampAspect.USERGROUP,facultylist[choice-1]);
	}
	/**
	 * Queries a user for camp location and creates the appropriate storage container to store it.
	 * @param s Scanner to be used.
	 * @return the hashmap entry denoting the a camp name
	 */
	static final Entry<CampAspect,Location> CampLocation(Scanner s){
		System.out.println("Please designate a camp location");
		Location[] locationlist = Location.class.getEnumConstants();
		int choice = 0;
		while(true) {
			for(int i=1;i<=locationlist.length;i++ )
				System.out.printf("%d:%s\n",i,locationlist[i-1].name());
			choice = -9;
			while(true){
				String inputs = s.nextLine();
				try {
					choice = Integer.parseInt(inputs);
				 }catch(NumberFormatException e) {} 
				if(choice!=-9) break;
				System.out.println("Please type in a valid option");
			}
			if(choice<1||choice>locationlist.length) {
				System.out.println("Invalid option chosen");
				continue;
			}
			break;
		}
		return new HashMap.SimpleEntry<CampAspect,Location>(CampAspect.LOCATION,locationlist[choice-1]);
	}	
	/**
	 * Queries a user for the number of camp attendee slots and creates the appropriate storage container to store it.
	 * @param s Scanner to be used.
	 * @return the hashmap entry denoting the a camp name
	 */
	static final Entry<CampAspect,Integer>CampSlots(Scanner s){
		System.out.println("Please choose the number of participant slots");
		Integer slots;
		while(true) {
			slots = -9;
			while(true){
				String inputs = s.nextLine();
				try {
					choice = Integer.parseInt(inputs);
				 }catch(NumberFormatException e) {} 
				if(slots!=-9) break;
				System.out.println("Please type in a valid option");
			}
			if(slots<1) {
				System.out.println("Too few slots");
				continue;
			}
			break;
		}
		return new HashMap.SimpleEntry<CampAspect,Integer>(CampAspect.SLOTS,slots);
	}
	/**
	 * Queries a user for number of camp committee slots and creates the appropriate storage container to store it.
	 * @param s Scanner to be used.
	 * @return the hashmap entry denoting the a camp name
	 */
	static final Entry<CampAspect,Integer>CampComitteeSlots(Scanner s){
		System.out.println("Please choose the number of committee slots");
		Integer slots;
		while(true) {
			slots = -9;
			while(true){
				String inputs = s.nextLine();
				try {
					choice = Integer.parseInt(inputs);
				 }catch(NumberFormatException e) {} 
				if(slots!=-9) break;
				System.out.println("Please type in a valid option");
			}
			if(slots<1) {
				System.out.println("Too few slots");
				continue;
			}
			break;
		}
		return new HashMap.SimpleEntry<CampAspect,Integer>(CampAspect.COMMITTEESLOTS,slots);
	}
	/**
	 * Queries a user for camp description and creates the appropriate storage container to store it.
	 * @param s Scanner to be used.
	 * @return the hashmap entry denoting the a camp name
	 */
	static final Entry<CampAspect,String>CampDescription(Scanner s){
		System.out.println("Type Camp Description");
		return new HashMap.SimpleEntry<CampAspect,String>(CampAspect.DESCRIPTION,s.nextLine());
	}
	/**
	 * Queries a user for camp staff ic and creates the appropriate storage container to store it.
	 * @param s Scanner to be used.
	 * @return the hashmap entry denoting the a camp name
	 */
	static final Entry<CampAspect,String>CampStaffIC(Scanner s){
		System.out.println("Type staff name");
		String userid=s.nextLine();
		return new HashMap.SimpleEntry<CampAspect,String>(CampAspect.STAFFIC,userid);
	}
}
