package Controller;

import Database.DatabaseIO;
import Model.Appointment;
import Model.Customer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**This is the controller for the primary application menu.*/
public class TopLevelMenu implements Initializable
{
    Stage stage;
    Parent scene;

//    private Part passedPart = null;

    @javafx.fxml.FXML
    private TableColumn partTablePartID;
    @javafx.fxml.FXML
    private TableColumn partTablePartName;
    @javafx.fxml.FXML
    private TableColumn partTableIL;
    @javafx.fxml.FXML
    private TableColumn partTablePrice;
    @javafx.fxml.FXML
    private TableColumn colProdID;
    @javafx.fxml.FXML
    private TableColumn colProdName;
    @javafx.fxml.FXML
    private TableColumn colProdIL;
    @javafx.fxml.FXML
    private TableColumn colProductPrice;
    @javafx.fxml.FXML
    private AnchorPane mainAP;
    @javafx.fxml.FXML
    private TextField searchForCustomerField;
    @javafx.fxml.FXML
    private TableView customersTable;
    @javafx.fxml.FXML
    private TextField searchForAppointmentField;
    @javafx.fxml.FXML
    private TableView appointmentsTable;


    /**This function sets up the initial values.  It brings in all the parts and products in the system
     * and associates their attributes to columns on the tables.  It also uses Platform.runlater to reset
     * a listener on the window that is changed in other menus.*/
    @Deprecated
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        partsTable.setItems(Inventory.getAllParts());
//        partTablePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
//        partTablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        partTableIL.setCellValueFactory(new PropertyValueFactory<>("stock"));
//        partTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
//
//        productsTable.setItems(Inventory.getAllProducts());
//        colProdID.setCellValueFactory(new PropertyValueFactory<>("id"));
//        colProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        colProdIL.setCellValueFactory(new PropertyValueFactory<>("stock"));
//        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
//
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

    /**This method shows an alert based on the passed string text.
     * @param alertText The text for the alert.*/
    public void showAlert(String alertText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(alertText);
        alert.showAndWait();
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
    @javafx.fxml.FXML
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
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/addAppointmentForm.fxml"));
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

    /**This function moves to the modify part menu. It calls a function in the controller for
     * the modify part menu to pass data identifying the part to be modified. If the function
     * is requested but no part is selected it displays an alert.
     * @param actionEvent the button being pressed
     * @throws IOException IOException*/
    public void modifyAppointmentButtonAction(ActionEvent actionEvent) throws IOException {
        //had to include a check to make sure something was selected
        if(appointmentsTable.getSelectionModel().getSelectedItem() != null) {
            Appointment passedAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
            ModifyAppointmentFormController.receiveData(passedAppointment);
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyPartForm.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } else{
            showAlert("Select a part to modify!");
        }
    }

    /**This function deletes the selected part. Before deletion it uses a confirmation alert to
     * confirm the user activity.  Additionally, if no part is deleted, it alerts the user.
     * @param actionEvent the button being pressed*/
    public void deleteAppointmentButtonAction(ActionEvent actionEvent) {
//        if(confirmationAlert("Are you sure you want to delete that part?")) {
//            Part p = (Part) partsTable.getSelectionModel().getSelectedItem();
//            if (!Inventory.deletePart(p)) {
//                showAlert("Part not deleted");
//            }
//        }
    }
    /**This function moves to the add customer menu.
     * @param actionEvent the button being pressed
     * @throws IOException IOException*/
    public void addCustomerButtonAction(ActionEvent actionEvent) throws IOException{
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/addCustomerForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**This function moves to the modify customer menu. It calls a function in the controller for
     * the modify customer menu to pass data identifying the customer to be modified.  If the function
     * is requested but no customer is selected it displays an alert.
     * @param actionEvent the button being pressed
     * @throws IOException IOException*/
    public void modifyCustomerButtonAction(ActionEvent actionEvent) throws IOException {
        if(customersTable.getSelectionModel().getSelectedItem() != null) {
            Customer passCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
            ModifyCustomerFormController.receiveData(passCustomer);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyProductForm.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } else{
            showAlert("Select a product to modify!");
        }
    }
    /**This function deletes the selected product. Before deletion it uses a confirmation alert to
     * confirm the user activity.  Additionally, it checks to see if a product has an associated
     * part, and if it does it does not delete hte product. If no product is deleted, it alerts the user.
     * @param actionEvent the button being pressed*/
    public void deleteCustomerButtonAction(ActionEvent actionEvent) {
        if(confirmationAlert("Are you sure you want to delete that product?")) {
            Customer p = (Customer) customersTable.getSelectionModel().getSelectedItem();
            if (p.getAllAssociatedParts().size() > 1) {
                showAlert("Cannot delete a customer that has an appointment associated with them.");
                return;
            }
            if (!DatabaseIO.deleteCustomer(p)) {
                showAlert("Product not deleted");
            }
        }
    }
    /**this function close the program when called.  The user must confirm the exit.
     * @param actionEvent the event*/
    public void exitHandler(ActionEvent actionEvent) {
        if(confirmationAlert("Are you sure you want to exit?")){
            Platform.exit();}
    }
}