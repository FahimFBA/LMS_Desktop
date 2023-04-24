package com.lms.lmsdesktop.faculty;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.sql.*;

public class FacultyClassroomController {

    @FXML
    private ChoiceBox<String> courseNameDropMenu;

    @FXML
    private JFXButton saveButton;

    @FXML
    private ChoiceBox<String> sectionDropMenu;

    @FXML
    private JFXButton loadButton;

    @FXML
    private TableView<CourseContent> contentTable;

    @FXML
    private TableColumn<CourseContent, Integer> table_serial;

    @FXML
    private TableColumn<CourseContent, String> table_date;

    @FXML
    private TableColumn<CourseContent, String> table_new_or_update;

    @FXML
    private TableColumn<CourseContent, String> table_content;

    @FXML
    private TableColumn<CourseContent, Boolean> table_notification;

    @FXML
    private TableColumn<CourseContent, String> table_notes;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_signup";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    public void initialize() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            ResultSet resultSet;

            // Load course_name values
            resultSet = statement.executeQuery("SELECT course_name FROM lms_data");
            while (resultSet.next()) {
                courseNameDropMenu.getItems().add(resultSet.getString("course_name"));
            }

            // Load course_section values
            resultSet = statement.executeQuery("SELECT course_section FROM lms_data");
            while (resultSet.next()) {
                sectionDropMenu.getItems().add(resultSet.getString("course_section"));
            }

            // Set up table columns
            table_serial.setCellValueFactory(new PropertyValueFactory<>("course_contentSerial"));
            table_date.setCellValueFactory(new PropertyValueFactory<>("course_content_date"));
            table_new_or_update.setCellValueFactory(new PropertyValueFactory<>("course_content_newOrUpdate"));
            table_content.setCellValueFactory(new PropertyValueFactory<>("course_content_data"));
            table_notification.setCellValueFactory(new PropertyValueFactory<>("course_send_notification"));
            table_notes.setCellValueFactory(new PropertyValueFactory<>("course_notes"));

            // Add cell factories and commit edit handlers for editable TableColumns
            table_date.setCellFactory(TextFieldTableCell.forTableColumn());
            table_date.setOnEditCommit(e -> e.getRowValue().setCourse_content_date(e.getNewValue()));

            table_new_or_update.setCellFactory(TextFieldTableCell.forTableColumn());
            table_new_or_update.setOnEditCommit(e -> e.getRowValue().setCourse_content_newOrUpdate(e.getNewValue()));

            table_content.setCellFactory(TextFieldTableCell.forTableColumn());
            table_content.setOnEditCommit(e -> e.getRowValue().setCourse_content_data(e.getNewValue()));

            table_notes.setCellFactory(TextFieldTableCell.forTableColumn());
            table_notes.setOnEditCommit(e -> e.getRowValue().setCourse_notes(e.getNewValue()));

            // Allow editing in TableView
            contentTable.setEditable(true);

            loadButton.setOnAction(e -> loadTableData());
            saveButton.setOnAction(e -> saveTableData()); // Bind the saveButton action

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        contentTable.refresh();
    }
    private void saveTableData() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement preparedStatement;

            for (CourseContent courseContent : contentTable.getItems()) {
                String query = "UPDATE lms_data SET course_content_date = ?, course_content_newOrUpdate = ?, course_content_data = ?, course_send_notification = ?, course_notes = ? WHERE course_contentSerial = ?";
                preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, courseContent.getCourse_content_date());
                preparedStatement.setString(2, courseContent.getCourse_content_newOrUpdate());
                preparedStatement.setString(3, courseContent.getCourse_content_data());
                preparedStatement.setBoolean(4, courseContent.getCourse_send_notification());
                preparedStatement.setString(5, courseContent.getCourse_notes());
                preparedStatement.setInt(6, courseContent.getCourse_contentSerial());

                int rowsUpdated = preparedStatement.executeUpdate();
                System.out.println("Updating row with course_contentSerial: " + courseContent.getCourse_contentSerial());
                System.out.println("Rows updated: " + rowsUpdated);

                // Call updateRowInDatabase() to get the updated data and check if it has been updated correctly
                updateRowInDatabase(courseContent);
            }

            connection.close();
            refreshTable(); // Refresh the TableView to display the updated data
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private void updateRowInDatabase(CourseContent courseContent) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            connection.setAutoCommit(false);
            String query = "UPDATE lms_data SET course_content_date = ?, course_content_newOrUpdate = ?, course_content_data = ?, course_send_notification = ?, course_notes = ? WHERE course_contentSerial = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, courseContent.getCourse_content_date());
            preparedStatement.setString(2, courseContent.getCourse_content_newOrUpdate());
            preparedStatement.setString(3, courseContent.getCourse_content_data());
            preparedStatement.setBoolean(4, courseContent.getCourse_send_notification());
            preparedStatement.setString(5, courseContent.getCourse_notes());
            preparedStatement.setInt(6, courseContent.getCourse_contentSerial());

            System.out.println("Executing query: " + preparedStatement.toString());

            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);

            connection.commit();

            // Add a SELECT query to fetch the updated data
            String selectQuery = "SELECT * FROM lms_data WHERE course_contentSerial = ?";
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);
            selectPreparedStatement.setInt(1, courseContent.getCourse_contentSerial());
            ResultSet resultSet = selectPreparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Updated data:");
                System.out.println("course_content_date: " + resultSet.getString("course_content_date"));
                System.out.println("course_content_newOrUpdate: " + resultSet.getString("course_content_newOrUpdate"));
                System.out.println("course_content_data: " + resultSet.getString("course_content_data"));
                System.out.println("course_send_notification: " + resultSet.getBoolean("course_send_notification"));
                System.out.println("course_notes: " + resultSet.getString("course_notes"));
            }

            connection.setAutoCommit(true);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTableData() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();

            ObservableList<CourseContent> data = FXCollections.observableArrayList();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM lms_data");

            while (resultSet.next()) {
                data.add(new CourseContent(
                        resultSet.getInt("course_contentSerial"),
                        resultSet.getString("course_content_date"),
                        resultSet.getString("course_content_newOrUpdate"),
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
}
