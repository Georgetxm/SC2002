package cams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

import entities.Camp;
import entities.Staff;
import entities.Student;
import entities.User;
import types.Faculty;
import types.Location;

/**
 * The Class that contains function to read and write CSV files.
 * 
 * 
 * @author Teo Xuan Ming
 * @version 1.0
 * @since 2021-11-20
 */
public final class ReadWriteCSV {
    public static final void readUserCSV(HashMap<String, User> userList, String pathName) {

        // Read CSV files from lists folder
        File folder = new File(pathName);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()
                        && file.getName().endsWith(".csv")
                        && (file.getName().startsWith("staff") || file.getName().endsWith("student"))) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line = br.readLine(); // Skip header line

                        while ((line = br.readLine()) != null) {
                            String[] values = line.split(",");
                            String userId = values[1].split("@")[0];
                            String password = values[3];
                            Faculty faculty = Faculty.valueOf(values[2]);

                            if (file.getName().contains("staff")) {
                                Staff staff = new Staff(userId, password, faculty);
                                userList.put(userId, staff);
                            } else if (file.getName().contains("student")) {
                                Student student = new Student(userId, password, faculty);
                                userList.put(userId, student);
                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // public static final void readCampCSV(HashMap<Integer, Camp> campList) {

    // // Read CSV files from lists folder
    // File folder = new File("lists");
    // File[] files = folder.listFiles();

    // if (files != null) {
    // for (File file : files) {
    // if (file.isFile()
    // && file.getName().endsWith(".csv")
    // && file.getName().startsWith("camp")) {
    // try (BufferedReader br = new BufferedReader(new FileReader(file))) {
    // String line = br.readLine(); // Skip header line

    // while ((line = br.readLine()) != null) {
    // String[] values = line.split(",");
    // int campId = Integer.parseInt(values[0]);
    // String campName = values[1];
    // String campDate = values[2];
    // LocalDate campRegistrationDeadline = LocalDate.parse(values[3]);
    // Faculty campFaculty = Faculty.valueOf(values[4]);
    // Location campLocation = Location.valueOf(values[5]);
    // int campSlots = Integer.parseInt(values[6]);
    // int campCommitteeSlots = Integer.parseInt(values[7]);
    // String campDescription = values[8];
    // String staffIC = values[9];

    // Camp camp = new Camp(campId, campName, campDate, campLocation, campSlots,
    // campCommitteeSlots, campDescription);
    // campList.put(campId, camp);
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
    // }
    // }
    // }
}
