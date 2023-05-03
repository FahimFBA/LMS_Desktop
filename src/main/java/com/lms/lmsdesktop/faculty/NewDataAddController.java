package com.lms.lmsdesktop.faculty;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;

public class NewDataAddController {
    @FXML
    private Label course_name_label;

    @FXML
    private Label course_section_label;

    private String courseName;
    private String courseSection;



    @FXML
    private TextField course_content;

    @FXML
    private JFXButton Exit;

    @FXML
    private JFXButton Save;

    @FXML
    private DatePicker date;

    @FXML
    private JFXButton filePicker;

    @FXML
    private TextField notes;

    @FXML
    private JFXCheckBox notification_checkbox;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_signup";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    private FacultyClassroomController facultyClassroomController;

    public void setFacultyClassroomController(FacultyClassroomController facultyClassroomController) {
        this.facultyClassroomController = facultyClassroomController;
    }

    public void setCourseInfo(String courseName, String courseSection) {
        this.courseName = courseName;
        this.courseSection = courseSection;
        course_name_label.setText(courseName);
        course_section_label.setText(courseSection);
    }
    @FXML
    void saveData(ActionEvent event) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO lms_data (course_name, course_section, course_content_date, course_content_data, course_send_notification, course_notes) VALUES (?, ?, ?, ?, ?, ?)")) {

            String formattedDate = date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            preparedStatement.setString(1, courseName);
            preparedStatement.setString(2, courseSection);
            preparedStatement.setString(3, formattedDate);
            preparedStatement.setString(4, course_content.getText());
            preparedStatement.setBoolean(5, notification_checkbox.isSelected());
            preparedStatement.setString(6, notes.getText());

            preparedStatement.executeUpdate();

            // Update the table in the FacultyClassroomController
            if (facultyClassroomController != null) {
                facultyClassroomController.loadTableData();
            }

            // Close the window after saving the data
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ExitWindow(ActionEvent event) {

        if (facultyClassroomController != null) {
            facultyClassroomController.loadTableData();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }
}
