package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Optional;

/**
 * This class controls the addProductForm fxml file. It contains the code to allow
 * a user to enter data into a form and have that data create a new product object. This object
 * is then saved to an ObservableList which persists over the life of the application instance.
 */

public class AddCustomerFormController
{
    @javafx.fxml.FXML
    private AnchorPane ap;
    @javafx.fxml.FXML
    private TextField newProductID;
    @javafx.fxml.FXML
    private TextField newProductName;
    @javafx.fxml.FXML
    private TextField newProductInv;
    @javafx.fxml.FXML
    private TextField newProductPrice;
    @javafx.fxml.FXML
    private TextField newProductMax;
    @javafx.fxml.FXML
    private TextField newProductMin;
    @javafx.fxml.FXML
    private TextField searchForPartField;
    @javafx.fxml.FXML
    private TableView partsTable;
    @javafx.fxml.FXML
    private TableColumn partTablePartID;
    @javafx.fxml.FXML
    private TableColumn partTablePartName;
    @javafx.fxml.FXML
    private TableColumn partTableIL;
    @javafx.fxml.FXML
    private TableColumn partTablePrice;
    @javafx.fxml.FXML
    private TableView associatedPartsTable;
    @javafx.fxml.FXML
    private TableColumn associatedPartID;
    @javafx.fxml.FXML
    private TableColumn associatedPartName;
    @javafx.fxml.FXML
    private TableColumn associatedPartIL;
    @javafx.fxml.FXML
    private TableColumn associatedPartPrice;
    /** An object to help switch screens */
    Stage stage;
    /** An object to help switch screens */
    Parent scene;
    /** An integer to create a unique part ID.*/
    int numberID = 1;
    /** An ObservableList to track parts associated with products */
//    ObservableList<Part> newList =  FXCollections.observableArrayList();
    //needs to be outside function

    /** This is the initialize method for this controller.  This function automatically runs
     *  when the screen is loaded.  It is used here to iterate through the ObservableList
     *  containing all the recorded Products.  The largest unique part identification number
     *  is recorded in the numberID variable and incremented by one to create a new unique product number.
     *  This could be improved in the future by creating product IDs based on Part IDs.  Additionally,
     *  Platform.runlater is called. This method is built in to Run a specified runnable at
     *  some unspecified time in the future. This provides time for the screen to load before a listener
     *  is set on a windows event - in this case, clicking on the x in the top right corner.  Also,
     *  object keys are associated with columns in the appropriate table.
     * */
    @javafx.fxml.FXML
    public void initialize() {
//        ObservableList<Product> p = Inventory.getAllProducts();
//        for (Product each : p) {
//            if (each.getId() > numberID) {
//                numberID = each.getId() + 1;
//            }
//        }
//        newProductID.setText(Integer.toString(numberID));
//
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setListener();
            }
        });
