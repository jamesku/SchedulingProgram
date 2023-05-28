package controller;

import database.DatabaseIO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.UtcConversion;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**This is the controller for the primary application menu.*/
public class TopLevelMenu implements Initializable
{
    Stage stage;
    Parent scene;

    @javafx.fxml.FXML
    private AnchorPane mainAP;
    @javafx.fxml.FXML
    private TextField searchForCustomerField;
    @javafx.fxml.FXML
    private TableView customersTable;
    @javafx.fxml.FXML
    private TableView appointmentsTable;
    @javafx.fxml.FXML
    private TableColumn tableCustomerID;
    @javafx.fxml.FXML
    private TableColumn tableCustomerName;
    @javafx.fxml.FXML
    private TableColumn tableAddress;
    @javafx.fxml.FXML
    private TableColumn tablePostalCode;
    @javafx.fxml.FXML
    private TableColumn tablePhone;
    @javafx.fxml.FXML
    private TableColumn tableCountry;
    @javafx.fxml.FXML
    private TableColumn tableDivision;
    @javafx.fxml.FXML
    private TableColumn tableApptID;
    @javafx.fxml.FXML
    private TableColumn tableTitle;
    @javafx.fxml.FXML
    private TableColumn tableDescription;
    @javafx.fxml.FXML
    private TableColumn tableLocation;
    @javafx.fxml.FXML
    private TableColumn tableContact;
    @javafx.fxml.FXML
    private TableColumn tableType;
    @javafx.fxml.FXML
    private TableColumn tableStart;
    @javafx.fxml.FXML
    private TableColumn tableEnd;
    @javafx.fxml.FXML
    private TableColumn tableCID;
    @javafx.fxml.FXML
    private TableColumn tableUID;
    @javafx.fxml.FXML
    private ToggleGroup tGroup;
    @javafx.fxml.FXML
    private RadioButton allRadioButton;

    //used for tracking what customer is selected
    static int indexSelected;
    //used for tracking the program user
    static int currentUserID;
    //used for checking for appointments 15 minutes away only once at login.
    static boolean firstTime = true;

    /**This function sets up the initial values.  It brings in all the parts and products in the system
     * and associates their attributes to columns on the tables.  It checks if this is the first time its
     * being initialized to do the 15 minute appointment check and manages customer selection to show
     * appointments.
     * It also uses Platform.runlater to reset a listener on the window that is changed in other menus.*/
    @Deprecated
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customersTable.setItems(DatabaseIO.getAllCustomers());
        tableCustomerID.setCellValueFactory(new PropertyValueFactory<>("custID"));
        tableCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tablePostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        tablePhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tableCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        tableDivision.setCellValueFactory(new PropertyValueFactory<>("division"));

        tableApptID.setCellValueFactory(new PropertyValueFactory<>("apID"));
        tableTitle.setCellValueFactory(new PropertyValueFactory<>("apTitle"));
        tableDescription.setCellValueFactory(new PropertyValueFactory<>("apDesc"));
        tableLocation.setCellValueFactory(new PropertyValueFactory<>("apLocation"));
        tableType.setCellValueFactory(new PropertyValueFactory<>("apType"));
        tableContact.setCellValueFactory(new PropertyValueFactory<>("apContactName"));
        tableStart.setCellValueFactory(new PropertyValueFactory<>("apStartString"));
        tableEnd.setCellValueFactory(new PropertyValueFactory<>("apEndString"));
        tableCID.setCellValueFactory(new PropertyValueFactory<>("apCID"));
        tableUID.setCellValueFactory(new PropertyValueFactory<>("apUID"));

