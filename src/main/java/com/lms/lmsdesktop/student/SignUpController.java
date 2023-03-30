package com.lms.lmsdesktop.student;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private Button SignMeUp;
    @FXML
    private TextField email;

    @FXML
    private TextField name;

    @FXML
    private TextField student_id;
    @FXML
    private PasswordField password;

    @FXML
    private PasswordField retype_password;


    @FXML
    void handleSignUp(ActionEvent event) {
        String student_email = email.getText();
        String student_name = name.getText();
        String student_id_string = student_id.getText();
        int student_id = Integer.parseInt(student_id_string);
        String student_password = password.getText();
        String student_retype_password = retype_password.getText();
        String sqlFilePath = "/database/student_signup.sql";

        // Check if the passwords match
        if (!student_password.equals(student_retype_password)) {
            try {
                // Load the SignUpFailed page
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signed-up-failed-view.fxml"));
                Parent signUpFailedRoot = fxmlLoader.load();

                // Create a new scene with the loaded SignUpFailed page
                Scene scene = new Scene(signUpFailedRoot);

                // Get the current stage from the source of the action event
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the scene on the current stage and show it
                currentStage.setScene(scene);
                currentStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                InputStream inputStream = getClass().getResourceAsStream(sqlFilePath);
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_signup", "root", "root");
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO signupinfo (student_email, student_name, student_id, student_password) VALUES (?, ?, ?, ?)");
                stmt.setString(1, student_email);
                stmt.setString(2, student_name);
                stmt.setInt(3, student_id);
                stmt.setString(4, student_password);
                stmt.executeUpdate();
                stmt.close();
                conn.close();

                // open the SingUpSuccess
                try {
                    // Load the SignUpSuccess page
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sign-up-success.view.fxml"));
                    Parent signUpSuccessRoot = fxmlLoader.load();

                    // Create a new scene with the loaded SignUpSuccess page
                    Scene scene = new Scene(signUpSuccessRoot);

                    // Get the current stage from the source of the action event
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    // Set the scene on the current stage and show it
                    currentStage.setScene(scene);
                    currentStage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
