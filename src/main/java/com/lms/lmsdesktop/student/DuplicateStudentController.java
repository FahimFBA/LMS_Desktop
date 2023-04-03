package com.lms.lmsdesktop.student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DuplicateStudentController {
    @FXML
    private Button tryAgain;

    @FXML
    void setTryAgain(ActionEvent event) throws IOException {
        // open the SignUp page
        SignUp signUp = new SignUp();
        signUp.start((Stage) tryAgain.getScene().getWindow());

        // close the SignUpFailed page
        if (tryAgain.getScene() != null && tryAgain.getScene().getWindow() != null) {
            tryAgain.getScene().getWindow().hide();
        }
    }

}
