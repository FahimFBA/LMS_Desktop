package com.lms.lmsdesktop.faculty;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class FacultyLandingController {
    @FXML
    private ImageView image;

    @FXML
    private Text invalid;


    @FXML
    void Submit(MouseEvent event) {

    }

    @FXML
    void backFac(MouseEvent event) {

    }

    @FXML
    private Button home;


    @FXML
    void Home(ActionEvent event) throws IOException {
        // Create a new FXMLLoader object by passing the path of the FXML file as a parameter to the constructor
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lms/lmsdesktop/main-application-view.fxml"));

        // Use the load() method of the FXMLLoader object to load the FXML file and create the corresponding UI component
        Parent root = loader.load();

        // Create a new Scene object with the loaded UI component
        Scene scene = new Scene(root);

        // Get the current stage (window)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene to the stage and show it
        stage.setScene(scene);
        stage.show();
    }


}
