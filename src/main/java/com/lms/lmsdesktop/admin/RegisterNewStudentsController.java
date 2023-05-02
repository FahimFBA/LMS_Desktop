package com.lms.lmsdesktop.admin;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.*;

public class RegisterNewStudentsController {

    @FXML
    private JFXButton ExitButton;

    @FXML
    private JFXButton ExportButton;
    @FXML
    private JFXButton refreshButton;

    @FXML
    private JFXButton addButton;

    @FXML
    private TableView<Student> approvedStudentTable;

    @FXML
    private TableColumn<Student, String> studentPassword;

    @FXML
    private TableColumn<Student, String> student_Email;

    @FXML
    private TableColumn<Student, String> student_ID;

    @FXML
    private TableColumn<Student, String> student_Name;

    @FXML
    private TableColumn<Student, String> student_Status;

    private ObservableList<Student> studentData = FXCollections.observableArrayList();

    public void initialize() {
        // Set up table columns
        student_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        student_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        student_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        studentPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        student_Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        student_Status.setCellFactory(col -> new TableCell<Student, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    int status = Integer.parseInt(item);
                    setText(status == 0 ? "OK" : "BLOCKED");
                    if (status == 0) {
                        setStyle("-fx-background-color: #66ff66");
                    } else {
                        setStyle("-fx-background-color: #ff6666");
                    }
                }
            }
        });

        // Populate table data from database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/student_signup", "root", "root");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM student_signup_table WHERE student_status IN ('0', '1')")) {

            while (rs.next()) {
                Student student = new Student(rs.getString("student_email"), rs.getString("student_name"), rs.getString("student_id"), rs.getString("student_password"), rs.getString("student_status"));
                studentData.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Display an error message dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to retrieve data from the database");
            alert.setContentText("An error occurred while retrieving data from the database. Please try again later.");
            alert.showAndWait();
        }

        // Set table data
        approvedStudentTable.setItems(studentData);
    }

    @FXML
    public void ExitWindow() {
        ExitButton.getScene().getWindow().hide();
    }

    @FXML
    public void addNewStudent() {
        try {
            // Load the FXML file as a Parent object
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lms/lmsdesktop/admin/ChangeStudentsData_Register.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            // hide the current window
//            addButton.getScene().getWindow().hide();

            // Get the controller instance and pass the student data as a parameter to the initData method
            ChangeStudentsData_RegisterController controller = fxmlLoader.getController();
            Student student = new Student("", "", "", "", "");
            controller.initData(student, studentData);

            // Show the stage and wait for it to close
            stage.showAndWait();

            // Refresh the table data
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void refreshDB() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/student_signup", "root", "root")) {
            // Clear the current items in the table
            approvedStudentTable.getItems().clear();

            // Load the data from the database again
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM student_signup_table WHERE student_status = '0' OR student_status = '1'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = new Student(rs.getString("student_email"), rs.getString("student_name"), rs.getString("student_id"), rs.getString("student_password"), rs.getString("student_status"));
                approvedStudentTable.getItems().add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteRow() {
        // Get the selected student from the table
        Student selectedStudent = approvedStudentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            return;
        }

        // Delete the student from the database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/student_signup", "root", "root")) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM student_signup_table WHERE student_id = ?");
            stmt.setString(1, selectedStudent.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Remove the student from the table's items list
        approvedStudentTable.getItems().remove(selectedStudent);
    }


}
