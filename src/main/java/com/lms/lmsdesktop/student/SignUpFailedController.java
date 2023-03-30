package com.lms.lmsdesktop.student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpFailedController {
    @FXML
    private Button tryAgainButton;
    @FXML
    void tryAgainButtonActivity(ActionEvent event) throws IOException {
        // open the SignUp page
        SignUp signUp = new SignUp();
        signUp.start((Stage) tryAgainButton.getScene().getWindow());

        // close the SignUpFailed page
        if (tryAgainButton.getScene() != null && tryAgainButton.getScene().getWindow() != null) {
            tryAgainButton.getScene().getWindow().hide();
        }
    }

}
