package controller;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.awt.event.ActionEvent;

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

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void handleComboType(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void handleComboMonth(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void handleComboContact(ActionEvent actionEvent) {
    }
}
