package controller;

import Model.Customer;
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

/**This controls the Modify product menu.*/
public class ModifyCustomerFormController
{
    Stage stage;
    Parent scene;

    @javafx.fxml.FXML
    private AnchorPane ap;
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

    @javafx.fxml.FXML
    private TextField modifyProductID;
    @javafx.fxml.FXML
    private TextField modifyProductName;
    @javafx.fxml.FXML
    private TextField modifyProductInv;
    @javafx.fxml.FXML
    private TextField modifyProductPrice;
    @javafx.fxml.FXML
    private TextField modifyProductMax;
    @javafx.fxml.FXML
    private TextField modifyProductMin;

    /**A variable to hold the product to be modified*/
    private static Customer passedCustomer = null;

    /**An observable list to track associated parts*/
//    private ObservableList<Part> newList = FXCollections.observableArrayList();

    /**A variable to track where in the ObservableList of Products the Product to be modified is.*/
    int index;

    /** This is the initialize method for this controller.  This function automatically runs
     *  when the screen is loaded.  It is used here set up some information about the Product that
     *  needs to be modified and fill out the associated parts table.
     *  Additionally, Platform.runlater is called. This method is built in
     *  to Run a specified runnable at some unspecified time in the future. This provides time
     *  for the screen to load before a listener is set on a windows event - in this case,
     *  clicking on the x in the top right corner.
     *
     * */
    @javafx.fxml.FXML
    public void initialize() {

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
//
//        customerID.setText(passedProduct.getId()+"");
//        customerName.setText(passedProduct.getName());
//        customerInv.setText(passedProduct.getStock()+"");
//        modifyProductPrice.setText(passedProduct.getPrice()+"");
//        modifyProductMax.setText(passedProduct.getMax()+"");
//        modifyProductMin.setText(passedProduct.getMin()+"");
//
//        /**This iteration creates a temporary array to enable the cancel function to work correctly.
//         * Java links arraylists if they are just equated to each other, so a new list, filled with
//         * the same variables is important.*/
//        newList.clear();
//        for (int i = 0; i < passedProduct.getAllAssociatedParts().size(); i++) {
//            newList.add(passedProduct.getAllAssociatedParts().get(i));
//        }
//
//        associatedPartsTable.setItems(newList);
//
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
    }

    /**This associates a part with a product. It adds the associated part to the ObservableList
     * that is tracking associated parts.  If no part is selected, it prompts the user
     * to select a part
     * @param actionEvent button click*/
    @Deprecated
    public void associateAppointment(ActionEvent actionEvent){
//        Part passedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
//        if(partsTable.getSelectionModel().getSelectedItem() != null) {
//            newList.add(passedPart);
//            System.out.println(passedProduct);
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
//        return;
    }
    /**This method shows an alert based on the passed string text.
     * @param alertText the text used for the alert*/
    public void showAlert(String alertText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(alertText);
        alert.showAndWait();
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
    public void saveHandler(ActionEvent actionEvent) {
//        ObservableList<Product> allProducts = Inventory.getAllProducts();
//        index = allProducts.indexOf(passedProduct);
//        try{
//            if (!minMaxCheck(Integer.parseInt(modifyProductInv.getText()),
//                    Integer.parseInt(modifyProductMin.getText()),
//                    Integer.parseInt(modifyProductMax.getText()))) {
//                return;}
//        }
//            catch (Exception e) {
//                showAlert("Please make sure min, max and inventory fields are filled in correctly!");
//                return;
//        }
//            try {
//                int id = 0;
//                try {
//                    id = Integer.parseInt(modifyProductID.getText());
//                } catch (Exception e){
//                    showAlert("Please make sure the Product ID is correct.");
//                    return;
//                }
//                String name = modifyProductName.getText();
//                double price = 0;
//                try {
//                    price = Double.parseDouble(modifyProductPrice.getText());
//                } catch (Exception e){
//                    showAlert("Please check Product Price.");
//                    return;
//                }
//                int inv = 0;
//                try {
//                    inv = Integer.parseInt(modifyProductInv.getText());
//                } catch (Exception e) {
//                    showAlert("Please check that inventory is an integer.");
//                    return;
//                }
//                int min = 0;
//                try {
//                    min = Integer.parseInt(modifyProductMin.getText());
//                }catch (Exception e) {
//                    showAlert("Please check that min is an integer.");
//                    return;
//                }
//                int max = 0;
//                try {
//                    max = Integer.parseInt(modifyProductMax.getText());
//                } catch (Exception e) {
//                    showAlert("Please check that max is an integer.");
//                    return;
//                }
//                Product p = new Product(newList,id,name,price,inv,min,max);
//                Inventory.updateProduct(index, p);
//                cancelHandler(actionEvent);
//            } catch (Exception e) {
//                showAlert("Please make sure all the fields are filled in correctly!");
//            }
    }

    /**This function dissassociates a part from a product. It uses a selected product, checks with
     * the user that they want to remove the part fromt he product and then removes the part from
     * the product.  If a product is not selected, the user is prompted to select one.
     * @param actionEvent button click*/
    @Deprecated
    public void removeAssociatedPart(ActionEvent actionEvent) {
//        if (confirmationAlert("Are you sure you want to remove that part?")) {
//            Part passedPart = (Part) associatedPartsTable.getSelectionModel().getSelectedItem();
//            if (newList.remove(passedPart)) {
//                //ignore
//            } else {
//                showAlert("Select a product to remove!");
//            }
//            associatedPartsTable.setItems(newList);
//        }
    }

    /**This method checks that an inventory value is less than or equal to a maximum and greater than
     * or equal to a minimum.
     * @param max max value
     * @param min min value
     * @param mid mid value
     * @return boolean if the check passes*/
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
}