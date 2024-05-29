package com.lms.lmsdesktop.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class ChangeStudentsData_RegisterController {
    @FXML
    private JFXButton ExitButton;



    @FXML
    private JFXButton saveToDB_Button;

    @FXML
    private TextField student_Email;

    @FXML
    private TextField student_ID;

    @FXML
    private TextField student_Name;

    @FXML
    private TextField student_Password;

    @FXML
    private JFXCheckBox student_Status;

    private Student student2;
    private ObservableList<Student> studentData;

    public void initData(Student student, ObservableList<Student> studentData) {
        this.student2 = student;
        this.studentData = studentData;

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

        // Check if the email or ID already exists in the database
        boolean isDuplicate = false;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/student_signup", "root", "root")) {
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM student_signup_table WHERE student_email=? OR student_id=?");
            stmt.setString(1, email);
            stmt.setString(2, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                isDuplicate = true;
            }

            if (isDuplicate) {
                // Show a warning popup if the email or ID already exists in the database
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Duplicate Record");
                alert.setHeaderText("Cannot add new student");
                alert.setContentText("A record with this email or ID already exists in the database. Please enter a unique email or ID.");
                alert.showAndWait();
            } else {
                // Insert the new student into the database
                stmt = conn.prepareStatement("INSERT INTO student_signup_table (student_id, student_name, student_email, student_password, student_status) VALUES (?, ?, ?, ?, ?)");
                stmt.setString(1, id);
                stmt.setString(2, name);
                stmt.setString(3, email);
                stmt.setString(4, password);
                stmt.setString(5, status);
                stmt.executeUpdate();

                // Add the new student to the studentData list only after it has been successfully inserted into the database
                Student newStudent = new Student(email, name, id, password, status);
                studentData.add(newStudent);

//                // Close the window
//                Stage stage = (Stage) saveToDB_Button.getScene().getWindow();
//                stage.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exitWindow() {
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }


}
