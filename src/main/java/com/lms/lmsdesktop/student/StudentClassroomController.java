package com.lms.lmsdesktop.student;

import com.jfoenix.controls.JFXButton;
import com.lms.lmsdesktop.faculty.CourseContent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.*;

public class StudentClassroomController {

    @FXML
    private JFXButton Exit;

    @FXML
    private TableView<CourseContent> contentTable;

    @FXML
    private ChoiceBox<String> courseNameDropMenu;

    @FXML
    private JFXButton loadButton;

    @FXML
    private ChoiceBox<String> sectionDropMenu;

    @FXML
    private TableColumn<CourseContent, String> table_content;

    @FXML
    private TableColumn<CourseContent, String> table_date;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_signup";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    @FXML
    public void initialize() {
        loadCourseNamesAndSections();
        configureTableColumns();
    }

    private void loadCourseNamesAndSections() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();

            ResultSet courseNameResultSet = statement.executeQuery("SELECT DISTINCT course_name FROM lms_data");
            while (courseNameResultSet.next()) {
                courseNameDropMenu.getItems().add(courseNameResultSet.getString("course_name"));
            }

            ResultSet courseSectionResultSet = statement.executeQuery("SELECT DISTINCT course_section FROM lms_data");
            while (courseSectionResultSet.next()) {
                sectionDropMenu.getItems().add(courseSectionResultSet.getString("course_section"));
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configureTableColumns() {
        table_date.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        table_content.setCellValueFactory(cellData -> cellData.getValue().getContentProperty());
    }

    private void loadTableData() {
        String selectedCourseName = courseNameDropMenu.getValue();
        String selectedCourseSection = sectionDropMenu.getValue();

        if (selectedCourseName == null || selectedCourseSection == null) {
            return;
        }

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM lms_data WHERE course_name = ? AND course_section = ?");
            preparedStatement.setString(1, selectedCourseName);
            preparedStatement.setString(2, selectedCourseSection);

            ObservableList<CourseContent> data = FXCollections.observableArrayList();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                data.add(new CourseContent(
                        resultSet.getString("course_content_date"),
                        resultSet.getString("course_content_data"),
                        resultSet.getBoolean("course_send_notification"),
                        resultSet.getString("course_notes")
                ));
            }

            contentTable.setItems(data);

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadButtonClicked(ActionEvent event) {
        loadTableData();
    }

    @FXML
    void ExitWindow(ActionEvent event) {
        Stage stage = (Stage) Exit.getScene().getWindow();
        stage.close();
    }
}

