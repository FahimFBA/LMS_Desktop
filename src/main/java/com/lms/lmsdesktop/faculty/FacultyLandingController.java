package com.lms.lmsdesktop.faculty;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.sql.*;

public class FacultyLandingController {

    @FXML
    private JFXButton Home;

    @FXML
    private TextField Email;

    @FXML
    private PasswordField password;

    @FXML
    private JFXButton submitButton;

    @FXML
    void handleSubmitButtonAction(MouseEvent event) {
        String dbURL = "jdbc:mysql://localhost:3306/student_signup";
        String username = "root";
        String mysqlPassword = "root"; // Replace with your own MySQL password

        String mailEntered = Email.getText();
        String passwordEntered = password.getText();

        try (Connection conn = DriverManager.getConnection(dbURL, username, mysqlPassword)) {

            String sql = "SELECT * FROM faculty_table WHERE faculty_email = ? AND faculty_password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, mailEntered);
            statement.setString(2, passwordEntered);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                // Faculty name and password matched in the database
                Parent facultyLMSViewParent = FXMLLoader.load(getClass().getResource("faculty-lms-view.fxml"));
                Scene facultyLMSViewScene = new Scene(facultyLMSViewParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(facultyLMSViewScene);
                window.show();
            } else {
                // Faculty name and/or password did not match in the database
                Text warningText = new Text("Invalid name or password");
                // Add warningText to a popup dialog and display it
                // Example: Alert alert = new Alert(AlertType.WARNING, "Invalid name or password");
                // alert.showAndWait();
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    @FXML
    void handleHomeButtonAction(ActionEvent event) {
        try {
            // Load the FXML file for the home view
            Parent homeViewParent = FXMLLoader.load(getClass().getResource("home-view.fxml"));

            // Create a new Scene with the loaded FXML file
            Scene homeViewScene = new Scene(homeViewParent);

            // Get the current stage (window)
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new Scene to the stage and show it
            window.setScene(homeViewScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Submit(MouseEvent event) {
        String dbURL = "jdbc:mysql://localhost:3306/student_signup";
        String username = "root";
        String mysqlPassword = "root"; // Replace with your own MySQL password

        String mailEntered = Email.getText();
        String passwordEntered = password.getText();


        try (Connection conn = DriverManager.getConnection(dbURL, username, mysqlPassword)) {

            String sql = "SELECT * FROM faculty_table WHERE faculty_email = ? AND faculty_password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, mailEntered);
            statement.setString(2, passwordEntered);


            ResultSet result = statement.executeQuery();

            if (result.next()) {
                // Faculty name and password matched in the database
                Parent facultyLMSViewParent = FXMLLoader.load(getClass().getResource("faculty-lms-view.fxml"));
                Scene facultyLMSViewScene = new Scene(facultyLMSViewParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(facultyLMSViewScene);
                window.show();
            } else {
                // Faculty name and/or password did not match in the database
                Alert alert = new Alert(AlertType.WARNING, "Invalid email or password");
                alert.showAndWait();
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

}

