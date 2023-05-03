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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NewDataEditController {
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
    private CourseContent existingContent;

    public void setExistingContent(CourseContent existingContent) {
        this.existingContent = existingContent;
        date.setValue(LocalDate.parse(existingContent.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        course_content.setText(existingContent.getContent());
        notification_checkbox.setSelected(existingContent.isNotification());
        notes.setText(existingContent.getNotes());
    }

    public void setCourseInfo(String courseName, String courseSection) {
        this.courseName = courseName;
        this.courseSection = courseSection;
        course_name_label.setText(courseName);
        course_section_label.setText(courseSection);
    }
    @FXML
    void saveData(ActionEvent event) {
        if (existingContent != null) {
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "UPDATE lms_data SET course_content_date = ?, course_content_data = ?, course_send_notification = ?, course_notes = ? WHERE course_name = ? AND course_section = ? AND course_content_date = ? AND course_content_data = ?")) {

                String formattedDate = date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                preparedStatement.setString(1, formattedDate);
                preparedStatement.setString(2, course_content.getText());
                preparedStatement.setBoolean(3, notification_checkbox.isSelected());
                preparedStatement.setString(4, notes.getText());
                preparedStatement.setString(5, courseName);
                preparedStatement.setString(6, courseSection);
                preparedStatement.setString(7, existingContent.getDate());
                preparedStatement.setString(8, existingContent.getContent());

                preparedStatement.executeUpdate();

                // Update the table in the FacultyClassroomController
                if (facultyClassroomController != null) {
                    facultyClassroomController.loadTableData();
                }

                // Close the window after updating the data
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
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
