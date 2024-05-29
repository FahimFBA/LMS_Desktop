package com.lms.lmsdesktop.admin;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class ChangeFacultyDataNewController {
    @FXML
    private JFXButton ExitButton;

    @FXML
    private TextField code;

    @FXML
    private TextField course_name;

    @FXML
    private TextField email;

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private JFXButton saveToDB_Button;

    @FXML
    private TextField section;

    @FXML
    void SaveToDB(ActionEvent event) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish the database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost/student_signup", "root", "root");

            // Prepare the SQL query for faculty_table
            String query = "INSERT INTO faculty_table (faculty_name, faculty_email, faculty_password) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            // Set the data from the text fields
            preparedStatement.setString(1, name.getText());
            preparedStatement.setString(2, email.getText());
            preparedStatement.setString(3, password.getText());

            // Execute the query
            preparedStatement.executeUpdate();

            // Get the last inserted faculty ID
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int facultyId = 0;
            if (generatedKeys.next()) {
                facultyId = generatedKeys.getInt(1);
            }

            // Prepare the SQL query for the course table
            String courseQuery = "INSERT INTO course (course_code, course_name, course_section, course_section_faculty_ID) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(courseQuery);

            preparedStatement.setString(1, code.getText());
            preparedStatement.setString(2, course_name.getText());
            preparedStatement.setString(3, section.getText());
            preparedStatement.setInt(4, facultyId);

            // Execute the query
            preparedStatement.executeUpdate();

            // Close the window
            ((Stage) saveToDB_Button.getScene().getWindow()).close();

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // 1062 is the error code for Duplicate entry
                // Show a pop-up warning
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Duplicate Entry");
                alert.setHeaderText(null);
                alert.setContentText("The entry already exists. Please check the primary key.");
                alert.showAndWait();
            } else {
                System.out.println("Error: " + e);
            }
        } finally {
            // Close the resources
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("Error: " + e);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error: " + e);
                }
            }
        }
    }



}
