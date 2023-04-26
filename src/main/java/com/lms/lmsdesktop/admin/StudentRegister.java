package com.lms.lmsdesktop.admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class StudentRegister extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file from the specified path
        URL fxmlUrl = getClass().getResource("student-register-view.fxml");
        if (fxmlUrl == null) {
            // Handle error gracefully
            throw new IOException("FXML file not found");
        }

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();

        // Set up the scene and show the stage
        Scene scene = new Scene(root);

//        // Add the CSS file to the scene
//        URL cssUrl = getClass().getResource("css/AdminLanding.css");
//        scene.getStylesheets().add(cssUrl.toExternalForm());

        primaryStage.setTitle("Student Registration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
