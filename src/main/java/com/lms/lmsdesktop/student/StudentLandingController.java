package com.lms.lmsdesktop.student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StudentLandingController {

//    @FXML
//    private Button mainPage;
//
//
//    // getting error here
//    @FXML
//    void mainPage(ActionEvent event) {
//        Stage LMS = new Stage();
//        LMS.setTitle("LMS Desktop");
//        LMS.setScene(new Scene(new VBox()));
//        Parent root;
//        try {
//            root = FXMLLoader.load(getClass().getResource("main-application-view.fxml"));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//        LMS.setScene(new Scene(root));
//        LMS.show();
//        mainPage.getScene().getWindow().hide();
//    }


    @FXML
    private TextField id;

    @FXML
    private PasswordField pass;

    @FXML
    private Button submit;


    @FXML
    private Button home;

    public void home(ActionEvent event) throws IOException {

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

    @FXML
    void login(ActionEvent event) throws IOException {
        String userId = id.getText();
        String password = pass.getText();

        if (userId.equals("student") && password.equals("student")) {
            Parent root = FXMLLoader.load(getClass().getResource("/com/lms/lmsdesktop/student/student-classroom-view.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Student Classroom");
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Invalid username or password");
            alert.setContentText("Please enter valid credentials");
            alert.showAndWait();
        }
    }

}
