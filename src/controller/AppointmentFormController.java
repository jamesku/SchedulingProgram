package controller;

import javafx.collections.ObservableList;
import model.Appointment;
import database.DatabaseIO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.StData;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**Main class controlling adding and updating Appointments.*/
public class AppointmentFormController
{
    @javafx.fxml.FXML
    private AnchorPane ap;
    @javafx.fxml.FXML
    private TextField ApptID;
    @javafx.fxml.FXML
    private TextField ApptTitle;
    @javafx.fxml.FXML
    private TextField ApptDesc;
    @javafx.fxml.FXML
    private TextField ApptLocation;
    @javafx.fxml.FXML
    private DatePicker ApptDate;
    @javafx.fxml.FXML
    private ComboBox ApptStart;
    @javafx.fxml.FXML
    private ComboBox ApptType;
    @javafx.fxml.FXML
    private ComboBox ApptEnd;
    @javafx.fxml.FXML
    private ComboBox ApptContact;
    @javafx.fxml.FXML
    private ComboBox ApptUID;
    @javafx.fxml.FXML
    private ComboBox ApptCID;


    Stage stage;
    Parent scene;

    /**This is a holder for the Appointment object to be modified. */
    private static Appointment passedAppointment = null;

    /**A variable to track if the Appointment is new or an update*/
    private static boolean newAppointment = true;

