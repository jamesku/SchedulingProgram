package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class FirstScreenController implements Initializable {
    @javafx.fxml.FXML
    private TextField loginUserName;
    @javafx.fxml.FXML
    private TextField loginPassword;
    @javafx.fxml.FXML
    private Label loginLocation;
    @javafx.fxml.FXML
    private Button loginSubmit;
    Stage stage;
    Parent scene;

    Boolean french = false;
    @javafx.fxml.FXML
    private Label loginTitle;
    @javafx.fxml.FXML
    private Label timezoneLabel;

    String alertText = "Username or Password not recognized, please try again.";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale currentLocale = Locale.getDefault();
        if(!currentLocale.getLanguage().equals("en")){
            ResourceBundle rb = ResourceBundle.getBundle("localization/lang", currentLocale);
            loginUserName.setText(rb.getString("username"));
            loginPassword.setText(rb.getString("password"));
            loginTitle.setText(rb.getString("login"));
            timezoneLabel.setText(rb.getString("timezone"));
            loginSubmit.setText(rb.getString("submit"));
            alertText = rb.getString("alertText");
        };
        ZonedDateTime timeZoneInfo = ZonedDateTime.now(ZoneId.systemDefault());
        loginLocation.setText(String.valueOf(timeZoneInfo.getZone()));
    }
    @javafx.fxml.FXML
    public void submitLogin(ActionEvent actionEvent) throws IOException {
        if(false){
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TopLevelMenu.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            showAlert();
        }
    }

    /**This method shows an alert based on the passed string text.
     **/
    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(alertText);
        alert.showAndWait();
    }

}
