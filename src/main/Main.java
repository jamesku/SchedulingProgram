package main;

import database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

/**Main class.
 * JavaDocs in root.
 * @author James Kuhr*/
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Locale.setDefault(new Locale("fr"));
        Parent root = FXMLLoader.load(getClass().getResource("../view/FirstScreen.fxml"));
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }

    /**Main function
     * @param args args*/
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }

}
