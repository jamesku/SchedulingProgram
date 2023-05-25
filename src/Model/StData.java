package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;

public class StData {

    public static ObservableList<String> getTimeBoxesStart() {
        ObservableList<String> allTimeBoxes = FXCollections.observableArrayList();
        String[] quarterHours = { "00", "15", "30", "45" };
        for (int i = 8; i < 12; i++) {
            for (int j = 0; j < 4; j++) {
                String t = i + ":" + quarterHours[j] + "AM";
                allTimeBoxes.add(t);
            }
        }
            String t = "12:00PM";
            allTimeBoxes.add(t);
            t = "12:15PM";
            allTimeBoxes.add(t);
            t = "12:30PM";
            allTimeBoxes.add(t);
            t = "12:45PM";
            allTimeBoxes.add(t);

            for (int k = 1; k < 10; k++) {
                for (int l = 0; l < 4; l++) {
                    t = k + ":" + quarterHours[l] + "PM";
                    allTimeBoxes.add(t);
                }
            }

            return allTimeBoxes;
        }

    public static ObservableList<String> getTimeBoxesEnd() {
        ObservableList<String> allTimeBoxes = FXCollections.observableArrayList();
        String[] quarterHours = { "00", "15", "30", "45" };
        for (int i = 8; i < 12; i++) {
            for (int j = 0; j < 4; j++) {
                if(i != 8 && j !=0) {
                    String t = i + ":" + quarterHours[j] + "AM";
                    allTimeBoxes.add(t);
                }
            }
        }
        String t = "12:00PM";
        allTimeBoxes.add(t);
        t = "12:15PM";
        allTimeBoxes.add(t);
        t = "12:30PM";
        allTimeBoxes.add(t);
        t = "12:45PM";
        allTimeBoxes.add(t);

        for (int k = 1; k < 10; k++) {
            for (int l = 0; l < 4; l++) {
                t = k + ":" + quarterHours[l] + "PM";
                allTimeBoxes.add(t);
                if(k == 9 && l == 3){
                    allTimeBoxes.add("10:00PM");
                }
            }
        }

        return allTimeBoxes;
    }

    public static ObservableList<String> getAvailableTypes() {
        ObservableList<String> allAvailableTypes = FXCollections.observableArrayList();
        allAvailableTypes.add("Planning Session");
        allAvailableTypes.add("De-Briefing");
        allAvailableTypes.add("Preparatory");
        allAvailableTypes.add("One-on-One");
        allAvailableTypes.add("Review");
    return allAvailableTypes;
    }
}
