package com.lms.lmsdesktop.admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeStudentsData_ApprovedController {

    @FXML
    private TextField student_ID;

    @FXML
    private TextField student_Name;

    @FXML
    private TextField student_Email;

    @FXML
    private TextField student_Password;

    @FXML
    private JFXCheckBox student_Status;

    @FXML
    private JFXButton saveToDB_Button;

    private Student student;

    public void initData(Student student) {
        this.student = student;

        // Populate the fields with the student data
        student_ID.setText(student.getId());
        student_Name.setText(student.getName());
        student_Email.setText(student.getEmail());
        student_Password.setText(student.getPassword());
        student_Status.setSelected(student.getStatus().equals("0"));
    }

    @FXML
    public void SaveToDB() {
        // Get the updated values from the fields
        String id = student_ID.getText();
        String name = student_Name.getText();
        String email = student_Email.getText();
        String password = student_Password.getText();
        String status = student_Status.isSelected() ? "0" : "1";

        // Update the database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/student_signup", "root", "root")) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE student_signup_table SET student_name=?, student_email=?, student_password=?, student_status=? WHERE student_id=?");
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, status);
            stmt.setString(5, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Update the student object in the approvedStudentTable
        student.setName(name);
        student.setEmail(email);
        student.setPassword(password);
        student.setStatus(status);

        // Close the window
        Stage stage = (Stage) saveToDB_Button.getScene().getWindow();
        stage.close();
    }
}
