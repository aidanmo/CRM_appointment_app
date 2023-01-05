package Main;

import Utils.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {



    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/LoginScreen.fxml"));
        stage.setTitle("Appointment Manager");
        stage.setScene(new Scene(root, 500, 300));
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }



}
