package camsAction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Scanner;

import cams.CamsInteraction;
import controllers.Controller;
import entities.Student;
import entities.UserInfoMissingException;
import interactions.Interaction;

/**
 * Interaction that represents the action of generating and printing the
 * performance report of camp committee members to csv
 * report.
 * Effectively serves as a function pointer
 * 
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-25
 */
public class doGeneratePerformanceReport extends Interaction {
	/**
	 * Asks the controller for a list of camp committee members and their points and
	 * generates a csv document displaying them
	 * 
	 * @return owncampmenu with campid, userid, and filters preserved
	 * @throws MissingRequestedDataException if camp whose camp details were to be
	 *                                       edited cannot be found.
	 */
	@Override
	public Interaction run(String currentuser, Scanner s, Controller control)
			throws UserInfoMissingException, MissingRequestedDataException {

		if (campid == null)
			throw new MissingRequestedDataException("Camp is not valid");

		// Get everyone, staff, committee or whatever associated with the camp. Is a set
		// of strings.
		HashSet<Serializable> everyone = control.Directory().with(entities.Camp.class, campid).get(entities.User.class);
		// Format is String: Points
		HashMap<String, Integer> memberlist = new HashMap<String, Integer>();

		for (Serializable name : everyone)
			// If dude is a student and camp committee of this camp
			if (control.User().getClass((String) name) == Student.class
					&& control.User().getCampCommitteeOfStudent((String) name) == campid)
				// ask usercontroller for his points and put Name: Points into the member list
				// hashmap
				memberlist.put((String) name, control.User().incrementPoints((String) name, 0));

		/*
		 * So right now the committee members and their points are in a HashMap, or
		 * dictionary basically.
		 * You need to somehow get this onto a csv or txt.
		 * If need help converting a weird datatype to string, just chuck it into
		 * GetData.FromObject() which can convert anything into a decently looking
		 * string.
		 */

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
		LocalDateTime now = LocalDateTime.now();

		File file = new File("output/camp" + campid.toString() + "_peformance_" + dtf.format(now) + ".csv");
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			// header row
			writer.append("userId,points\n");
			for (Entry<String, Integer> entry : memberlist.entrySet()) {
				writer.append(entry.getKey() + "," + entry.getValue() + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// flush and close writer
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
