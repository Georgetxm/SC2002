package camsAction;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import cams.CamsInteraction;
import controllers.Controller;
import entities.Student;
import entities.User;
import entities.UserInfoMissingException;
import interactions.Interaction;
import types.CampAspect;
import types.Role;

/**
 * Interaction that represents the action of generating and printing a camp
 * report.
 * Effectively serves as a function pointer
 * 
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doGenerateAttendanceList extends Interaction {
	/**
	 * Generates an attendance list txt or csv
	 * 
	 * @return the correct single camp menu, removing rolerequested and preserving
	 *         all other parameters
	 * @throws MissingRequestedDataException if suggestion to be deleted cannot be
	 *                                       found.
	 */
	@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException, MissingRequestedDataException {
		if (campid == null)
			throw new MissingRequestedDataException("Camp is not valid");

		// Get everyone, staff, committee or whatever associated with the camp. Is a set
		// of strings.
		HashSet<Serializable> everyone = control.Directory().with(entities.Camp.class, campid).get(entities.User.class);

		List<Entry<String, Role>> memberlist = new ArrayList<Entry<String, Role>>();

		for (Serializable name : everyone) {// for each dude in everyone
			// if student
			if (control.User().getClass((String) name) == Student.class)
				// if he is camp committee for this camp that we are generating the report of
				// (since he could be a camp committee elsewhere)
				if (control.User().getCampCommitteeOfStudent((String) name) == campid) {
					if (rolerequested.contains(Role.COMMITTEE))
						memberlist.add(new HashMap.SimpleEntry<String, Role>((String) name, Role.COMMITTEE));
				} else if (rolerequested.contains(Role.ATTENDEE))
					memberlist.add(new HashMap.SimpleEntry<String, Role>((String) name, Role.ATTENDEE));
		}
		// Gets the camp details
		TreeMap<CampAspect, ? extends Object> info = control.Camp().details(campid).info();
		String campinfostring = "";
		for (CampAspect aspect : info.keySet()) // For each category of detail
			// append it to the campinfostring in the format "Category: Thing", e.g.
			// Location: THE ARC
			campinfostring += String.format("%s: %s\n", aspect.name(), GetData.FromObject(info.get(aspect)));
		/*
		 * So right now, the list of members we want is in member list, and the camp
		 * details is already in a nice string.
		 * If you don't want the nice string, you can just uh, take it from "info"
		 * directly.
		 * 
		 * 
		 */
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
		LocalDateTime now = LocalDateTime.now();

		try (FileWriter writer = new FileWriter("/output/camp" + campid.toString() + "_" + dtf.format(now) + ".csv")) {
			// header row
			writer.append("name,email,faculty,password\n");
			for (Entry<String, Role> entry : memberlist) {
				writer.append(entry.getKey() + "," + entry.getValue() + "\n");
			}

			// for (User user : userList.values()) {
			// writer.append(user.getUserId() + "," + user.getUserId() + "," +
			// user.getFaculty() + ","
			// + user.getPassword() + "\n");
			// }
			// return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		HashSet<Serializable> usercamps = new HashSet<Serializable>();
		usercamps = control.Directory().with(entities.User.class, currentuser).get(entities.Camp.class);
		next = (usercamps != null && usercamps.contains(campid)) ? CamsInteraction.OwnCampMenu(campid, currentuser)
				: CamsInteraction.OtherCampMenu(campid, currentuser);
		if (this.userid != null)
			next = next.withuser(userid);
		if (this.filters != null)
			next = next.withfilter(filters);
		return next.withcamp(campid);
	}

}
