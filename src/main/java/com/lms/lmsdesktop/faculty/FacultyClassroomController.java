package com.lms.lmsdesktop.faculty;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

public class FacultyClassroomController {

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private ChoiceBox<String> courseNameDropMenu;


    @FXML
    private JFXButton backToDashboard;

    @FXML
    private JFXButton Exit;

    @FXML
    private ChoiceBox<String> sectionDropMenu;

    @FXML
    private JFXButton loadButton;

    @FXML
    private TableView<CourseContent> contentTable;


    @FXML
    private TableColumn<CourseContent, String> table_date;



    @FXML
    private TableColumn<CourseContent, String> table_content;

    @FXML
    private TableColumn<CourseContent, Boolean> table_notification;

    @FXML
    private TableColumn<CourseContent, String> table_notes;

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
        table_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        table_content.setCellValueFactory(new PropertyValueFactory<>("content"));
        table_notification.setCellValueFactory(new PropertyValueFactory<>("notification"));
        table_notes.setCellValueFactory(new PropertyValueFactory<>("notes"));

        table_notification.setCellFactory(CheckBoxTableCell.forTableColumn(table_notification));
        table_notes.setCellFactory(TextFieldTableCell.forTableColumn());
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
    void addNewContent(ActionEvent event) {
        // open new-data-add.fxml
        try {
            openNewDataAddWindow(courseNameDropMenu.getValue(), sectionDropMenu.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void deleteSelectedContent(ActionEvent event) {
        CourseContent selectedContent = contentTable.getSelectionModel().getSelectedItem();
        if (selectedContent != null) {
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "DELETE FROM lms_data WHERE course_content_date = ? AND course_content_data = ?")) {

                preparedStatement.setString(1, selectedContent.getDate());
                preparedStatement.setString(2, selectedContent.getContent());

                preparedStatement.executeUpdate();
                contentTable.getItems().remove(selectedContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Inside FacultyClassroom.java
    private void openNewDataAddWindow(String courseName, String courseSection) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("new-data-add.fxml"));
            Parent root = loader.load();
            NewDataAddController newDataAddController = loader.getController();
            newDataAddController.setCourseInfo(courseName, courseSection);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void ExitWindow(ActionEvent event) {
        Stage stage = (Stage) Exit.getScene().getWindow();
        stage.close();
    }


    @FXML
    void openDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("faculty-lms-view.fxml"));
            Parent root = loader.load();
            FacultyLMSController facultyLMSController = loader.getController();
            Scene scene = new Scene(root);

            // Create a new stage for the LMS view
            Stage lmsStage = new Stage();
            lmsStage.setScene(scene);

            // Set mouse drag event for moving the window
            root.setOnMousePressed(mouseEvent -> {
                final double x = mouseEvent.getSceneX();
                final double y = mouseEvent.getSceneY();
                root.setOnMouseDragged(dragEvent -> {
                    lmsStage.setX(dragEvent.getScreenX() - x);
                    lmsStage.setY(dragEvent.getScreenY() - y);
                });
            });

            // Show the LMS view
            lmsStage.show();

            // Hide the main window
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
