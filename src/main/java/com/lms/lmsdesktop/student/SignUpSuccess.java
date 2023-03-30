package com.lms.lmsdesktop.student;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SignUpSuccess extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file from the specified path
        URL fxmlUrl = getClass().getResource("sign-up-success.view.fxml");
        Parent root = FXMLLoader.load(fxmlUrl);

        // Set up the scene and show the stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Sign Up Successful!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
