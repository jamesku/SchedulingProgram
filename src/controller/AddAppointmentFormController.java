package Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * This class controls the addPartForm fxml file. It contains the code to allow
 * a user to enter data into a form and have that data create a new object, either instantiated through
 * the InHouse class or the Outsourced class, both of which inherit from the Part class. This object
 * is then saved to an ObservableList which persists over the life of the application instance.
 */

public class AddAppointmentFormController {
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton addPartInHouse;
    @FXML
    private ToggleGroup addPartToggles;
    @FXML
    private RadioButton addPartOutsourced;
    @FXML
    private Label partsVariableLabel;
    @FXML
    private TextField newPartID;
    @FXML
    private TextField newPartName;
    @FXML
    private TextField newPartInv;
    @FXML
    private TextField newPartPrice;
    @FXML
    private TextField newPartMax;
    @FXML
    private TextField newPartVarField;
    @FXML
    private TextField newPartMin;
    @FXML
    private AnchorPane ap;

    /** A boolean to track if Parts are made in-house or outsourced */
    private Boolean inHouse = true;

    /** An integer to create a unique part ID.*/
    int numberID = 1;

    /** This is the initialize method for this controller.  This function automatically runs
     *  when the screen is loaded.  It is used here to iterate through the ObservableList
     *  containing all the recorded Parts.  The largest unique part identification number
     *  is recorded in the numberID variable and incremented by one to create a new unique part number.
     *  Originally, this number was based on the size of the array, but when Parts are deleted and then
     *  added later this methodology resulted in duplicate identification numbers. Additionally,
     *  Platform.runlater is called. This method is built in to Run a specified runnable at
     *  some unspecified time in the future. This provides time for the screen to load before a listener
     *  is set on a windows event - in this case, clicking on the x in the top right corner.
     * */
    @FXML
    public void initialize() {
//        ObservableList<Part> p = Inventory.getAllParts();
//        for (Part each : p) {
//            if (each.getId() > numberID) {
//                numberID = each.getId() + 1;
//            }
//        }
//        newPartID.setText(Integer.toString(numberID));
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

    /** This method changes the label on the form depending on the boolean inHouse.
     * @param actionEvent  the triggering user event*/
    @FXML
    public void partInHouseHandler(ActionEvent actionEvent) {
//        partsVariableLabel.setText("Machine ID");
//        inHouse = true;
    }

    /** This method changes the label on the form depending on the boolean inHouse.
     * @param actionEvent  the triggering user event*/
    @FXML
    public void partOutSourcedHandler(ActionEvent actionEvent) {
//        partsVariableLabel.setText("Company Name");
//        inHouse = false;
    }

    /**Once the saved button is clicked the form is checked for errors before the part is added
     * and the user is returned to the main screen.  First, the function calls on the minMaxCheck()
     * function to determine if given values are appropriate for the min, max and inventory amounts.
     * Then it checks on if the product has an int machine idea if it was built in house or a string
     * company name if it was built outsourced. If not, an alert is shown asking the user to check
     * their inputs.  Then an InHouse or Outsourced object is built and each proposed value is
     * parsed into the appropriate data type.  Should any parse not work,an error is thrown and
     * the user is prompted to check their inputs. If all checks are passed, the part is added
     * to the ObservableList of parts, the number is incremented for the next added part
     * to have a unique user id and then the user is sent back to the main menu.
     *<p><b>
     * FUTURE ENHANCEMENT This functionality could be improved in the future through setting the maximum
     * automatically to the inventory level if the inventory level is above the max and confirming
     * the value with the user.</b></p>
     * <p><b>
     * LOGICAL ERROR The min/max/mid check was outside the try catch and parse errors were not leading
     * toa anything.  I put it inside its own try catch</b></p>
     * @param actionEvent  the triggering user event
     * @throws IOException IOException*/
    @Deprecated
    public void newPartSaveHandler(ActionEvent actionEvent) throws IOException {
//        Part p;
//        try{
//        if(!minMaxCheck(Integer.parseInt(newPartInv.getText()), Integer.parseInt(newPartMin.getText()),
//                Integer.parseInt(newPartMax.getText()))) {
//                return;
//                }
//        }
//        catch(Exception e){
//                showAlert("Please make sure min, max and inventory fields are filled in correctly!");
//                return;
//        }
//            if (inHouse && isNumber(newPartVarField.getText()) || !inHouse && !isNumber(newPartVarField.getText())) {
//                if (inHouse) {
//                        int partId = 0;
//                        try{
//                        partId = Integer.parseInt(newPartID.getText());
//                        } catch (Exception e) {
//                            showAlert("Please check that ID is an integer.");
//                            return;
//                        }
//                        String partName = newPartName.getText();
//                        double partPrice = 0;
//                        try{
//                            partPrice = Double.parseDouble(newPartPrice.getText());
//                        } catch (Exception e) {
//                            showAlert("Please check that the Price is a number.");
//                            return;
//                        }
//                        int partInv = 0;
//                        try{
//                            partInv = Integer.parseInt(newPartInv.getText());
//                        } catch (Exception e) {
//                            showAlert("Please check that Inventory is an integer.");
//                            return;
//                        }
//                        int partMin = 0;
//                        try{
//                            partMin = Integer.parseInt(newPartMin.getText());
//                        } catch (Exception e) {
//                            showAlert("Please check that min is an integer.");
//                            return;
//                        }
//                        int partMax = 0;
//                        try{
//                            partMax = Integer.parseInt(newPartMax.getText());
//                        } catch (Exception e) {
//                            showAlert("Please check that max is an integer.");
//                            return;
//                        }
//                        int partVarField = 0;
//                        try{
//                            partVarField = Integer.parseInt(newPartVarField.getText());
//                        } catch (Exception e) {
//                            showAlert("Please check that machine ID is an integer.");
//                            return;
//                        }
//
//                        InHouse I = new InHouse(partId,partName,partPrice,partInv,
//                                partMin,partMax,partVarField);
//                        Inventory.addPart(I);
//                        numberID++;
//                        cancelHandler(actionEvent);
//
//                } else {
//                    try {
//                        int partId = 0;
//                        try{
//                            partId = Integer.parseInt(newPartID.getText());
//                        } catch (Exception e) {
//                            showAlert("Please check that ID is an integer.");
//                            return;
//                        }
//                        String partName = newPartName.getText();
//                        double partPrice = 0;
//                        try{
//                            partPrice = Double.parseDouble(newPartPrice.getText());
//                        } catch (Exception e) {
//                            showAlert("Please check that the Price is a number.");
//                            return;
//                        }
//                        int partInv = 0;
//                        try{
//                            partInv = Integer.parseInt(newPartInv.getText());
//                        } catch (Exception e) {
//                            showAlert("Please check that Inventory is an integer.");
//                            return;
//                        }
//                        int partMin = 0;
//                        try{
//                            partMin = Integer.parseInt(newPartMin.getText());
//                        } catch (Exception e) {
//                            showAlert("Please check that min is an integer.");
//                            return;
//                        }
//                        int partMax = 0;
//                        try{
//                            partMax = Integer.parseInt(newPartMax.getText());
//                        } catch (Exception e) {
//                            showAlert("Please check that max is an integer.");
//                            return;
//                        }
//                        String partVarField = newPartVarField.getText();
//
//                        Outsourced o = new Outsourced(partId,partName,partPrice,partInv,
//                                partMin,partMax,partVarField);
//                        Inventory.addPart(o);
//                        numberID++;
//                        cancelHandler(actionEvent);
//                    } catch (Exception e) {
//                        showAlert("Please make sure all the fields are filled in!");
//                    }
//                }
//        }else {
//            showAlert("Please verify if your part is outsourced or in-house");
//        }
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

    /** This method checks if a value is a number by trying to parse it into a double
     * If an exception is thrown then it is not a double or an int.
     * @param value The value to check*/
    private static boolean isNumber(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**This method shows an alert based on the passed string text.
     * @param alertText  The Text to fill out the alert*/
    public void showAlert(String alertText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(alertText);
        alert.showAndWait();
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
}
