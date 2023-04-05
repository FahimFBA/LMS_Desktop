package com.lms.lmsdesktop.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class CourseRegController {

    @FXML
    private TextField course_creditTF;

    @FXML
    private TextField course_enrollment_codeTF;

    @FXML
    private TextField course_facultyTF;

    @FXML
    private TextField course_idTF;

    @FXML
    private TextField course_nameTF;

    @FXML
    private TextField course_sectionTF;

    @FXML
    private TextField course_total_studentsTF;

    @FXML
    private Button submitButton;

    @FXML
    void submitButton(ActionEvent event) {
        String course_id = course_idTF.getText();
        String course_name = course_nameTF.getText();
        String course_section = course_sectionTF.getText();
        int course_credit = Integer.parseInt(course_creditTF.getText());
        String course_faculty = course_facultyTF.getText();
        String course_enrollment_code = course_enrollment_codeTF.getText();
        String course_total_students = course_total_studentsTF.getText();

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_signup", "root", "root");

            // Create a PreparedStatement with the SQL INSERT statement
            String sql = "INSERT INTO course (course_id, course_name, course_section, course_credit, course_section_faculty, course_section_enrollment_code, course_section_total_students) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, course_id);
            statement.setString(2, course_name);
            statement.setString(3, course_section);
            statement.setInt(4, course_credit);
            statement.setString(5, course_faculty);
            statement.setString(6, course_enrollment_code);
            statement.setString(7, course_total_students);

            // Execute the PreparedStatement
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new course has been added!");
            }

            // Close the resources
            statement.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