    /** This is the initialize method for this controller.  This function automatically runs
     *  when the screen is loaded. It is used here set up some information about the Appointment that
     *  needs to be modified. Additionally, Platform.runlater is called. This method is built in
     *  to Run a specified runnable at some unspecified time in the future. This provides time
     *  for the screen to load before a listener is set on a windows event - in this case,
     *  clicking on the x in the top right corner.
     * */
    @javafx.fxml.FXML
    public void initialize() {

        ApptCID.setItems(DatabaseIO.getCustomerCombo());
        ApptUID.setItems(DatabaseIO.getUserCombo());
        ApptContact.setItems(DatabaseIO.getContactCombo());
        ApptType.setItems(StData.getAvailableTypes());
        ApptStart.setItems(StData.getTimeBoxes());
        ApptEnd.setItems(StData.getTimeBoxes());

        if (newAppointment){
            ApptID.setText("Will be auto-generated");
        } else {
            ApptID.setText(passedAppointment.getApID() + "");
            ApptTitle.setText(passedAppointment.getApTitle());
            ApptDesc.setText(passedAppointment.getApDesc() + "");
            ApptLocation.setText(passedAppointment.getApLocation() + "");
            ApptType.setValue(passedAppointment.getApType() + "");
            ApptContact.setValue(passedAppointment.getApContactName());
            ApptCID.setValue(passedAppointment.getApCID());
            ApptUID.setValue(passedAppointment.getApUID());
            ApptDate.setValue(passedAppointment.getLocalDateTimeEnd().toLocalDate());
            ApptStart.setValue(passedAppointment.getLocalDateTimeStart().toLocalTime().format(DateTimeFormatter.ofPattern("h:mma")));
            ApptEnd.setValue(passedAppointment.getLocalDateTimeEnd().toLocalTime().format(DateTimeFormatter.ofPattern("h:mma")));

        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setListener();
            }
        });
    }

    /** This function sets a listener on a WindowsEvent.  This listener is set on the current
     * stage to gather close requests, which comes from the x in the top right corner.  Instead of
     * closing the window, the event is consumed, and instead the program is redirected to return
     * to the main screen.  This had to be run after the screen is loaded and so it relies on
     * Platform.runlater.*/
    public void setListener() {
        Stage stage = (Stage) (ap.getScene().getWindow());
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                windowEvent.consume();
                try {
                    scene = FXMLLoader.load(getClass().getResource("/View/TopLevelMenu.fxml"));
                } catch (IOException e) {
                    //ignore
                }
                stage.setScene(new Scene(scene));
                stage.show();
            }
        });
    }

    /**This function is called from another class to pass a particular piece of data.  Here is takes in
     * a Appointment object and sets the local variable. It also modifies a boolean to indicate this is
     * an update
     * @param p The Appointment passed from another class.*/
    public static void receiveData(Appointment p){
        passedAppointment = p;
        newAppointment = false;
    }


    /**This is the save function for a modified Appointment.  It checks:
     * if the appointment is new or not,
     * if there is a customer ID integer value,
     * if there is a User ID integer value,
     * if there is a Contact value,
     * if there is a date value,
     * if there is an appointment start time, which it combines with date for a localdatetime,
     * if there is an appointment end time, which it combines with date for a localdatetime,
     * both of which it checks for overlaps, time outside business hours and other scheduling errors,
     * converts the date times to Strings for viewing purposes,
     * if there is a title value,
     * if there is a description value,
     * if there is a location value, and
     * if there is a title value.
     * It makes these an Appointment object and then sends them to the database for addition/modification
     * before going back to the main screen.
     *
     * finds the index of where the modified
     * Appointment occurs in the ObservableList of all Appointments.  Then it checks if the min/max and inventory
     * values are appropriate and returns an alert if not. It then checks if the Appointment is outsourced
     * or inhouse and tries to update the Appointment as is appropriate. Field types are checked by parsing
     * and if an error is thrown an alert is shown. It then updates the Observable List of all Appointments
     * and returns the main menu.
     * @param actionEvent button click
     * @throws IOException IOException*/
    @Deprecated
    public void ApptSaveHandler(ActionEvent actionEvent) throws IOException {
        int apID = 0;
        String apTitle = null;
        String apDesc = null;
        String apLocation = null;
        LocalDate apDate = null;
        LocalTime apStart = null;
        LocalTime apEnd = null;
        int apCID = 0;
        int apUID = 0;
        String apContact = null;
        String apType = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (!newAppointment) {
            apID = Integer.parseInt(ApptID.getText());
        }else {apID = 0;};

        if(ApptCID.getValue() != null) {
            apCID = Integer.parseInt(ApptCID.getValue().toString());
        }else{showAlert("Please check the Customer_ID value"); return;}

        if(ApptUID.getValue() != null) {
            apUID= Integer.parseInt(ApptUID.getValue().toString());
        }else{showAlert("Please check the User_ID value"); return;}

        if(ApptContact.getValue() != null && !((String)ApptContact.getValue()).isEmpty()) {
            apContact= (String)ApptContact.getValue();
        }else{showAlert("Please check the Contact value"); return;}

        if(ApptDate.getValue() != null){
            apDate = LocalDate.parse(ApptDate.getValue().toString(), formatter);
        } else {
            showAlert("Please check the date value");
            return;
        }

        if(ApptStart.getValue() != null) {
            String s = ApptStart.getValue().toString();
            if (s.length() < 7) {
                s = "0" + s;
            }
            apStart = LocalTime.parse(s, DateTimeFormatter.ofPattern("hh:mma", Locale.getDefault()));
        }
        else {
            showAlert("Please check the start time");
            return;
        }

        if(ApptEnd.getValue() != null){
            String t = ApptEnd.getValue().toString();
            if(t.length() <7){ t = "0"+t;}
            apEnd = LocalTime.parse(t, DateTimeFormatter.ofPattern("hh:mma", Locale.getDefault()));
            } else {
                showAlert("Please check the end time");
                return;
            }

        LocalDateTime localDateTimeStart = apDate.atTime(apStart);
        LocalDateTime localDateTimeEnd = apDate.atTime(apEnd);

        if(!(checkHours(localDateTimeEnd) && checkHours(localDateTimeStart))){
            showAlert("Appointment time is outside business hours!");
            return;
            }

        if(localDateTimeStart.isAfter(localDateTimeEnd)){
            showAlert("Please check appointment times");
            return;
            }

        if(checkOverlap(apCID, localDateTimeStart, localDateTimeEnd)){
            showAlert("Appointment overlaps with existing appointment.");
            return;
            }

        String ApptStartString = localDateTimeStart.toString();
        String ApptEndString = localDateTimeEnd.toString();

        if(!ApptTitle.getText().isEmpty()){
               apTitle = ApptTitle.getText();
        } else {
               showAlert("Please check the title value");
                return;
        }

        if(!ApptDesc.getText().isEmpty()){
            apDesc = ApptDesc.getText();
        } else {
            showAlert("Please check the description value");
            return;
        }

        if(!ApptLocation.getText().isEmpty()){
            apLocation = ApptLocation.getText();
        } else {
                showAlert("Please check the location value");
                return;
        }

        if (ApptType.getValue() != null)  {
            apType = (String) ApptType.getValue();
        } else {
            showAlert("Please check the type value");
            return;
        }

        Appointment pass = new Appointment(apID, apTitle, apDesc, apLocation,apType,ApptStartString,
                    ApptEndString, localDateTimeStart, localDateTimeEnd,apCID,apUID, apContact);
        if (newAppointment) {
            DatabaseIO.addAppointment(pass);
            cancelHandler(actionEvent);
        } else{
            DatabaseIO.updateAppointment(pass);
            cancelHandler(actionEvent);
        }
    }

    /**This method shows an alert based on the passed string text.
     * @param alertText The text for the alert.*/
    public void showAlert(String alertText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(alertText);
        alert.showAndWait();
    }

    /** This method sends the user back to the main menu and toggles the boolean for a new appointment.
     * @param actionEvent button click
     * @throws IOException IOexception*/
    @Deprecated
    public void cancelHandler(ActionEvent actionEvent) throws IOException {
        newAppointment = true;
        clearValues();
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/TopLevelMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Clears all values on the form as needed.*/
    public void clearValues(){
            ApptID.setText("Will be auto-generated");
            ApptTitle.setText("");
            ApptDesc.setText("");
            ApptLocation.setText("");
            ApptType.setValue(null);
            ApptContact.setValue(null);
            ApptCID.setValue(null);
            ApptUID.setValue(null);
            ApptDate.setValue(null);
            ApptStart.setValue(null);
            ApptEnd.setValue(null);
    }

    /**Checks if the hours occur between 8am and 10pm EST time.
     * @param dt the datetime to check
     * @return boolean true/false*/
    public boolean checkHours(LocalDateTime dt){
        ZonedDateTime zonedDT = ZonedDateTime.of(dt, ZoneId.systemDefault());
        ZonedDateTime estdt = zonedDT.withZoneSameInstant(ZoneId.of("America/New_York"));
        if (estdt.getHour() > 22 || (estdt.getHour() == 22 && estdt.getMinute()>0)){
            return false;
        }
        if (estdt.getHour() < 8){
            return false;
        }
        return true;
    }
    /**Checks if the hours overlap existing appointments.
     * @param custID the customer to check appointments for
     * @param endTime the time the appointment is scheduled to end.
     * @param startTime the time the appointment is scheduled to begin.
     * @return boolean true/false*/
    public boolean checkOverlap(int custID, LocalDateTime startTime, LocalDateTime endTime){
        ObservableList<Appointment> tempList = DatabaseIO.getSelectAppointments(custID);
        for (Appointment a : tempList){
            if(a.getLocalDateTimeStart().isAfter(startTime) && a.getLocalDateTimeStart().isBefore(endTime)) {
            return true;
            }
            if(a.getLocalDateTimeEnd().isAfter(startTime) && a.getLocalDateTimeEnd().isBefore(endTime)) {
                return true;
            }
            if(a.getLocalDateTimeStart().isBefore(startTime) && a.getLocalDateTimeEnd().isAfter(endTime)) {
                return true;
            }
        }
        return false;
    }
}