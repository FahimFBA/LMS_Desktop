package com.lms.lmsdesktop.student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpSuccessController {

    @FXML
    private Button login;

    @FXML
    void loginActivity(MouseEvent event) throws IOException, IOException{
        // Open the StudentLanding page
        StudentLanding studentLanding = new StudentLanding();
        studentLanding.start((Stage) login.getScene().getWindow());

        // Close the SignUpSuccess page
        if (login.getScene() != null && login.getScene().getWindow() != null) {
            login.getScene().getWindow().hide();
        }
    }

}
