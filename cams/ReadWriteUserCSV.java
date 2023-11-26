package cams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import entities.Staff;
import entities.Student;
import entities.User;
import types.Faculty;
import types.Role;

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
     * Expected CSV format: name,email,faculty,password
     * 
     * @param userList the user list
     * @param pathName the path name e.g. /lists
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
                        && (file.getName().startsWith("staff") || file.getName().startsWith("student"))) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        // Skip header line i.e. first row
                        String line = br.readLine();

                        // Read each line of the file
                        while ((line = br.readLine()) != null) {
                            String[] values = line.split(",");
                            String name = values[0];
                            String userId = values[1].split("@")[0]; // Remove @e.ntu.edu
                            Faculty faculty = Faculty.valueOf(values[2]);
                            String password = values[3];
                            int campCommittee = -1;
                            if (values.length > 4) {
                                campCommittee = Integer.parseInt(values[4]);
                            }
                            int points = 0;
                            if (values.length > 5) {
                                points = Integer.parseInt(values[5]);
                            }

                            if (file.getName().contains("staff")) {
                                Staff staff = new Staff(userId, name, password, faculty);
                                userList.put(userId, staff);
                            } else if (file.getName().contains("student")) {
                                Student student = new Student(userId, name, password, faculty, campCommittee, points);
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

    /**
     * Write user CSV.
     * Output CSV format: name,email,faculty,password
     * 
     * @param userList         the user list
     * @param fileNameWithPath e.g. /lists/staff_list.csv
     * @param role             the role
     * @return true, if successful
     */
    public static final boolean writeUserCSV(HashMap<String, User> userList, String fileNameWithPath, Role role) {

        File file;
        switch (role) {
            case STAFF:
                file = new File("lists/staff_list.csv");
                break;
            case ATTENDEE:
            case COMMITTEE:
                file = new File("lists/student_list.csv");
                break;
            default:
                return false;
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            // header row
            writer.append("name,email,faculty,password\n");

            for (User user : userList.values()) {
                if (user.getClass() == Staff.class && role == Role.STAFF ||
                        user.getClass() == Student.class && role == Role.ATTENDEE) {
                    writer.append(user.getName())
                            .append(",")
                            .append(user.getUserId() + "@NTU.EDU.SG")
                            .append(",")
                            .append(user.getFaculty().toString())
                            .append(',')
                            .append(user.getPassword());

                    if (user.getClass() == Student.class) {
                        writer.append(",")
                                .append(String.valueOf(((Student) user).getCampCommittee()))
                                .append(",")
                                .append(String.valueOf(((Student) user).getPoints()));
                    }

                    writer.append("\n");
                }

            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return false;
    }
}
