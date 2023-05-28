package controller;

import model.Appointment;
import model.Customer;
import database.DatabaseIO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**Controlls the cusomter add/modify form.*/
public class CustomerFormController
{
    @javafx.fxml.FXML
    private AnchorPane ap;
    @javafx.fxml.FXML
    private TextField customerID;
    @javafx.fxml.FXML
    private TextField customerName;
    @javafx.fxml.FXML
    private TextField customerAddress;
    @javafx.fxml.FXML
    private TextField customerPC;
    @javafx.fxml.FXML
    private TextField customerPhone;
    @javafx.fxml.FXML
    private ComboBox comboCountry;
    @javafx.fxml.FXML
    private ComboBox comboDivision;

    /**A variable to hold the product to be modified, if one is passed*/
    private static Customer passedCustomer = null;
    /**A boolean to determine if its a customer to be modified or a new customer.*/
    static boolean newCustomer = true;

    Stage stage;
    Parent scene;

    /** This is the initialize method for this controller.  This function automatically runs
     *  when the screen is loaded.  It is used here set up some information about the Customer that
     *  needs to be modified. Additionally, Platform.runlater is called. This method is built in
     *  to Run a specified runnable at some unspecified time in the future. This provides time
     *  for the screen to load before a listener is set on a windows event - in this case,
     *  clicking on the x in the top right corner.
     * */
    @javafx.fxml.FXML
    public void initialize() {

        comboCountry.setItems(DatabaseIO.getCountryCombo());

        if (newCustomer){
            customerID.setText("Will be auto-generated");
        } else {
            customerID.setText(passedCustomer.getCustID() + "");
            customerName.setText(passedCustomer.getName());
            customerAddress.setText(passedCustomer.getAddress() + "");
            customerPC.setText(passedCustomer.getPostalCode() + "");
            customerPhone.setText(passedCustomer.getPhoneNumber() + "");
            comboCountry.setValue(passedCustomer.getCountry());
            comboDivision.setValue(passedCustomer.getDivision());
            comboDivision.setItems(DatabaseIO.getDivisionCombo((String)comboCountry.getValue()));
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
     * a Customer object and sets the local variable.  It also triggers the newCustomer boolean to false.
     * @param p The customer passed from another class.*/
    public static void receiveData(Customer p){
        passedCustomer = p;
        newCustomer = false;
    }

    /** Handles a selection of a country in a combobox.  Using the selection it calls to the
     * database to get values set up the first level division combobox.
     * @param actionEvent actionEvent*/
    @javafx.fxml.FXML
    public void countryComboActionHandler(ActionEvent actionEvent) {
        int selectedIndex = comboCountry.getSelectionModel().getSelectedIndex();
        Object selectedItem = comboCountry.getSelectionModel().getSelectedItem().toString();
        comboDivision.setItems(DatabaseIO.getDivisionCombo((String)comboCountry.getValue()));
    }

    /** This method sends the user back to the main menu. It also resets the newCustomer boolean.
     * @param actionEvent button click
     * @throws IOException IOexception*/
    @Deprecated
    public void cancelHandler(ActionEvent actionEvent) throws IOException {
        newCustomer=true;
        clearValues();
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/TopLevelMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This is the save function for a new or modified customer.  It checks:
     * the customerID is an integer, if not, it defaults the value to 0,
     * the customer Name has a value,
     * the address has a value,
     * the postal code has an integer value,
     * the phone number has an integer value,
     * the country combobox has a selected value,
     * the division combobox has a selected value,
     * Field types are checked by parsing and if an error is thrown an alert is shown.
     * It then creates a customer object and passes it to the database for modification or insertion.
     * @param actionEvent button click
     * @throws java.io.IOException exception*/
    @Deprecated
    public void saveHandler(ActionEvent actionEvent) throws IOException {
        int custID = 0;
        String name = null;
        String address = null;
        String postalCode = null;
        String phoneNumber = null;
        String country;
        String division;

        try{
        custID = Integer.parseInt(customerID.getText());}
        catch (Exception e){
            custID=1;
        }
        if(!customerName.getText().isEmpty()){
            name = customerName.getText();
        } else {
            showAlert("Please check the customer name value");
            return;
        }

        if(!customerAddress.getText().isEmpty()){
            address = customerAddress.getText();
        } else {
            showAlert("Please check the customer address value");
            return;
        }

        try { int temp = Integer.parseInt(customerPC.getText()); postalCode = customerPC.getText();}
        catch (Exception e) {
            showAlert("Please check the postal code value.");
            return;
        }

        try { int temp = Integer.parseInt(customerPhone.getText()); phoneNumber=customerPhone.getText();}
        catch (Exception e){
            showAlert("Please check the phone number value, please use numbers only.");
            return;
        }

        if(comboCountry.getValue() != null){
            Object selectedItem = comboCountry.getSelectionModel().getSelectedItem().toString();
            country = (String) selectedItem;} else {
            showAlert("Please pick a country");
            return;
        }

        if(comboDivision.getValue() != null){
            Object selectedItemDiv = comboDivision.getSelectionModel().getSelectedItem().toString();
            division = (String) selectedItemDiv;} else {
            showAlert("Please pick a division");
            return;
        }

        Customer pass = new Customer(custID, name, address,postalCode,phoneNumber,country,division);
        if (newCustomer) {
            DatabaseIO.addCustomer(pass);
            cancelHandler(actionEvent);
        } else{
            DatabaseIO.updateCustomer(pass);
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

    /**Clears all values on the form as needed.*/
    public void clearValues(){
        customerID.setText("");
        customerName.setText("");
        customerAddress.setText("");
        customerPC.setText("");
        customerPhone.setText("");
        comboCountry.setValue(null);
        comboDivision.setValue(null);
    }
}