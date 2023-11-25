package cams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

/**
 * The Class that contains function to read and write
 * the "camp_list" CSV file.
 * 
 * @author Teo Xuan Ming
 * @version 1.0
 * @since 2021-11-20
 */
public class ReadWriteCampCSV {
    /**
     * Read camp_list CSV.
     * Expected CSV format: campId,campName,campDate,
     * campRegistrationDeadline,campFaculty,campLocation,campSlots,
     * campCommitteeSlots,campDescription,staffIC,attendees,committee,
     * visibility,creationDate,enquiries,suggestions
     * 
     * @param userList
     * @param pathName
     */
    public static final void readCampCSV(HashMap<Integer, Camp> campList, String pathName) {

        // Read CSV files from lists folder
        File folder = new File(pathName);
        File[] files = folder.listFiles();

        // Check not empty directory
        if (files != null) {
            // Iterate through files
            for (File file : files) {
                // If file name ends with csv and starts camp
                if (file.isFile()
                        && file.getName().endsWith(".csv")
                        && file.getName().startsWith("camp")) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line = br.readLine(); // Skip header line
                        int lastCampId = 0;
                        while ((line = br.readLine()) != null) {
                            String[] values = line.split(",");
                            // Will not use, generate new campId when creating
                            // camp Object to ensure unique id each time
                            int campId = Integer.parseInt(values[0]);

                            String campName = values[1];

                            // Get campDate as HashSet<LocalDate>
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
                            boolean visibility = Boolean.parseBoolean(values[10]);
                            LocalDate creationDate = LocalDate.parse(values[11]);
                            int attendeeCount = Integer.parseInt(values[12]);
                            int committeeCount = Integer.parseInt(values[13]);
                            // Create CampInfo and Camp Object
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
                            Camp camp = new Camp(campId, campInfo, visibility, creationDate, attendeeCount,
                                    committeeCount);
                            campList.put(camp.getCampid(), camp);
                            lastCampId = campId;
                        }
                        Camp.setNextCampId(lastCampId + 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Write user CSV.
     * Output CSV format: name,email,faculty,password
     * 
     * @param userList         the user list
     * @param fileNameWithPath e.g. /lists/staff_list.csv
     * @return true, if successful
     */
    public static final boolean writeCampCSV(HashMap<Integer, Camp> campList, String fileNameWithPath) {
        File file = new File(fileNameWithPath);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            // header row
            writer.append(
                    "campId,campName,campDates,registrationDeadline,faculty,location,slots,committeeSlots,description,staffIC,visibility,createDate,attendeeCount,committeeCount\n");

            for (Camp camp : campList.values()) {

                HashSet<LocalDate> datesHashSet = (HashSet<LocalDate>) camp.getCampInfo().info()
                        .get(CampAspect.DATE);
                String dates = "";
                for (LocalDate localDate : datesHashSet) {
                    dates += localDate.toString() + ";";
                }
                writer.append(String.valueOf(camp.getCampid()));
                writer.append(",");
                writer.append(camp.getCampInfo().info().get(CampAspect.NAME).toString());
                writer.append(",");
                writer.append(dates);
                writer.append(",");
                writer.append(camp.getCampInfo().info().get(CampAspect.REGISTRATION_DEADLINE).toString());
                writer.append(",");
                writer.append(camp.getCampInfo().info().get(CampAspect.USERGROUP).toString());
                writer.append(",");
                writer.append(camp.getCampInfo().info().get(CampAspect.LOCATION).toString());
                writer.append(",");
                writer.append(camp.getCampInfo().info().get(CampAspect.SLOTS).toString());
                writer.append(",");
                writer.append(camp.getCampInfo().info().get(CampAspect.COMMITTEESLOTS).toString());
                writer.append(",");
                writer.append(camp.getCampInfo().info().get(CampAspect.DESCRIPTION).toString());
                writer.append(",");
                writer.append(camp.getCampInfo().info().get(CampAspect.STAFFIC).toString());
                writer.append(",");
                writer.append(String.valueOf(camp.getVisibility()));
                writer.append(",");
                writer.append(camp.getCreationDate().toString());
                writer.append(",");
                writer.append(String.valueOf(camp.getAttendeeCount()));
                writer.append(",");
                writer.append(String.valueOf(camp.getCommitteeCount()));
                writer.append("\n");
            }
            return true;
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
        return false;
    }
}
