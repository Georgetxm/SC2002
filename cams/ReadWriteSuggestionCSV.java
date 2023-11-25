package cams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import entities.Suggestion;
import types.CampAspect;

public class ReadWriteSuggestionCSV {

    private static String convertAspectEntryToCsvRow(Entry<CampAspect, ? extends Object> suggestionAspect) {
        CampAspect chosenaspect = suggestionAspect.getKey();
        String valueString = "";
        if (chosenaspect == CampAspect.DATE) {
            HashSet<LocalDate> date = (HashSet<LocalDate>) suggestionAspect.getValue();
            StringBuilder sb = new StringBuilder();
            for (LocalDate d : date) {
                sb.append(d.toString()).append("|");
            }
            valueString = sb.toString();
        } else {
            valueString = suggestionAspect.getValue().toString();
        }
        String aspectString = (chosenaspect != null) ? chosenaspect.toString() : "";
        String fValueString = (!valueString.equals(null)) ? valueString.toString() : "";
        return aspectString + ";" + fValueString;
    }

    private static Entry<CampAspect, ? extends Object> convertCsvRowToAspectEntry(String csvRow) {
        String[] values = csvRow.split(";");
        CampAspect chosenaspect = (CampAspect) CampAspect.valueOf(values[0]);
        switch (chosenaspect) {
            case DATE:
                HashSet<LocalDate> datelist = new HashSet<LocalDate>();
                for (String d : values[1].split("|")) {
                    datelist.add(LocalDate.parse(d));
                }
                return new SimpleEntry<CampAspect, HashSet<LocalDate>>(CampAspect.DATE, datelist);
            case REGISTRATION_DEADLINE:
                return new SimpleEntry<CampAspect, LocalDate>(CampAspect.REGISTRATION_DEADLINE,
                        LocalDate.parse(values[1]));
            case LOCATION:
                return new SimpleEntry<CampAspect, String>(CampAspect.LOCATION, values[1]);
            case SLOTS:
                return new SimpleEntry<CampAspect, Integer>(CampAspect.SLOTS, Integer.parseInt(values[1]));
            case DESCRIPTION:
                return new SimpleEntry<CampAspect, String>(CampAspect.DESCRIPTION, values[1]);
            default:
                System.out.println("This field cannot be changed.");
        }
        return null;
    }

    /**
     * Read user CSV.
     * Expected CSV format: name,email,faculty,password
     * 
     * @param userList
     * @param pathName
     */
    public static final void readSuggestionCSV(HashMap<Integer, Suggestion> suggestionList, String pathName) {

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
                        && file.getName().startsWith("suggestion")) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        // Skip header line i.e. first row
                        String line = br.readLine();

                        int lastSuggestionId = 0;
                        // Read each line of the file
                        while ((line = br.readLine()) != null) {
                            String[] values = line.split(",");
                            int suggestionId = Integer.parseInt(values[0]);
                            String rationale = values[1];
                            Entry<CampAspect, ? extends Object> suggestionAspect = convertCsvRowToAspectEntry(
                                    values[2]);
                            boolean accepted = Boolean.parseBoolean(values[3]);
                            LocalDate creationDate = LocalDate.parse(values[4]);
                            LocalDate lastUpdateDate = LocalDate.parse(values[5]);

                            Suggestion suggestion = new Suggestion(suggestionId, rationale, suggestionAspect, accepted,
                                    creationDate, lastUpdateDate);
                            suggestionList.put(suggestionId, suggestion);
                        }
                        Suggestion.setNextSuggestionId(lastSuggestionId + 1);
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
    public static final boolean writeSuggestionCSV(HashMap<Integer, Suggestion> suggestionList,
            String fileNameWithPath) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileNameWithPath);
            // header row
            writer.append("suggestionId,rationale,suggestionAspect,accepted,creationDate,lastUpdatedDate\n");

            for (Suggestion suggestion : suggestionList.values()) {
                writer.append(String.valueOf(suggestion.getSuggestionId()))
                        .append(",")
                        .append(suggestion.getRationale())
                        .append(",")
                        .append(suggestion.getSuggestionAspect().toString())
                        .append(",")
                        .append(String.valueOf(suggestion.isAccepted()))
                        .append(",")
                        .append(suggestion.getCreationDate().toString())
                        .append(",")
                        .append(suggestion.getLastUpdatedDate().toString())
                        .append("\n");
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
        return true;

    }

}
