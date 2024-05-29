package com.lms.lmsdesktop.admin;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.*;

public class ApprovedFacultyController {

    @FXML
    private TableColumn<Faculty, String> CC;

    @FXML
    private TableColumn<Faculty, String> CN;

    @FXML
    private TableColumn<Faculty, String> CS;

    @FXML
    private TableColumn<Faculty, String> CSEC;

    @FXML
    private JFXButton ExitButton;

    @FXML
    private JFXButton ExportButton;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private TableColumn<Faculty, String> email;

    @FXML
    private TableView<Faculty> facultyDBview;

    @FXML
    private TableColumn<Faculty, String> name;

    @FXML
    private TableColumn<Faculty, String> password;

    @FXML
    private JFXButton refreshButton;

    @FXML
    private TableColumn<Faculty, String> serial;

    @FXML
    private JFXButton updateButton;
    @FXML
    void ChangeData(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lms/lmsdesktop/admin/ChangeFacultyData-update.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new stage for the new window
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            // Get the controller instance and pass the selected faculty object to the initData method
            ChangeFacultyDataUpdateController controller = fxmlLoader.getController();
            controller.initData(facultyDBview.getSelectionModel().getSelectedItem());

            // Show the window and do not block the code
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @FXML
    void ExitWindow(ActionEvent event) {
        // Get the current window
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        // Close the window
        stage.close();
    }



    @FXML
    public void initialize() {
        serial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        CS.setCellValueFactory(new PropertyValueFactory<>("courseSerial"));
        CC.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        CN.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        CSEC.setCellValueFactory(new PropertyValueFactory<>("courseSection"));

        refreshDB();
    }

    @FXML
    void addData(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lms/lmsdesktop/admin/changeFacultyData-new.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new stage for the new window
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            // Show the window and do not block the code
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @FXML
    public void refreshDB() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/student_signup", "root", "root")) {
            // Clear the current items in the table
            facultyDBview.getItems().clear();

            // Load the data from the database again
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM faculty_table INNER JOIN course ON faculty_table.faculty_serial_id = course.course_section_faculty_ID");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Faculty faculty = new Faculty(rs.getString("faculty_serial_id"), rs.getString("faculty_name"), rs.getString("faculty_email"), rs.getString("faculty_password"), rs.getString("course_serial"), rs.getString("course_code"), rs.getString("course_name"), rs.getString("course_section"));
                facultyDBview.getItems().add(faculty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteRow() {
        // Get the selected faculty from the table
        Faculty selectedFaculty = facultyDBview.getSelectionModel().getSelectedItem();
        if (selectedFaculty == null) {
            System.out.println("No faculty selected.");
            return;
        }

        System.out.println("Selected faculty: " + selectedFaculty.getSerial());

        // Delete the faculty from the database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/student_signup", "root", "root")) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM faculty_table WHERE faculty_serial_id = ?");
            stmt.setString(1, selectedFaculty.getSerial());
            stmt.executeUpdate();

            // Delete the course information associated with the faculty
            PreparedStatement courseStmt = conn.prepareStatement("DELETE FROM course WHERE course_section_faculty_ID = ?");
            courseStmt.setString(1, selectedFaculty.getSerial());
            courseStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Remove the faculty from the table's items list
        facultyDBview.getItems().remove(selectedFaculty);
    }
}
