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

//    private Part passedPart = null;

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

    static int indexSelected;
    static int currentUserID;
    static boolean firstTime = true;


    /**This function sets up the initial values.  It brings in all the parts and products in the system
     * and associates their attributes to columns on the tables.  It also uses Platform.runlater to reset
     * a listener on the window that is changed in other menus.*/
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
        tableStart.setCellValueFactory(new PropertyValueFactory<>("localDateTimeStart"));
        tableEnd.setCellValueFactory(new PropertyValueFactory<>("localDateTimeEnd"));
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
     * @param uid The Part passed from another class.*/
    public static void receiveData(int uid){
        currentUserID = uid;
    }

    /**provide an alert when there is an appointment within 15 minutes of the user’s log-in.
     * A custom message should be displayed in the user interface and include the appointment ID, date,
     * and time. If the user does not have any appointments within 15 minutes of logging in,
     * display a custom message in the user interface indicating there are no upcoming appointments.*/
    public void checkFifteenMinutes(int uid){
        boolean noOverlap = true;
        LocalDateTime nowDT = LocalDateTime.now();
        ObservableList<Appointment> tempList = DatabaseIO.getAllAppointments();
        for (Appointment a : tempList){
            if (a.getLocalDateTimeStart().isBefore(nowDT.plusMinutes(15))&& a.getLocalDateTimeStart().isAfter(nowDT)){
                noOverlap = false;
                String alertText = "Appointment "+a.getApID()+ " at "+a.getLocalDateTimeStart() +
                        " is begining in the next 15 minutes";
                showAlert(alertText);
            }
        }
        if(noOverlap){
            showAlert("There are no upcomming appointments in the next 15 minutes.");
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

    /**This function allows the user to search for a Part and return the found values in the appropriate table.
     * The function first creates an ObservableList using the text of the search field to find
     * all Parts that have matched names, even if the name is incomplete.  If that list returns nothing,
     * the function tried to match via ID number.  It displays on the table matched parts.
     * @param event  the triggering user event*/
    @Deprecated
    public void searchByAppointment(Event event) {
//        String q = searchForPartField.getText();
//        ObservableList<Part> searchedParts = Inventory.lookupPart(q);
//        if (searchedParts.size() == 0){
//            try{
//                int partID = Integer.parseInt(q);
//                Part selectedPart = Inventory.lookupPart(partID);
//                if (selectedPart != null){
//                    searchedParts.add(selectedPart);
//                }else{
//                    showAlert("Part not found!");
//                    searchForPartField.setText("");
//                    searchedParts = Inventory.getAllParts();
//                }
//            }catch (Exception e)
//            {
//                showAlert("Part not found!");
//                searchForPartField.setText("");
//                searchedParts = Inventory.getAllParts();
//            }
//        }
//        partsTable.setItems(searchedParts);
    }

    /**This function allows the user to search for a Product and return the found values in the appropriate table.
     * The function first creates an ObservableList using the text of the search field to find
     * all Products that have matched names, even if the name is incomplete.  If that list returns nothing,
     * the function tried to match via ID number.  It displays on the table matched products.
     * @param event  the triggering user event*/
    @javafx.fxml.FXML
    public void searchByCustomer(Event event) {
//        productsTable.setItems(searchedProducts);
//        String q = searchForProductField.getText();
//        ObservableList<Product> searchedProducts = Inventory.lookupProduct(q);
//        if (searchedProducts.size() == 0){
//            try{
//                int productID = Integer.parseInt(q);
//                Product selectedProduct = Inventory.lookupProduct(productID);
//                if (selectedProduct != null){
//                    searchedProducts.add(selectedProduct);
//                }else{
//                    showAlert("Product not found!");
//                    searchForProductField.setText("");
//                    searchedProducts = Inventory.getAllProducts();
//                }
//            }catch (NumberFormatException e)
//            {
//                //ignore
//            }
//        }
    }

    /**This function moves to the add part menu.
     * @param actionEvent the button being pressed
     * @throws IOException IOException*/
    public void addAppointmentButtonAction(ActionEvent actionEvent) throws IOException {
        indexSelected = customersTable.getSelectionModel().getSelectedIndex();
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AppointmentForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This function moves to the add part menu.
     * @param actionEvent the button being pressed
     * @throws IOException IOException*/
    public void logoutHandler(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/FirstScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This function moves to the add part menu.
     * @param actionEvent the button being pressed
     * @throws IOException IOException*/
    public void handleReporting(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Reporting.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This function moves to the modify part menu. It calls a function in the controller for
     * the modify part menu to pass data identifying the part to be modified. If the function
     * is requested but no part is selected it displays an alert.
     * @param actionEvent the button being pressed
     * @throws IOException IOException*/
    public void modifyAppointmentButtonAction(ActionEvent actionEvent) throws IOException {
        //had to include a check to make sure something was selected
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

    /**This function deletes the selected part. Before deletion it uses a confirmation alert to
     * confirm the user activity.  Additionally, if no part is deleted, it alerts the user.
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
    /**This function deletes the selected product. Before deletion it uses a confirmation alert to
     * confirm the user activity.  Additionally, it checks to see if a product has an associated
     * part, and if it does it does not delete hte product. If no product is deleted, it alerts the user.
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
        }
    }

//    /**This function is called from another class to pass a particular piece of data.  Here is takes in
//     * a Part object and sets the local variable.
//     * @param p The Part passed from another class.*/
//    public static void receiveData(int indexPassed){
//        indexSelected = indexPassed;
//    }

    /**this function close the program when called.  The user must confirm the exit.
     * @param actionEvent the event*/
    public void exitHandler(ActionEvent actionEvent) {
        if(confirmationAlert("Are you sure you want to exit?")){
            Platform.exit();}
    }

    public void allCustomersAction(ActionEvent actionEvent){
        allRadioButton.setSelected(true);
        customersTable.getSelectionModel().clearSelection();
        appointmentsTable.setItems(DatabaseIO.getAllAppointments());
    }

    public void seeAllAppointments(ActionEvent actionEvent){
        if(customersTable.getSelectionModel().getSelectedItem() == null) {
            appointmentsTable.setItems(DatabaseIO.getAllAppointments());
        } else{
            selectedCustomerHandler();
        }

    }
    public void seeMonthAppointments(ActionEvent actionEvent){
        if(customersTable.getSelectionModel().getSelectedItem() == null) {
            ObservableList<Appointment> tempList = DatabaseIO.getAllAppointments();
            ObservableList<Appointment> newList = FXCollections.observableArrayList();
            for (Appointment a : tempList){
//                LocalDateTime temp = a.getLocalDateTimeStart();
//                LocalDateTime onemonthfromnow = LocalDateTime.now().plusMonths(1);
//                boolean check = a.getLocalDateTimeEnd().isAfter(LocalDateTime.now().plusMonths(1));
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
            for (Appointment a : tempList){
                if(a.getLocalDateTimeEnd().isBefore(LocalDateTime.now().plusMonths(1))){
                    newList.add(a);
                }
            }
            appointmentsTable.setItems(newList);
        }

    }

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