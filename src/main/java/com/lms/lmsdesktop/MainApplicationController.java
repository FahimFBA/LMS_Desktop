package com.lms.lmsdesktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplicationController {

    @FXML
    private Button admin;

    @FXML
    private Button faculty;

    @FXML
    private Button student;


    // admin button clicks opens the admin-landing-view which location is "src/main/resources/com/lms/lmsdesktop/admin/admin-landing-view.fxml" and set a new scene
//    @FXML
//    void handleAdminButtonClick(ActionEvent event) {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("admin/admin-landing-view.fxml"));
//            admin.getScene().setRoot(root);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    void admin(ActionEvent event) {
        // also make sure to create a new stage
        Stage adminStage = new Stage();
        adminStage.setTitle("Admin Homepage");
        adminStage.setScene(new Scene(new VBox()));

        // load the admin view FXML file
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("admin/admin-landing-view.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // set the scene
        adminStage.setScene(new Scene(root));
        adminStage.show();

        // hide the current stage
        admin.getScene().getWindow().hide();


    }

    @FXML
    void faculty(ActionEvent event) {
        // also make sure to create a new stage
        Stage facultyStage = new Stage();
        facultyStage.setTitle("Faculty Homepage");
        facultyStage.setScene(new Scene(new VBox()));

        // load the admin view FXML file
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("faculty/faculty-landing-view.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // set the scene
        facultyStage.setScene(new Scene(root));
        facultyStage.show();

        // hide the current stage
        faculty.getScene().getWindow().hide();
    }

    @FXML
    void student(ActionEvent event) {
        Stage studentStage = new Stage();
        studentStage.setTitle("Student Homepage");
        studentStage.setScene(new Scene(new VBox()));

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("student/student-landing-view.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        studentStage.setScene(new Scene(root));
        studentStage.show();

        student.getScene().getWindow().hide();
    }

}
