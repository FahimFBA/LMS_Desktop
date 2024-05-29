package com.lms.lmsdesktop.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

public class AdminLandingController implements Initializable {
    @FXML
    private JFXButton Home;

    @FXML
    private JFXButton adminSubmit;
    @FXML
    private PasswordField admin_pass;

    @FXML
    private TextField admin_user_id;

    @FXML
    void handleHomeButton(ActionEvent event) throws IOException {
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
    void adminSubmit(ActionEvent event) {
        String Admin_ID = admin_user_id.getText();
        String Admin_Pass = admin_pass.getText();
        if (Admin_ID.equals("admin") && Admin_Pass.equals("admin")) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lms/lmsdesktop/admin/admin-panel-view.fxml"));
                Parent googleMeetView = fxmlLoader.load();
                Scene scene = new Scene(googleMeetView);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Admin Panel");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                // hide the current window.
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Login Failed");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
