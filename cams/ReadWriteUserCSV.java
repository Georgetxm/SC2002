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
 * 
 * @author Teo Xuan Ming
 * @version 1.0
 * @since 2021-11-20
 */
public final class ReadWriteUserCSV {
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

}
