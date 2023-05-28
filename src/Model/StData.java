package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This class hold miscellaneous functions to construct data sets.*/
public class StData {

    /**This constructs a list of all times in a day in 15 minute increments.
     * @return allTimeBoxes the String list of 15 minute time increments*/
    public static ObservableList<String> getTimeBoxes() {
        ObservableList<String> allTimeBoxes = FXCollections.observableArrayList();
        String[] quarterHours = { "00", "15", "30", "45" };
        String t = "12:00AM";
        allTimeBoxes.add(t);
        t = "12:15AM";
        allTimeBoxes.add(t);
        t = "12:30AM";
        allTimeBoxes.add(t);
        t = "12:45AM";
        allTimeBoxes.add(t);

        for (int i = 1; i < 12; i++) {
            for (int j = 0; j < 4; j++) {
                if(i != 8 && j !=0) {
                    String o = i + ":" + quarterHours[j] + "AM";
                    allTimeBoxes.add(o);
                }
            }
        }
        t = "12:00PM";
        allTimeBoxes.add(t);
        t = "12:15PM";
        allTimeBoxes.add(t);
        t = "12:30PM";
        allTimeBoxes.add(t);
        t = "12:45PM";
        allTimeBoxes.add(t);

        for (int k = 1; k < 12; k++) {
            for (int l = 0; l < 4; l++) {
                String p = k + ":" + quarterHours[l] + "PM";
                allTimeBoxes.add(p);
            }
        }
        return allTimeBoxes;
    }

    /**This constructs a list of all different appointment types.
     * @return allAvailableTypes the String list of available appointment types*/
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
