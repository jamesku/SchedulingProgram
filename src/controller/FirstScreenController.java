package Controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Deprecated
    public void submitLogin(ActionEvent actionEvent) throws IOException {
        if(true){
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TopLevelMenu.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
