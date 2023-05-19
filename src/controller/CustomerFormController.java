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
    static boolean newCustomer = true;

    Stage stage;
    Parent scene;

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
     * a Customer object and sets the local variable.
     * @param p The customer passed from another class.*/
    public static void receiveData(Customer p){
        passedCustomer = p;
        newCustomer = false;
    }

    @javafx.fxml.FXML
    public void countryComboActionHandler(ActionEvent actionEvent) {
        int selectedIndex = comboCountry.getSelectionModel().getSelectedIndex();
        Object selectedItem = comboCountry.getSelectionModel().getSelectedItem().toString();
        comboDivision.setItems(DatabaseIO.getDivisionCombo((String)comboCountry.getValue()));
    }

    @javafx.fxml.FXML
    public void divisionComboActionHandler(ActionEvent actionEvent) {
        int selectedIndex = comboDivision.getSelectionModel().getSelectedIndex();
        Object selectedItem = comboDivision.getSelectionModel().getSelectedItem().toString();
    }

    /** This method sends the user back to the main menu.
     * @param actionEvent button click
     * @throws IOException IOexception*/
    @Deprecated
    public void cancelHandler(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/TopLevelMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This is the save function for a modified product.  First it finds the index of where the modified
     * product occurs in the ObservableList of all products.  Then it checks if the min/max and inventory
     * values are appropriate and returns an alert if not. It then checks if the product is outsourced
     * or inhouse and tries to update the product as is appropriate. Field types are checked by parsing
     * and if an error is thrown an alert is shown. It then updates the Observable List of all products
     * and returns the main menu.
     * @param actionEvent button click*/
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
        division = (String) selectedItemDiv;} else
        {showAlert("Please pick a division");
            return;}

        ObservableList<Appointment> newList = FXCollections.observableArrayList();

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


}