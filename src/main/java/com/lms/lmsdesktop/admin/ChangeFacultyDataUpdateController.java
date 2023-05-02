package com.lms.lmsdesktop.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChangeFacultyDataUpdateController {

    @FXML
    private JFXButton ExitButton;

    @FXML
    private TextField code;

    @FXML
    private TextField email;

    @FXML
    private TextField name;
    @FXML
    private TextField course_name;

    @FXML
    private TextField password;

    @FXML
    private JFXButton saveToDB_Button;

    @FXML
    private TextField section;

    @FXML
    void SaveToDB(ActionEvent event) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/student_signup", "root", "root")) {
            // Update the faculty_table
            PreparedStatement facultyStmt = conn.prepareStatement("UPDATE faculty_table SET faculty_name = ?, faculty_email = ?, faculty_password = ? WHERE faculty_serial_id = ?");
            facultyStmt.setString(1, name.getText());
            facultyStmt.setString(2, email.getText());
            facultyStmt.setString(3, password.getText());
            facultyStmt.setString(4, selectedFaculty.getSerial());
            facultyStmt.executeUpdate();

            // Update the course table
            PreparedStatement courseStmt = conn.prepareStatement("UPDATE course SET course_code = ?, course_name = ?, course_section = ? WHERE course_section_faculty_ID = ?");
            courseStmt.setString(1, code.getText());
            courseStmt.setString(2, selectedFaculty.getCourseName()); // Assuming you want to keep the original course name
            courseStmt.setString(3, section.getText());
            courseStmt.setString(4, selectedFaculty.getSerial());
            courseStmt.executeUpdate();

            // Close the window after updating the data
            Stage stage = (Stage) saveToDB_Button.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Faculty selectedFaculty;

    public void initData(Faculty faculty) {
        this.selectedFaculty = faculty;

        // Set the current data in the text fields
        name.setText(faculty.getFacultyName()); // Update this line
        email.setText(faculty.getEmail());
        password.setText(faculty.getPassword());
        code.setText(faculty.getCourseCode());
        course_name.setText(faculty.getCourseName());
        section.setText(faculty.getCourseSection());
    }


}
