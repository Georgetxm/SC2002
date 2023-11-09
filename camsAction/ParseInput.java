package camsAction;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Map.Entry;

import types.CampAspects;
import types.Faculty;
import types.Location;

final class ParseInput {
	static final Entry<CampAspects,String> CampName(Scanner s){
		System.out.println("Type Camp Name\n");
		return new HashMap.SimpleEntry<CampAspects,String>(CampAspects.NAME,s.nextLine());
	}
	static final Entry<CampAspects,HashSet<LocalDate>> CampDate(Scanner s){
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
		return new HashMap.SimpleEntry<CampAspects,HashSet<LocalDate>>(CampAspects.DATE,datelist);
	}
	static final SimpleEntry<CampAspects, LocalDate> CampRegisterDate(Scanner s){
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
		return new HashMap.SimpleEntry<CampAspects,LocalDate>(CampAspects.REGISTRATION_DEADLINE,date);
	}
	static final Entry<CampAspects,Faculty> CampFaculty(Scanner s){
		System.out.println("Please choose a host faculty");
		Faculty[] facultylist = Faculty.class.getEnumConstants();
		int choice = 0;
		while(true) {
			for(int i=1;i<=facultylist.length;i++ )
				System.out.printf("%d:%s\n",i,facultylist[i-1].name());
			choice=s.nextInt();
			s.nextLine();
			if(choice<1||choice>facultylist.length) {
				System.out.println("Invalid option chosen");
				continue;
			}
			break;
		}
		return new HashMap.SimpleEntry<CampAspects,Faculty>(CampAspects.USERGROUP,facultylist[choice-1]);
	}
	static final Entry<CampAspects,Location> CampLocation(Scanner s){
		System.out.println("Please designate a camp location");
		Location[] locationlist = Location.class.getEnumConstants();
		int choice = 0;
		while(true) {
			for(int i=1;i<=locationlist.length;i++ )
				System.out.printf("%d:%s\n",i,locationlist[i-1].name());
			choice=s.nextInt();
			s.nextLine();
			if(choice<1||choice>locationlist.length) {
				System.out.println("Invalid option chosen");
				continue;
			}
			break;
		}
		return new HashMap.SimpleEntry<CampAspects,Location>(CampAspects.LOCATION,locationlist[choice-1]);
	}
	static final Entry<CampAspects,Integer>CampSlots(Scanner s){
		System.out.println("Please choose the number of participant slots");
		Integer slots;
		while(true) {
			slots=s.nextInt();
			s.nextLine();
			if(slots<1) {
				System.out.println("Too few slots");
				continue;
			}
			break;
		}
		return new HashMap.SimpleEntry<CampAspects,Integer>(CampAspects.SLOTS,slots);
	}
	static final Entry<CampAspects,Integer>CampComitteeSlots(Scanner s){
		return new HashMap.SimpleEntry<CampAspects,Integer>(CampAspects.COMMITTEESLOTS,10);
	}
	static final Entry<CampAspects,String>CampDescription(Scanner s){
		System.out.println("Type Camp Description");
		return new HashMap.SimpleEntry<CampAspects,String>(CampAspects.DESCRIPTION,s.nextLine());
	}
}
