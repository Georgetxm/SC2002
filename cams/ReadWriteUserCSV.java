package cams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import entities.Staff;
import entities.Student;
import entities.User;
import types.Faculty;

/**
 * The Class that contains function to read and write
 * the "staff_list" and "student_list" CSV files.
 * 
 * @author Teo Xuan Ming
 * @version 1.0
 * @since 2021-11-20
 */
public final class ReadWriteUserCSV {

    /**
     * Read user CSV.
     * Expected CSV format: name,userId,faculty,password
     * 
     * @param userList
     * @param pathName
     */
    public static final void readUserCSV(HashMap<String, User> userList, String pathName) {

        // Read all files from lists folder
        File folder = new File(pathName);
        File[] files = folder.listFiles();

        // Check not empty directory
        if (files != null) {
            // Iterate through files
            for (File file : files) {
                // If file name ends with csv and starts with staff or with student
                if (file.isFile()
                        && file.getName().endsWith(".csv")
                        && (file.getName().startsWith("staff") || file.getName().endsWith("student"))) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        // Skip header line i.e. first row
                        String line = br.readLine();

                        // Read each line of the file
                        while ((line = br.readLine()) != null) {
                            String[] values = line.split(",");
                            String userId = values[1].split("@")[0]; // Remove @e.ntu.edu
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

}
