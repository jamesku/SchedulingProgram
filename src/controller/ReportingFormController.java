package controller;

import database.DatabaseIO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import model.StData;

import java.io.IOException;

public class ReportingFormController
{
    @javafx.fxml.FXML
    private AnchorPane mainAP;
    @javafx.fxml.FXML
    private ComboBox comboType;
    @javafx.fxml.FXML
    private ComboBox comboMonth;
    @javafx.fxml.FXML
    private TextField typeMonthTotal;
    @javafx.fxml.FXML
    private ComboBox comboCountry;
    @javafx.fxml.FXML
    private ComboBox comboDivision;
    @javafx.fxml.FXML
    private TextField countryDivisionTotal;
    @javafx.fxml.FXML
    private TableView appointmentsTable;
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
    private ComboBox comboContact;


    Stage stage;
    Parent scene;

    ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December");

    @javafx.fxml.FXML
    public void initialize() {
        comboType.setItems(StData.getAvailableTypes());
        comboMonth.setItems(months);
        comboCountry.setItems(DatabaseIO.getCountryCombo());
        comboContact.setItems(DatabaseIO.getContactCombo());

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
    }

    @javafx.fxml.FXML
    public void handleComboType(ActionEvent actionEvent) {
        if(comboType.getValue() !=null && comboMonth !=null){
            String month = comboMonth.getValue().toString();
            String type = comboType.getValue().toString();
            typeMonthTotal.setText(DatabaseIO.countTypeMonth(month, type)+"");
        }
    }

    @javafx.fxml.FXML
    public void handleComboMonth(ActionEvent actionEvent) {
        if(comboType.getValue() !=null && comboMonth !=null){
            String month = comboMonth.getValue().toString();
            String type = comboType.getValue().toString();
            typeMonthTotal.setText(DatabaseIO.countTypeMonth(month, type)+"");
        }
    }

    @javafx.fxml.FXML
    public void handleComboContact(ActionEvent actionEvent) {
        appointmentsTable.setItems(DatabaseIO.getSelectContactAppointments(comboContact.getValue().toString()));
    }


    /** Handles a selection of a country in a combobox.  Using the selection it calls to the
     * database to set up the first level division combobox.
     * @param actionEvent actionEvent*/
    @javafx.fxml.FXML
    public void handleComboCountry(ActionEvent actionEvent) {
        comboDivision.setItems(DatabaseIO.getDivisionCombo((String)comboCountry.getValue()));
    }

    /** Handles a selection of a first level division in a combobox.
     * @param actionEvent actionEvent*/
    @javafx.fxml.FXML
    public void handleComboDivision(ActionEvent actionEvent) {
        String division = comboDivision.getValue().toString();
        countryDivisionTotal.setText(DatabaseIO.countCountryDivision(division)+"");

    }
    /** This method sends the user back to the main menu.
     * @param actionEvent button click
     * @throws IOException IOexception*/
    @Deprecated
    public void exitHandler(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/TopLevelMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }
}
