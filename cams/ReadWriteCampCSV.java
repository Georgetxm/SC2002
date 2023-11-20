package cams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import entities.Camp;
import entities.CampInfo;
import types.CampAspect;
import types.Faculty;
import types.Location;

public class ReadWriteCampCSV {
    public static final void readCampCSV(HashMap<Integer, Camp> campList, String pathName) {

        // Read CSV files from lists folder
        File folder = new File(pathName);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()
                        && file.getName().endsWith(".csv")
                        && file.getName().startsWith("camp")) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line = br.readLine(); // Skip header line

                        while ((line = br.readLine()) != null) {
                            String[] values = line.split(",");
                            int campId = Integer.parseInt(values[0]); // Will not use, generate new campId when creating
                                                                      // camp Object to ensure unique id each time
                            String campName = values[1];

                            String[] localDateStrings = values[2].split(";");
                            HashSet<LocalDate> campDate = new HashSet<LocalDate>();
                            for (String localDateString : localDateStrings) {
                                campDate.add(LocalDate.parse(localDateString));
                            }

                            LocalDate campRegistrationDeadline = LocalDate.parse(values[3]);
                            Faculty campFaculty = Faculty.valueOf(values[4]);
                            Location campLocation = Location.valueOf(values[5]);
                            int campSlots = Integer.parseInt(values[6]);
                            int campCommitteeSlots = Integer.parseInt(values[7]);
                            String campDescription = values[8];
                            String staffIC = values[9];

                            HashSet<String> attendees = new HashSet<String>();
                            String[] attendeeStrings = values[10].split(";");
                            for (String attendeeId : attendeeStrings) {
                                attendees.add(attendeeId);
                            }

                            HashSet<String> committee = new HashSet<String>();
                            String[] commiteeStrings = values[11].split(";");
                            for (String committeeId : commiteeStrings) {
                                committee.add(committeeId);
                            }

                            boolean visibility = Boolean.parseBoolean(values[12]);
                            LocalDate creationDate = LocalDate.parse(values[13]);

                            HashSet<Integer> enquiries = new HashSet<Integer>();
                            String[] enquiriesString = values[14].split(";");
                            for (String enquiriyId : enquiriesString) {
                                enquiries.add(Integer.parseInt(enquiriyId));
                            }

                            HashSet<Integer> suggestions = new HashSet<Integer>();
                            String[] suggestionsString = values[15].split(";");
                            for (String suggestionId : suggestionsString) {
                                suggestions.add(Integer.parseInt(suggestionId));
                            }

                            TreeMap<CampAspect, Object> campInfoObj = new TreeMap<CampAspect, Object>();
                            campInfoObj.put(CampAspect.NAME, campName);
                            campInfoObj.put(CampAspect.DATE, campDate);
                            campInfoObj.put(CampAspect.REGISTRATION_DEADLINE, campRegistrationDeadline);
                            campInfoObj.put(CampAspect.USERGROUP, campFaculty);
                            campInfoObj.put(CampAspect.LOCATION, campLocation);
                            campInfoObj.put(CampAspect.SLOTS, campSlots);
                            campInfoObj.put(CampAspect.COMMITTEESLOTS, campCommitteeSlots);
                            campInfoObj.put(CampAspect.DESCRIPTION, campDescription);
                            campInfoObj.put(CampAspect.STAFFIC, staffIC);
                            CampInfo campInfo = new CampInfo(campInfoObj);
                            Camp camp = new Camp(campInfo, attendees, committee, visibility, creationDate, enquiries,
                                    suggestions);
                            campList.put(camp.getCampid(), camp);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