//
//        partsTable.setItems(Inventory.getAllParts());
//        partTablePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
//        partTablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        partTableIL.setCellValueFactory(new PropertyValueFactory<>("stock"));
//        partTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
//
//        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
//        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        associatedPartIL.setCellValueFactory(new PropertyValueFactory<>("stock"));
//        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
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

    /**This function allows the user to associate a part with a product.  The selected part is stored
     * in the variable newList, which can be saved as an object attribute in a new object
     * to replace the existing object.
     * @param actionEvent  the triggering user event*/
    @Deprecated
    public void associatePart(ActionEvent actionEvent){
//        Part passedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
//        if(partsTable.getSelectionModel().getSelectedItem() != null) {
//            newList.add(passedPart);
//        } else{
//            showAlert("Select a part to add!");
//        }
//        associatedPartsTable.setItems(newList);
    }

    /**This function allows the user to search for a Part and return the found values in the appropriate table.
     * The function first creates an ObservableList using the text of the search field to find
     * all Parts that have matched names, even if the name is incomplete.  If that list returns nothing,
     * the function tried to match via ID number.  It displays on the table matched parts.
     * @param event  the triggering user event*/
    @javafx.fxml.FXML
    public void searchByPart(Event event) {
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
//        return;
    }

    /**This method shows an alert based on the passed string text.
     * @param alertText The alert text.*/
    public void showAlert(String alertText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(alertText);
        alert.showAndWait();
    }

    /** This method sends the user back to the main menu.
     * @param actionEvent  the triggering user event
     * @throws IOException IOException*/
    @Deprecated
    public void cancelHandler(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/TopLevelMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }
    /**This function checks that the value is within allowed bounds and if the values are of the
     * correct type before saving.  The function use the minMaxCheck function to check that the inventory,
     * and max values are appropriate and returns if not. It then creates a new Product object by parsing
     * in the text values from the available fields. If a value cannot be parsed in, it is the wrong type
     * and an alert is given.  If all types are correct the new Product object is added to the existing
     * observablelist of products, the unique number generator is incremented and the user is returned
     * to the main screen.
     * @param actionEvent  the triggering user event*/
    @Deprecated
    public void saveHandler(ActionEvent actionEvent) throws IOException {
//        try{
//        if (!minMaxCheck(Integer.parseInt(newProductInv.getText()),
//                Integer.parseInt(newProductMin.getText()),
//                Integer.parseInt(newProductMax.getText()))) {
//            return;
//        }}
//        catch (Exception e) {
//            showAlert("Please make sure min, max and inventory fields are filled in correctly!");
//            return;
//        }
//
//            int productId = 0;
//            try{
//                productId = Integer.parseInt(newProductID.getText());
//            } catch (Exception e) {
//                showAlert("Please check that ID is an integer.");
//                return;
//            }
//            String productName = newProductName.getText();
//            double productPrice = 0;
//            try{
//                productPrice = Double.parseDouble(newProductPrice.getText());
//            } catch (Exception e) {
//                showAlert("Please check that the Price is a number.");
//                return;
//            }
//            int productInv = 0;
//            try{
//                productInv = Integer.parseInt(newProductInv.getText());
//            } catch (Exception e) {
//                showAlert("Please check that Inventory is an integer.");
//                return;
//            }
//            int productMin = 0;
//            try{
//                productMin = Integer.parseInt(newProductMin.getText());
//            } catch (Exception e) {
//                showAlert("Please check that min is an integer.");
//                return;
//            }
//            int productMax = 0;
//            try{
//                productMax = Integer.parseInt(newProductMax.getText());
//            } catch (Exception e) {
//                showAlert("Please check that max is an integer.");
//                return;
//            }
//
//            Product p = new Product(newList,productId,productName,productPrice,productInv,
//                    productMin,productMax);
//            Inventory.addProduct(p);
//            numberID++;
//            cancelHandler(actionEvent);
////        } catch (Exception e){
////            showAlert("Please make sure all the fields are filled in correctly!");
////        }
    }

    /**This function allows a user to diassociate a part from a product.  The function first confirms
     * the action through a confirmation alert and then removes the Part from newList, which is the
     * observablelist tracking associated parts during this session.  The appropriate table is then updated
     * @param actionEvent  the triggering user event*/
    @Deprecated
    public void removeAssociatedPart(ActionEvent actionEvent) {
//        if (confirmationAlert("Are you sure you want to remove that part?")){
//            Part passedPart = (Part) associatedPartsTable.getSelectionModel().getSelectedItem();
//            if (associatedPartsTable.getSelectionModel().getSelectedItem() != null) {
//                newList.remove(passedPart);
//            } else {
//                showAlert("Select a part to add!");
//            }
//            associatedPartsTable.setItems(newList);
//        }
    }

    /**This method checks that an inventory value is less than or equal to a maximum and greater than
     * or equal to a minimum.
     * @return if the given stock is equal to or between the min and the max
     * @param min  min value
     * @param mid  mid value
     * @param max  max value*/
    public boolean minMaxCheck(int mid, int min, int max){
//        if (mid <= max && mid >= min){
//            return true;
//        }
//        if(min>max){
//            showAlert("Min can not be greater than max!");
//            return false;
//        }
//        if(mid<min){
//            showAlert("Stock can not be less than min!");
//            return false;
//        }
//        if(mid>max){
//            showAlert("Stock can not be greater than max!");
//            return false;
//        }
//        showAlert("Please check the Min, Max and available stock values");
        return false;
    }


    /**This is a confirmation alert.  It is used to confirm a user's input and actions are taken
     * or canceled based on what is selected.
     * @return whether the user confirms their action
     * @param alertText Text string for alert box.*/
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

}