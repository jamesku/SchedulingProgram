package controller;

import model.Appointment;
import model.Customer;
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

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class AppointmentFormController
{
    @javafx.fxml.FXML
    private AnchorPane ap;
    @javafx.fxml.FXML
    private Label partsVariableLabel;
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
     *  when the screen is loaded.  It is used here set up some information about the Part that
     *  needs to be modified. Additionally, Platform.runlater is called. This method is built in
     *  to Run a specified runnable at some unspecified time in the future. This provides time
     *  for the screen to load before a listener is set on a windows event - in this case,
     *  clicking on the x in the top right corner.
     * */
    @javafx.fxml.FXML
    public void initialize() {
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
     * a Part object and sets the local variable.
     * @param p The Part passed from another class.*/
    public static void receiveData(Appointment p){
        passedAppointment = p;
        newAppointment = false;
    }

    /**This is the save function for a modified part.  First it finds the index of where the modified
     * part occurs in the ObservableList of all parts.  Then it checks if the min/max and inventory
     * values are appropriate and returns an alert if not. It then checks if the part is outsourced
     * or inhouse and tries to update the part as is appropriate. Field types are checked by parsing
     * and if an error is thrown an alert is shown. It then updates the Observable List of all parts
     * and returns the main menu.
     * @param actionEvent button click
     * @throws IOException IOException*/
    @Deprecated
    public void AppointmentSaveHandler(ActionEvent actionEvent) throws IOException {
    int apID = 0;
    String apTitle = null;
    String apDesc = null;
    String apLocation = null;
    LocalDate apDate = null;
    LocalTime apStart = null;
    LocalTime apEnd = null;
    int apCID = 0;
    int apUID = 0;
    int apContact = 0;
    String apType = null;

    apID = Integer.parseInt(ApptID.getText());
    apCID= Integer.parseInt((String)ApptCID.getValue());
    apUID= Integer.parseInt((String)ApptUID.getValue());
    apContact= Integer.parseInt((String)ApptContact.getValue());

        if((ApptDate.getValue() != null)){
            apDate = (LocalDate) ApptType.getValue();
        } else {
            showAlert("Please check the type value");
            return;
        }

    if(!((String)ApptStart.getValue()).isEmpty()){
            apStart = (LocalTime) ApptStart.getValue();
        } else {
            showAlert("Please check the start time");
            return;
        }

    if(!((String)ApptEnd.getValue()).isEmpty()){
            apEnd = (LocalTime)ApptEnd.getValue();
        } else {
            showAlert("Please check the end time");
            return;
        }

        LocalDateTime localDateTimeStart = apDate.atTime(apStart);
        LocalDateTime localDateTimeEnd = apDate.atTime(apEnd);

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
            apTitle = ApptLocation.getText();
        } else {
            showAlert("Please check the location value");
            return;
        }


        if(!((String)(ApptType.getValue())).isEmpty()){
            apType = (String)ApptType.getValue();
        } else {
            showAlert("Please check the type value");
            return;
        }

        Appointment pass = new Appointment(apID, apTitle, apDesc, apLocation,apType, localDateTimeStart,
                localDateTimeEnd,apCID,apUID, apContact);
        if (newAppointment) {
            DatabaseIO.addAppointment(pass);
        } else{
            DatabaseIO.updateAppointment(pass);
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

}