        if (firstTime) {
            checkFifteenMinutes(currentUserID);
            firstTime = false;
        }
        customersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedCustomerHandler();
            }
        });

        customersTable.getSelectionModel().selectIndices(indexSelected);

        Platform.runLater(new Runnable() {
            @Override public void run() {
                setListener();
            }
        });
    }

    /** This function sets a listener on a WindowsEvent.  This listener is set on the current
     * stage to gather close requests, which comes from the x in the top right corner. Since this action
     * is modified in other parts of the program, this function ensures that the original functionality
     * of closing the window occurs when anyone clicks on the x in the top right corner.
     * This had to be run after the screen is loaded and so it relies on
     * Platform.runlater.*/
    public void setListener(){
        Stage stage = (Stage) (mainAP.getScene().getWindow());
        stage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent windowEvent) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    /**This function is called from another class to pass a particular piece of data.  Here is takes in
     * a userID and sets the local variable.
     * @param uid The userID passed from another class.*/
    public static void receiveData(int uid){
        currentUserID = uid;
    }

    /**This provides an alert when an appointment is within 15 minutes of the user log-in.
     * A custom message is displayed in the user interface that includes the appointment ID, date,
     * and time. All appointments are requested from the database and then iterated through.
     * The evaluation is done in system using datetime methods.
     * If there is not an appointment with 15, another alert is shown indicating this.
     * @param uid the user ID to check*/
    public void checkFifteenMinutes(int uid){
        boolean noOverlap = true;
        LocalDateTime nowDT = LocalDateTime.now();
        ObservableList<Appointment> tempList = DatabaseIO.getAllAppointments();
        for (Appointment a : tempList){
            if (a.getLocalDateTimeStart().isBefore(nowDT.plusMinutes(15))&& a.getLocalDateTimeStart().isAfter(nowDT)){
                noOverlap = false;
                String alertText = "Appointment "+a.getApID()+ " at "+
                        UtcConversion.dtFormat(a.getLocalDateTimeStart()) +
                        " is begining in the next 15 minutes";
                showAlert(alertText);
            }
        }
        if(noOverlap){
            showAlert("There are no upcoming appointments in the next 15 minutes.");
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

    /**This function resets options to show all appointments of a specific customer when one is selected.*/
    public void selectedCustomerHandler(){
        allRadioButton.setSelected(true);
        Customer selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
        appointmentsTable.setItems(DatabaseIO.getSelectAppointments(selectedCustomer.getCustID()));
    }

    /**This method shows a confirmation alert based on the passed string text.
     * @param alertText The text for the alert.
     * @return whether the action is confirmed*/
    public boolean confirmationAlert(String alertText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(alertText);
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
            return false;
        } else if (option.get() == ButtonType.OK) {
            return true;
        } else if (option.get() == ButtonType.CANCEL) {
            return false;
        } else {
            return false;
        }
    }

    /**This function moves to the add/modify appointments menu.
     * @param actionEvent the button being pressed
     * @throws IOException IOException*/
    public void addAppointmentButtonAction(ActionEvent actionEvent) throws IOException {
        indexSelected = customersTable.getSelectionModel().getSelectedIndex();
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AppointmentForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This function logs out and moves to the login menu menu.
     * @param actionEvent the button being pressed
     * @throws IOException IOException*/
    public void logoutHandler(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/FirstScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This function moves to the reporting menu.
     * @param actionEvent the button being pressed
     * @throws IOException IOException*/
    public void handleReportingButton(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Reporting.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This function moves to the modify appointment menu. It calls a function in the controller for
     * the modify appointment menu to pass data identifying the appointment to be modified. If the function
     * is requested but no appointment is selected it displays an alert.
     * @param actionEvent the button being pressed
     * @throws IOException IOException*/
    public void modifyAppointmentButtonAction(ActionEvent actionEvent) throws IOException {
        indexSelected = customersTable.getSelectionModel().getSelectedIndex();
        if(appointmentsTable.getSelectionModel().getSelectedItem() != null) {
            Appointment passedAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
            AppointmentFormController.receiveData(passedAppointment);
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AppointmentForm.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } else{
            showAlert("Select an appointment to modify!");
        }
    }

    /**This function deletes the selected appointment. Before deletion it uses a confirmation alert to
     * confirm the user activity.  If no appointment is deleted, it alerts the user, otherwise
     * appointment id and type are deleted.
     * @param actionEvent the button being pressed*/
    public void deleteAppointmentButtonAction(ActionEvent actionEvent) {
        indexSelected = customersTable.getSelectionModel().getSelectedIndex();
        if(confirmationAlert("Are you sure you want to delete that Appointment?")) {
            Appointment p = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
            int tempApID = p.getApID();
            String tempApType = p.getApType();
            if (!DatabaseIO.deleteAppointment(tempApID)) {
                showAlert("Appointment not deleted");
            } else {
                if(customersTable.getSelectionModel().getSelectedItem() == null) {
                    appointmentsTable.setItems(DatabaseIO.getAllAppointments());
                }
                selectedCustomerHandler();
                showAlert("Appointment "+tempApID+", type: "+tempApType+" deleted.");
            }
        }
    }

    /**This function moves to the add customer menu.
     * @param actionEvent the button being pressed
     * @throws IOException IOException*/
    public void addCustomerButtonAction(ActionEvent actionEvent) throws IOException{
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/CustomerForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This function moves to the modify customer menu. It calls a function in the controller for
     * the modify customer menu to pass data identifying the customer to be modified.  If the function
     * is requested but no customer is selected it displays an alert.
     * @param actionEvent the button being pressed
     * @throws IOException IOException*/
    public void modifyCustomerButtonAction(ActionEvent actionEvent) throws IOException {
        indexSelected = customersTable.getSelectionModel().getSelectedIndex();
        if(customersTable.getSelectionModel().getSelectedItem() != null) {
            Customer passCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
            CustomerFormController.receiveData(passCustomer);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerForm.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } else{
            showAlert("Select a customer record to modify!");
        }
    }

    /**This function deletes the selected customer. Before deletion it uses a confirmation alert to
     * confirm the user activity.  If confirmed, it deletes all appointments first. If no customer
     * is deleted, it alerts the user.  It then reloads the customers table.
     * @param actionEvent the button being pressed*/
    public void deleteCustomerButtonAction(ActionEvent actionEvent) {
        if(confirmationAlert("Are you sure you want to delete that Customer record? All associated appointments " +
                "will be deleted as well.")) {
            Customer p = (Customer) customersTable.getSelectionModel().getSelectedItem();
            DatabaseIO.deleteAssociatedAppointments(p.getCustID());
            if(DatabaseIO.deleteCustomer(p.getCustID())){
                showAlert("Customer removed");
            }
            customersTable.setItems(DatabaseIO.getAllCustomers());
            appointmentsTable.getItems().clear();
        }
    }

    /**this function close the program when called.  The user must confirm the exit.
     * @param actionEvent the event*/
    public void exitHandler(ActionEvent actionEvent) {
        if(confirmationAlert("Are you sure you want to exit?")){
            Platform.exit();}
    }

    /**The function request all customers and sets the customers table. It resets the radio options.
     * @param actionEvent button click*/
    public void allCustomersAction(ActionEvent actionEvent){
        allRadioButton.setSelected(true);
        customersTable.getSelectionModel().clearSelection();
        appointmentsTable.setItems(DatabaseIO.getAllAppointments());
    }

    /**The function request all appointments and sets the appointments table.
     * @param actionEvent button click*/
    public void seeAllAppointments(ActionEvent actionEvent){
        if(customersTable.getSelectionModel().getSelectedItem() == null) {
            appointmentsTable.setItems(DatabaseIO.getAllAppointments());
        } else{
            selectedCustomerHandler();
        }
    }

    /**The function request all appointments within the next month and sets the appointments table
     * based on a comparison done in system.
     * Based on if a customer is selected or not, the appointments table will have all appointments or
     * just select appointments.
     * <p><b>LAMBDA EXPRESSION is included here as an alternative to move through an ObservableList
     * and check if the object date qualifies.  It replaces the need for an explicit loop.</b></p>
     * @param actionEvent button click*/
    public void seeMonthAppointments(ActionEvent actionEvent){
        if(customersTable.getSelectionModel().getSelectedItem() == null) {
            ObservableList<Appointment> tempList = DatabaseIO.getAllAppointments();
            ObservableList<Appointment> newList = FXCollections.observableArrayList();
            for (Appointment a : tempList){
                if(a.getLocalDateTimeEnd().isBefore(LocalDateTime.now().plusMonths(1))){
                    newList.add(a);
                }
            }
            appointmentsTable.setItems(newList);
        } else{
            Customer selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
            appointmentsTable.setItems(DatabaseIO.getSelectAppointments(selectedCustomer.getCustID()));
            ObservableList<Appointment> tempList = appointmentsTable.getItems();
            ObservableList<Appointment> newList = FXCollections.observableArrayList();
            //added lambda expression here
            tempList.forEach( (a) -> {
                if(a.getLocalDateTimeEnd().isBefore(LocalDateTime.now().plusMonths(1))){
                    newList.add(a); }
                    });
            appointmentsTable.setItems(newList);
        }
    }

    /**The function request all appointments within the next week and sets the appointments table
     * based on a comparison done in system.
     * Based on if a customer is selected or not, the appointments table will have all appointments or
     * just select appointments.
     * @param actionEvent button click*/
    public void seeWeekAppointments(ActionEvent actionEvent){
        if(customersTable.getSelectionModel().getSelectedItem() == null) {
            ObservableList<Appointment> tempList = DatabaseIO.getAllAppointments();
            ObservableList<Appointment> newList = FXCollections.observableArrayList();
            for (Appointment a : tempList){
                if(a.getLocalDateTimeEnd().isBefore(LocalDateTime.now().plusDays(7))){
                    newList.add(a);
                }
            }
            appointmentsTable.setItems(newList);
        } else{
            Customer selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
            appointmentsTable.setItems(DatabaseIO.getSelectAppointments(selectedCustomer.getCustID()));
            ObservableList<Appointment> tempList = appointmentsTable.getItems();
            ObservableList<Appointment> newList = FXCollections.observableArrayList();
            for (Appointment a : tempList){
                if(a.getLocalDateTimeEnd().isBefore(LocalDateTime.now().plusDays(7))){
                    newList.add(a);
                }
            }
            appointmentsTable.setItems(newList);
        }
    }
}