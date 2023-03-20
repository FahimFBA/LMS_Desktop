package com.lms.lmsdesktop.student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentLandingController {

    @FXML
    private Button mainPage;


    // getting error here
    @FXML
    void mainPage(ActionEvent event) {
        Stage LMS = new Stage();
        LMS.setTitle("LMS Desktop");
        LMS.setScene(new Scene(new VBox()));
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("main-application-view.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        LMS.setScene(new Scene(root));
        LMS.show();
        mainPage.getScene().getWindow().hide();
    }
}
