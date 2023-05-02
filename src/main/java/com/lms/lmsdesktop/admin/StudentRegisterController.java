package com.lms.lmsdesktop.admin;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class StudentRegisterController {

    @FXML
    private JFXButton approvedStudentButton;
    @FXML
    private JFXButton RegisterNewStudentButton;


    public void openApprovedStudentWindow(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lms/lmsdesktop/admin/approved-students-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            // Show the window and do not block the code
            stage.show();

            // Hide current window
            Scene scene = ((Node) event.getSource()).getScene();
            if (scene != null) {
                Window window = scene.getWindow();
                if (window != null) {
                    window.hide();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    public void openStudentRegister(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lms/lmsdesktop/admin/register-students-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            // Show the window and do not block the code
            stage.show();

            // Hide current window
            Scene scene = ((Node) event.getSource()).getScene();
            if (scene != null) {
                Window window = scene.getWindow();
                if (window != null) {
                    window.hide();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

