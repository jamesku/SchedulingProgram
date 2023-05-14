package Controller;

import Model.Appointment;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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

/**This controls the Modify part menu.*/
public class ModifyAppointmentFormController
{
    Stage stage;
    Parent scene;

    @javafx.fxml.FXML
    private AnchorPane ap;
    @javafx.fxml.FXML
    private RadioButton addPartInHouse;
    @javafx.fxml.FXML
    private RadioButton addPartOutsourced;
    @javafx.fxml.FXML
    private Label partsVariableLabel;
    @javafx.fxml.FXML
    private ToggleGroup modifyPartToggles;
    @javafx.fxml.FXML
    private TextField modifyPartID;
    @javafx.fxml.FXML
    private TextField modifyPartName;
    @javafx.fxml.FXML
    private TextField modifyPartInv;
    @javafx.fxml.FXML
    private TextField modifyPartCost;
    @javafx.fxml.FXML
    private TextField modifyPartMax;
    @javafx.fxml.FXML
    private TextField modifyPartVar;
    @javafx.fxml.FXML
    private TextField modifyPartMin;

    /**This tracks the state of the selected part type. */
    private Boolean inHouse = true;
    /**This is a holder for the Part object to be modified. */
    private static Appointment passedAppointment = null;
    /**This is used to track where to replace any updated part.*/
    int index;

    /** This is the initialize method for this controller.  This function automatically runs
     *  when the screen is loaded.  It is used here set up some information about the Part that
     *  needs to be modified. Additionally, Platform.runlater is called. This method is built in
     *  to Run a specified runnable at some unspecified time in the future. This provides time
     *  for the screen to load before a listener is set on a windows event - in this case,
     *  clicking on the x in the top right corner.
     * */
    @javafx.fxml.FXML
    public void initialize() {
//        modifyPartID.setText(passedPart.getId()+"");
//        modifyPartName.setText(passedPart.getName());
//        modifyPartInv.setText(passedPart.getStock()+"");
//        modifyPartCost.setText(passedPart.getPrice()+"");
//        modifyPartMax.setText(passedPart.getMax()+"");
//        modifyPartMin.setText(passedPart.getMin()+"");
//
//        if (passedPart instanceof Outsourced){
//            modifyPartVar.setText(((Outsourced) passedPart).getCompanyName());
//            addPartOutsourced.fire();
//            inHouse = false;
//        }
//        if (passedPart instanceof InHouse){
//            modifyPartVar.setText(((InHouse) passedPart).getMachineId()+"");
//            addPartInHouse.fire();
//            inHouse = true;
//        }
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
    }

    /**This changes a label based on if the part is inhouse or outsourced.
     * @param actionEvent button click */
    @javafx.fxml.FXML
    public void partInHouseHandler(ActionEvent actionEvent) {
//        inHouse = true;
//        partsVariableLabel.setText("Machine ID");
    }

    /**This changes a label based on if the part is inhouse or outsourced.
     * @param actionEvent button click */
    @javafx.fxml.FXML
    public void partOutSourcedHandler(ActionEvent actionEvent) {
//        inHouse = false;
//        partsVariableLabel.setText("Company Name");
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
    public void modifyPartSaveHandler(ActionEvent actionEvent) throws IOException {
//        ObservableList<Part> allParts = Inventory.getAllParts();
//        index = allParts.indexOf(passedPart);
//        try{
//        if(!minMaxCheck(Integer.parseInt(modifyPartInv.getText()), Integer.parseInt(modifyPartMin.getText()),
//                Integer.parseInt(modifyPartMax.getText()))) {
//            return;
//        }} catch (Exception e) {
//            showAlert("Please make sure all the fields are filled in correctly!");
//            return;
//        }
//        if (inHouse && isNumber(modifyPartVar.getText()) || !inHouse && !isNumber(modifyPartVar.getText())) {
//            System.out.println("here");
//            if (inHouse) {
//                int partId = 0;
//                try{
//                    partId = Integer.parseInt(modifyPartID.getText());
//                } catch (Exception e) {
//                    showAlert("Please check that ID is an integer.");
//                    return;
//                }
//                String partName = modifyPartName.getText();
//                double partPrice = 0;
//                try{
//                    partPrice = Double.parseDouble(modifyPartCost.getText());
//                } catch (Exception e) {
//                    showAlert("Please check that the Price is a number.");
//                    return;
//                }
//                int partInv = 0;
//                try{
//                    partInv = Integer.parseInt(modifyPartInv.getText());
//                } catch (Exception e) {
//                    showAlert("Please check that Inventory is an integer.");
//                    return;
//                }
//                int partMin = 0;
//                try{
//                    partMin = Integer.parseInt(modifyPartMin.getText());
//                } catch (Exception e) {
//                    showAlert("Please check that min is an integer.");
//                    return;
//                }
//                int partMax = 0;
//                try{
//                    partMax = Integer.parseInt(modifyPartMax.getText());
//                } catch (Exception e) {
//                    showAlert("Please check that max is an integer.");
//                    return;
//                }
//                int partVarField = 0;
//                try{
//                    partVarField = Integer.parseInt(modifyPartVar.getText());
//                } catch (Exception e) {
//                    showAlert("Please check that machine ID is an integer.");
//                    return;
//                }
//
//                InHouse I = new InHouse(partId,partName,partPrice,partInv,
//                        partMin,partMax,partVarField);
//                Inventory.updatePart(index, I);
//                cancelHandler(actionEvent);
//
//
//            } else {
//                try {
//                    int partId = 0;
//                    try{
//                        partId = Integer.parseInt(modifyPartID.getText());
//                    } catch (Exception e) {
//                        showAlert("Please check that ID is an integer.");
//                        return;
//                    }
//                    String partName = modifyPartName.getText();
//                    double partPrice = 0;
//                    try{
//                        partPrice = Double.parseDouble(modifyPartCost.getText());
//                    } catch (Exception e) {
//                        showAlert("Please check that the Price is a number.");
//                        return;
//                    }
//                    int partInv = 0;
//                    try{
//                        partInv = Integer.parseInt(modifyPartInv.getText());
//                    } catch (Exception e) {
//                        showAlert("Please check that Inventory is an integer.");
//                        return;
//                    }
//                    int partMin = 0;
//                    try{
//                        partMin = Integer.parseInt(modifyPartMin.getText());
//                    } catch (Exception e) {
//                        showAlert("Please check that min is an integer.");
//                        return;
//                    }
//                    int partMax = 0;
//                    try{
//                        partMax = Integer.parseInt(modifyPartMax.getText());
//                    } catch (Exception e) {
//                        showAlert("Please check that max is an integer.");
//                        return;
//                    }
//                    String partVarField = modifyPartVar.getText();
//
//                    Outsourced o = new Outsourced(partId,partName,partPrice,partInv,
//                            partMin,partMax,partVarField);
//                    Inventory.updatePart(index, o);
//                    cancelHandler(actionEvent);
//                } catch(Exception e){
//                    showAlert("Please make sure all the fields are filled in!");
//                }
//            }
//        } else {
//            showAlert("Please verify if your part is outsourced or in-house.");
//        }
    }


    /** This method sends the user back to the main menu.
     * @param actionEvent button click
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
     * @param alertText the text to use in the alert*/
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
//
//        showAlert("Please check the Min, Max and available stock values");
        return false;
    }

}