package cams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import entities.Enquiry;

/**
 * The Class that contains function to read and write
 * the "enquiry_list" CSV file.
 * 
 * @author Teo Xuan Ming
 * @version 1.0
 * @since 2021-11-20
 */
public class ReadWriteEnquiryCSV {
    /**
     * Read Enquiry CSV.
     * Expected CSV format: enquiryId, enquiryBody, seen, creationDate,
     * lastUpdateDate, replies
     * 
     * @param enquiryList the enquiry list
     * @param pathName    the path name e.g. /lists
     * @return the hash map of enquiryId and enquiries
     */
    public static final HashMap<Integer, Enquiry> readEnquiryCSV(HashMap<Integer, Enquiry> enquiryList,
            String pathName) {

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
                        && file.getName().startsWith("enquiry")) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        // Skip header line i.e. first row
                        String line = br.readLine();

                        int lastEnquiryId = 0;
                        // Read each line of the file
                        while ((line = br.readLine()) != null) {
                            String[] values = line.split(",");
                            int enquiryId = Integer.parseInt(values[0]);
                            String enquiryBody = values[1];
                            Boolean seen = Boolean.parseBoolean(values[2]);
                            LocalDate creationDate = LocalDate.parse(values[3]);
                            LocalDate lastUpdateDate = LocalDate.parse(values[4]);
                            String replyString;
                            ArrayList<String> replyArrayList = new ArrayList<String>();
                            if (values.length > 5) {
                                replyString = values[5];
                                String[] replies = replyString.split(";");
                                for (String reply : replies) {
                                    replyArrayList.add(reply);
                                }
                            }
                            lastEnquiryId = enquiryId;
                            Enquiry enquiry = new Enquiry(enquiryId, enquiryBody, seen, creationDate, lastUpdateDate,
                                    replyArrayList);

                            enquiryList.put(enquiryId, enquiry);
                        }
                        Enquiry.setNextEnquiryId(lastEnquiryId + 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return enquiryList;
    }

    // test
    /**
     * Write user CSV.
     * Expected CSV format: enquiryId, enquiryBody, seen, creationDate,
     * 
     * @param enquiryList      the enquiryList
     * @param fileNameWithPath e.g. /lists/staff_list.csv
     * @return true, if successful
     */
    public static final boolean writeEnquiryCSV(HashMap<Integer, Enquiry> enquiryList, String fileNameWithPath) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileNameWithPath);
            // header row
            writer.append("enquiryId,enquiryBody,seen,creationDate,lastUpdateDate,replies\n");

            for (Enquiry enquiry : enquiryList.values()) {
                writer.append(enquiry.getEnquiryId() + ","
                        + enquiry.getEnquiryBody() + ","
                        + enquiry.isSeen() + ","
                        + enquiry.getCreationDate() + ","
                        + enquiry.getLastUpdateDate() + ",");
                if (enquiry.getReplies().size() > 0) {
                    writer.append(String.join(";", enquiry.getReplies()) + "\n");
                } else {
                    writer.append("" + "\n");
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
